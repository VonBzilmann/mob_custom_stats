package me.bziluch.mobCustomStats.utils;

import me.bziluch.mobCustomStats.MobCustomStats;
import me.bziluch.mobCustomStats.models.EntityEffectsModel;
import me.bziluch.mobCustomStats.models.EntityEquipmentModel;
import me.bziluch.mobCustomStats.models.EquipmentSlotType;
import me.bziluch.mobCustomStats.services.ErrorLoggerService;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobStatModifierService {

    private static final FileConfiguration configFile = MobCustomStats.getConfigFile();
    private static final ErrorLoggerService logger = new ErrorLoggerService("max_error_messages", 64);

    private static final Map<EntityType, EntityEquipmentModel> entitiesEquipment = new HashMap<>();
    private static final Map<EntityType, EntityEffectsModel> entitiesEffects = new HashMap<>();

    public static void setStats(LivingEntity entity) {

        EntityType entityType = entity.getType();
        String entityTypeString = entityType.toString().toLowerCase();

        if (!configFile.contains("stats." + entityTypeString)) {
            return;
        }

        // Set health
        double health = configFile.getDouble("stats." + entityTypeString + ".health");
        if (health > 0) {
            entity.setMaxHealth(health);
            entity.setHealth(health);
        } else {
            logger.sendErrorMessage(health + " is not a valid health value");
        }

        // Set effects
        EntityEffectsModel entityEffectsModel = entitiesEffects.get(entityType);
        if (entityEffectsModel == null) {
            entityEffectsModel = new EntityEffectsModel();

            List<String> effectStringList = configFile.getStringList("stats." + entityTypeString + ".effects");
            if (effectStringList != null) {
                for (String effectString : effectStringList) {
                    String[] effectSplit = effectString.split("/");
                    PotionEffectType effectType = PotionEffectType.getByName(effectSplit[0]);
                    if (effectType != null) {
                        Integer amplifier = 1;

                        if (effectSplit.length == 2) {
                            amplifier = Integer.valueOf(effectSplit[1]);
                            if (amplifier <= 0 || amplifier > 255) {
                                logger.sendErrorMessage(effectSplit[1] + " is not a valid effect level");
                                amplifier = 1;
                            }
                        }

                        entityEffectsModel.addItem(new PotionEffect(effectType, PotionEffect.INFINITE_DURATION, amplifier, true, false));
                    } else {
                        logger.sendErrorMessage(effectSplit[0] + " is not a valid effect type");
                    }
                }
            }

            entitiesEffects.put(entityType, entityEffectsModel);
        }
        entityEffectsModel.applyEffects(entity);


        // Set equipment & enchantments
        EntityEquipmentModel entityEquipmentModel = entitiesEquipment.get(entityType);
        if (entityEquipmentModel == null) {
            entityEquipmentModel = new EntityEquipmentModel();

            List<String> equipmentStringList = configFile.getStringList("stats." + entityTypeString + ".equipment");
            if (equipmentStringList != null) {
                for (String equipmentString : equipmentStringList) {

                    String[] equipmentSplit = equipmentString.split("/");
                    if (equipmentSplit.length < 2) {
                        logger.sendErrorMessage(equipmentString + " is not a valid equipment string");
                        continue;
                    }

                    EquipmentSlotType slotType;
                    try {
                        slotType = EquipmentSlotType.valueOf(equipmentSplit[0]);
                    } catch (IllegalArgumentException e) {
                        logger.sendErrorMessage(equipmentSplit[0] + " is not a valid slot type");
                        continue;
                    }

                    Material material;
                    try {
                        material = Material.valueOf(equipmentSplit[1]);
                    } catch (IllegalArgumentException e) {
                        logger.sendErrorMessage(equipmentSplit[1] + " is not a valid item id");
                        continue;
                    }
                    ItemStack itemStack = new ItemStack(material);

                    if (equipmentSplit.length > 2) {
                        int splitIndex = 3;
                        while (equipmentSplit.length >= splitIndex) {

                            int level = 1;
                            if (equipmentSplit.length >= (splitIndex + 1)) {
                                level = Integer.parseInt(equipmentSplit[(splitIndex)]);
                                if (level <= 0 || level > 255) {
                                    logger.sendErrorMessage(equipmentSplit[(splitIndex)] + " is not a valid enchantment level");
                                    level = 1;
                                }
                            }

                            Enchantment enchantment = Enchantment.getByName(equipmentSplit[splitIndex - 1]);
                            if (enchantment != null) {
                                itemStack.addEnchantment(enchantment, level);
                            } else {
                                logger.sendErrorMessage(equipmentSplit[(splitIndex - 1)] + " is not a valid enchantment name");
                                continue;
                            }
                            splitIndex += 2;
                        }
                    }
                    entityEquipmentModel.addItem(slotType, itemStack);
                }
            }

            entitiesEquipment.put(entityType, entityEquipmentModel);
        }
        entityEquipmentModel.equipEntity(entity);

    }

}
