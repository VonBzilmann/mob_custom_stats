package me.bziluch.mobCustomStats.models;

import me.bziluch.mobCustomStats.MobCustomStats;
import me.bziluch.mobCustomStats.managers.ErrorLoggerManager;
import me.bziluch.mobCustomStats.services.ErrorLoggerService;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ConfigItemStack extends ItemStack {

    public static final String keyAmount = "amount";
    public static final String keyName = "name";
    public static final String keyLore = "lore";
    public static final String keyEnchants = "enchants";

    private static final ErrorLoggerService logger = ErrorLoggerManager.getLogger();

    public ConfigItemStack(ConfigurationSection section, String parentKey, String path) {

        // Get material
        Material material = Material.valueOf(parentKey);
        if (material == null) {
            logger.sendErrorMessage("Unable to get material in section "+path+"."+parentKey);
            return;
        }
        this.setType(material);

        // Set amount
        int amount = section.getInt(keyAmount);
        this.setAmount(amount > 0 ? amount : 1);

        // Set name
        String name = section.getString(keyName);
        if (name != null) {
            Objects.requireNonNull(this.getItemMeta()).setDisplayName(name);
            this.getItemMeta().setItemName(name);
        }

        // Set lore
        List<String> lore = section.getStringList(keyLore);
        if (!lore.isEmpty()) {
            Objects.requireNonNull(this.getItemMeta()).setLore(lore);
        }

        // Set enchants
        List<String> enchantStrings = section.getStringList(keyEnchants);
        for (String enchantString : enchantStrings) {
            String[] enchantStringArray = enchantString.split("/");

            if (enchantStringArray.length == 0) {
                logger.sendErrorMessage("Invalid enchant string ("+enchantString+") at "+path+"."+parentKey+"."+keyEnchants);
                continue;
            }

            Enchantment enchantment = Enchantment.getByName(enchantStringArray[0]);
            if (enchantment == null) {
                logger.sendErrorMessage("Invalid enchant name ("+enchantStringArray[0]+") at "+path+"."+parentKey+"."+keyEnchants);
                continue;
            }

            int level = 1;

            if (enchantStringArray.length > 1) {
                level = Integer.parseInt(enchantStringArray[1]);
            }

            Objects.requireNonNull(this.getItemMeta()).addEnchant(enchantment, level, true);
        }

    }

}
