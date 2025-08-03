package com.after_life.after_life_mod.shop.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.MenuProvider;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
public class ShopMenuProvider implements MenuProvider {
    @Override
    public Component getDisplayName() {
        return Component.literal("Магазин");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, net.minecraft.world.entity.player.Player player) {
        return new ShopMenu(id, inv);
    }
}