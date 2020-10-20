package com.ryandw11.structure.structure.properties;

import com.ryandw11.structure.CustomStructures;
import com.ryandw11.structure.structure.properties.schematics.SubSchematic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles the sub schematics of the structure.
 */
public class SubSchematics {

    private boolean enabled;
    private List<SubSchematic> schematics;

    /**
     * Get the sub schematics from a configuration file.
     *
     * @param configuration The configuration file.
     * @param plugin        The instance of custom structures. (CustomStructures.getInstance()).
     */
    public SubSchematics(FileConfiguration configuration, CustomStructures plugin) {
        schematics = new ArrayList<>();
        if (!configuration.contains("SubSchematics")) {
            enabled = false;
            return;
        }

        ConfigurationSection section = configuration.getConfigurationSection("SubSchematics");
        assert section != null;

        if (!section.contains("Enabled")) {
            enabled = false;
            return;
        }
        enabled = section.getBoolean("Enabled");

        if (!section.contains("Schematics")) {
            enabled = false;
            return;
        }
        try {
            for (String s : Objects.requireNonNull(section.getConfigurationSection("Schematics")).getKeys(false)) {
                schematics.add(new SubSchematic((Objects.requireNonNull(section.getConfigurationSection("Schematics." + s)))));
            }
        } catch (RuntimeException ex) {
            enabled = false;
            plugin.getLogger().warning("Unable to enable SubStructures on structure " + configuration.getName() + ".");
            plugin.getLogger().warning("The following error occurred:");
            plugin.getLogger().warning(ex.getMessage());
        }
    }

    /**
     * If sub schematics are enabled.
     *
     * @return If sub schematics are enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Only enable this via code if you are certain the formatting is right with the schematic list.
     * The plugin does no additional checks after the construction of the object.
     *
     * @param enabled If you want the feature to be enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the list of sub schematics.
     *
     * @return The list of sub schematics.
     */
    public List<SubSchematic> getSchematics() {
        return schematics;
    }

    /**
     * Set the list of sub schematics.
     *
     * @param schematics The list of sub schematics.
     */
    public void setSchematics(List<SubSchematic> schematics) {
        this.schematics = schematics;
    }
}
