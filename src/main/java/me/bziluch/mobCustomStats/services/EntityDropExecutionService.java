package me.bziluch.mobCustomStats.services;

import me.bziluch.mobCustomStats.models.EntityDropsModel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDropExecutionService {

    private static final Map<EntityType, EntityDropsModel> entitiesDrops = new HashMap<>();

    public static void clearMappings() {
        entitiesDrops.clear();
    }

    public static void updateDrops(List<ItemStack> drops, LivingEntity entity, int lootingLevel) {

    }
}
