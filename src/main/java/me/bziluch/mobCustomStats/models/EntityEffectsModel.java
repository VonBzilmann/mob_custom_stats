package me.bziluch.mobCustomStats.models;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class EntityEffectsModel {

    private final List<PotionEffect> effects = new ArrayList<>();

    public void addItem(PotionEffect potionEffect) {
        effects.add(potionEffect);
    }

    public void applyEffects(LivingEntity entity) {
        for (PotionEffect potionEffect : effects) {
            entity.addPotionEffect(potionEffect);
        }
    }

}
