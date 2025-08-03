package com.after_life.after_life_mod.shop.gui;

import com.after_life.after_life_mod.AfterLifeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.bus.api.IEventBus;
public class ShopMenuType {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, AfterLifeMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ShopMenu>> SHOP_MENU =
            MENUS.register("shop_menu",
                    () -> new MenuType<>(ShopMenu::new, FeatureFlags.VANILLA_SET));

    // Правильная регистрация через событие
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
