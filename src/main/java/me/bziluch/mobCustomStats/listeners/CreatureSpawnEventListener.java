package me.bziluch.mobCustomStats.listeners;

import me.bziluch.mobCustomStats.utils.MobStatModifierService;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnEventListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.getType() != EntityType.PLAYER) {
            MobStatModifierService.setStats(entity);
        }
    }
}
