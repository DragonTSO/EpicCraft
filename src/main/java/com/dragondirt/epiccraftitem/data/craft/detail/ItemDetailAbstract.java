package com.dragondirt.epiccraftitem.data.craft.detail;

/**
 * Abstract class for item detail components
 */
public abstract class ItemDetailAbstract {
    private final String key;
    private boolean enable;

    public ItemDetailAbstract(String key, boolean enable) {
        this.key = key;
        this.enable = enable;
    }

    public String getKey() {
        return key;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
