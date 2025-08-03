package com.after_life.after_life_mod.shop.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;

public class ShopMenu extends AbstractContainerMenu {
    public ShopMenu(int id, Inventory inv) {
        super(ShopMenuType.SHOP_MENU.get(), id);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}