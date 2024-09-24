package me.bziluch.mobCustomStats.services;

import me.bziluch.mobCustomStats.MobCustomStats;
import me.bziluch.mobCustomStats.models.ConfigItemStack;
import me.bziluch.mobCustomStats.models.EntityDropsModel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDropExecutionService {

    private static final FileConfiguration configFile = MobCustomStats.getConfigFile();
    private static final Map<EntityType, EntityDropsModel> entitiesDrops = new HashMap<>();

    public static void clearMappings() {
        entitiesDrops.clear();
    }

    public static void updateDrops(List<ItemStack> drops, EntityType entityType, int lootingLevel) {
        EntityDropsModel dropsModel = entitiesDrops.get(entityType);

        if (null == dropsModel) {
            dropsModel = loadDrops(entityType);
            entitiesDrops.put(entityType, dropsModel);
        }

        dropsModel.updateDropList(drops);
    }

    private static EntityDropsModel loadDrops(EntityType entityType) {
        EntityDropsModel model = new EntityDropsModel();

        String entityTypeString = entityType.toString().toLowerCase();
        String sectionPath = "stats." + entityTypeString + ".drops";

        ConfigurationSection section = configFile.getConfigurationSection(sectionPath);
        if (section == null || section.getKeys(false).isEmpty()) {
            return model;
        }

        model.setOverwriteDefaults(section.getBoolean("overwriteDefault"));
        ConfigurationSection itemsSection = section.getConfigurationSection("dropItems");

        assert itemsSection != null;
        for (String itemSectionKey : itemsSection.getKeys(false)) {
            model.addItem(new ConfigItemStack(itemsSection.getConfigurationSection(itemSectionKey), itemSectionKey, sectionPath));
        }

        return model;
    }

}
