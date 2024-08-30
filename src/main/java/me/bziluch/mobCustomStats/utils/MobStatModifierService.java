package me.bziluch.mobCustomStats.utils;

import me.bziluch.mobCustomStats.MobCustomStats;
import me.bziluch.mobCustomStats.models.EquipmentSlotType;
import me.bziluch.mobCustomStats.models.MobEffectModel;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobStatModifierService {

    private static FileConfiguration configFile = MobCustomStats.getConfigFile();

    private static final Map<String, String> modifiers = new HashMap<>();

    static {
        modifiers.put("health", "setupHealth");
        modifiers.put("weapon", "setWeapon");
        modifiers.put("effects", "setEffects");
    }

    public static void setStats(LivingEntity entity) {
        String entityType = entity.getType().toString().toLowerCase();

        //Set health
        double health = configFile.getDouble("stats." + entityType + ".health");
        if (health > 0) {
            entity.setMaxHealth(health);
            entity.setHealth(health);
        }

        //Set effects
        List<String> effectStringList = configFile.getStringList("stats." + entityType + ".effects");
        if (effectStringList != null) {
            for (String effectString : effectStringList) {
                String[] effectSplit = effectString.split("/");
                PotionEffectType effectType = PotionEffectType.getByName(effectSplit[0]);
                if (effectType != null) {
                    Integer amplifier = 1;

                    if (effectSplit.length == 2) {
                        amplifier = Integer.valueOf(effectSplit[1]);
                    }

                    entity.addPotionEffect(
                            new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, true, false)
                    );
                }
            }
        }

        List<String> equipmentStringList = configFile.getStringList("stats." + entityType + ".equipment");
        if (equipmentStringList != null) {
            for (String equipmentString : equipmentStringList) {

                String[] equipmentSplit = equipmentString.split("/");
                if (equipmentSplit.length < 2) {
                    continue;
                }

                EquipmentSlotType slotType = EquipmentSlotType.valueOf(equipmentSplit[0]);
                Material material = Material.valueOf(equipmentSplit[1]);
                ItemStack itemStack = new ItemStack(material);

                //TODO: make static collection of equipment and effects - for each mobs

                if (equipmentSplit.length > 2) {
                    int splitIndex = 3;
                    while (equipmentSplit.length >= splitIndex) {

                        int level = 1;
                        if (equipmentSplit.length >= (splitIndex + 1)) {
                            level = Integer.parseInt(equipmentSplit[(splitIndex)]);
                        }

                        Enchantment enchantment = Enchantment.getByName(equipmentSplit[splitIndex - 1]);
                        if (enchantment != null) {
                            itemStack.addEnchantment(enchantment, level);
                        }
                    }
                }

                //TODO: apply item to selected slot
            }
        }
    }

}
