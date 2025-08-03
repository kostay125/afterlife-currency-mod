package com.after_life.after_life_mod.shop.gui;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ShopScreenRegistrar {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ShopScreenRegistrar::registerMenuScreens);
    }

    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ShopMenuType.SHOP_MENU.get(), ShopScreen::new);
    }
}