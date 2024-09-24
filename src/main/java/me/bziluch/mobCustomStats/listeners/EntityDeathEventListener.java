package me.bziluch.mobCustomStats.listeners;

import me.bziluch.mobCustomStats.services.EntityDropExecutionService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EntityDeathEventListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Bukkit.broadcastMessage(ChatColor.GRAY + "Some entity has been killed");
        LivingEntity entity = event.getEntity();

        if (entity.getType() == EntityType.PLAYER) {
            return;
        }

        Entity killingEntity = entity.getLastDamageCause().getDamageSource().getCausingEntity();
        if (killingEntity == null || killingEntity.getType() != EntityType.PLAYER) {
            return;
        }

        int lootingLevel = 0;
        ItemStack weapon = Objects.requireNonNull(((LivingEntity) killingEntity).getEquipment()).getItemInMainHand();
        if (weapon != null) {
            lootingLevel = weapon.getEnchantmentLevel(Enchantment.LOOTING);
        }

        EntityDropExecutionService.updateDrops(event.getDrops(), event.getEntityType(), lootingLevel);
    }
}
