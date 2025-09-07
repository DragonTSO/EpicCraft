package com.dragondirt.epiccraftitem.util;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * File manager utility for handling configuration files
 */
public class FileManager {
    private final String fileName;
    private final EpicCraftItem plugin;
    private File file;
    private FileConfiguration config;

    public FileManager(String fileName, EpicCraftItem plugin) {
        this.fileName = fileName;
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
    }

    public FileManager(String fileName, EpicCraftItem plugin, String... folders) {
        this.fileName = fileName;
        this.plugin = plugin;
        
        File dataFolder = plugin.getDataFolder();
        for (String folder : folders) {
            dataFolder = new File(dataFolder, folder);
        }
        
        this.file = new File(dataFolder, fileName);
    }

    /**
     * Create folder structure
     */
    public void createFolder(String... folders) {
        File dataFolder = plugin.getDataFolder();
        for (String folder : folders) {
            dataFolder = new File(dataFolder, folder);
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
        }
    }

    /**
     * Create file
     */
    public void createFile() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create file " + fileName + ": " + e.getMessage());
            }
        }
    }

    /**
     * Copy default file from resources
     */
    public void copyDefault() {
        if (!file.exists()) {
            createFile();
            InputStream resource = plugin.getResource(fileName);
            if (resource != null) {
                try {
                    Files.copy(resource, file.toPath());
                } catch (IOException e) {
                    plugin.getLogger().severe("Could not copy default file " + fileName + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Load configuration
     */
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * Reload configuration
     */
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Save configuration
     */
    public void saveConfig() {
        if (config != null) {
            try {
                config.save(file);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save file " + fileName + ": " + e.getMessage());
            }
        }
    }

    /**
     * Get file
     */
    public File getFile() {
        return file;
    }

    /**
     * Get file name
     */
    public String getFileName() {
        return fileName;
    }
}
