package me.bziluch.mobCustomStats.models;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityDropsModel {

    private boolean overwriteDefaults = false;
    private final List<ConfigItemStack> drops = new ArrayList<>();

    public void setOverwriteDefaults(boolean overwriteDefaults) {
        this.overwriteDefaults = overwriteDefaults;
    }

    public void addItem(ConfigItemStack itemStack) {
        this.drops.add(itemStack);
    }

    public void updateDropList(List<ItemStack> dropList) {

        if (this.overwriteDefaults) {
            dropList.clear();
        }

        //TODO: advanced mechanics here (with drop rate determination, drop amount etc.)
        dropList.addAll(this.drops);
    }
}
