package me.bziluch.mobCustomStats.models;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EntityEquipmentModel {

    private final Map<EquipmentSlotType, ItemStack> equipment = new HashMap<>();

    public void addItem(EquipmentSlotType slotType, ItemStack itemStack) {
        this.equipment.put(slotType, itemStack);
    }

    public void equipEntity(LivingEntity entity) {
        ItemStack itemStack;
        EntityEquipment entityEquipment = entity.getEquipment();

        if (null != (itemStack = this.equipment.get(EquipmentSlotType.WEAPON))) {
            entityEquipment.setItemInMainHand(itemStack);
        }
        if (null != (itemStack = this.equipment.get(EquipmentSlotType.HELMET))) {
            entityEquipment.setHelmet(itemStack);
        }
        if (null != (itemStack = this.equipment.get(EquipmentSlotType.CHESTPLATE))) {
            entityEquipment.setChestplate(itemStack);
        }
        if (null != (itemStack = this.equipment.get(EquipmentSlotType.LEGGINGS))) {
            entityEquipment.setLeggings(itemStack);
        }
        if (null != (itemStack = this.equipment.get(EquipmentSlotType.BOOTS))) {
            entityEquipment.setBoots(itemStack);
        }
        if (null != (itemStack = this.equipment.get(EquipmentSlotType.LEFT_HAND))) {
            entityEquipment.setItemInOffHand(itemStack);
        }


    }

}
