package com.after_life.after_life_mod.shop.gui;

import com.after_life.after_life_mod.AfterLifeMod;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class ShopMenuType {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, AfterLifeMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ShopMenu>> SHOP_MENU =
            MENUS.register("shop_menu", () ->
                    new MenuType<>(
                            ShopMenu::new,
                            FeatureFlagSet.of(FeatureFlags.VANILLA)
                    )
            );

    public static final DeferredHolder<MenuType<?>, MenuType<AdminShopMenu>> ADMIN_SHOP_MENU =
            MENUS.register("admin_shop_menu", () ->
                    new MenuType<>(
                            AdminShopMenu::new,
                            FeatureFlagSet.of(FeatureFlags.VANILLA)
                    )
            );
}