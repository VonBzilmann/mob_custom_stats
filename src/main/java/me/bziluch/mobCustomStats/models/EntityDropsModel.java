package me.bziluch.mobCustomStats.models;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityDropsModel {

    private boolean overwriteDefaults = false;
    private final List<ItemStack> drops = new ArrayList<>();

    public void setOverwriteDefaults(boolean overwriteDefaults) {
        this.overwriteDefaults = overwriteDefaults;
    }

    public void addItem(ItemStack itemStack) {
        this.drops.add(itemStack);
    }

    public void updateDropList(List<ItemStack> dropList) {

        if (this.overwriteDefaults) {
            dropList.clear();
        }
        dropList.addAll(this.drops);
    }
}
