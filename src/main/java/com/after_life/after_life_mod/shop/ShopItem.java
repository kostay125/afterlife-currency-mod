package com.after_life.after_life_mod.shop;

import net.minecraft.world.item.ItemStack;

public class ShopItem {
    public ItemStack item;
    public int price;

    public ShopItem(ItemStack item, int price) {
        this.item = item;
        this.price = price;
    }

    public ItemStack copyItem() {
        return item.copy();
    }
}