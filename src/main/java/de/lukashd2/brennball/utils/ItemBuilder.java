package de.lukashd2.brennball.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
    }

    public ItemBuilder setName(String name){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color){
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setUnbreakable(){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder clearFlags(){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemStack toItemStack(){
        return itemStack;
    }

}
