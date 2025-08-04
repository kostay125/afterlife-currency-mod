package com.after_life.after_life_mod.shop.gui;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class AdminShopMenu extends AbstractContainerMenu {
    public AdminShopMenu(int containerId, Inventory playerInventory) {
        super(ShopMenuType.ADMIN_SHOP_MENU.get(), containerId);

        // Здесь добавляйте слоты и другую логику меню
        // Пример добавления слотов игрока:
        // addPlayerSlots(playerInventory);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.hasPermissions(2); // Или ваша логика проверки доступности меню
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // Базовая реализация
    }

    // Дополнительные методы по необходимости
    private void addPlayerSlots(Inventory playerInventory) {
        // Реализация добавления слотов инвентаря игрока
    }
}