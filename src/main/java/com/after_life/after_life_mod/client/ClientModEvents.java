package com.after_life.after_life_mod.client;

import com.after_life.after_life_mod.AfterLifeMod;
import com.after_life.after_life_mod.shop.gui.ShopMenu;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
import com.after_life.after_life_mod.shop.gui.ShopScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class ClientModEvents {
    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(ClientModEvents::registerScreens);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ShopMenuType.SHOP_MENU.get(), ShopScreen::new);
    }
}