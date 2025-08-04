package com.after_life.after_life_mod;

import com.after_life.after_life_mod.client.ClientModEvents;
import com.after_life.after_life_mod.currency.CurrencyManager;
import com.after_life.after_life_mod.db.DatabaseCurrencyManager;
import com.after_life.after_life_mod.db.InMemoryCurrencyManager;
import com.after_life.after_life_mod.shop.ShopCommands;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.sql.SQLException;

@Mod(AfterLifeMod.MODID)
public class AfterLifeMod {
    public static final String MODID = "aftelifeuniversal";
    public static CurrencyManager currencyManager;

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public AfterLifeMod(IEventBus modEventBus, ModContainer modContainer) {
        // Инициализация менеджера валюты
        initializeCurrencyManager();

        // Регистрация системы меню
        ShopMenuType.MENUS.register(modEventBus);

        // Регистрация команд
        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        // Инициализация клиентской части
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientModEvents.init(modEventBus, modContainer);
        }

        // Регистрация конфигурации (если нужно)

    }

    private void initializeCurrencyManager() {
        try {
            currencyManager = new DatabaseCurrencyManager();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database, using in-memory storage");
            e.printStackTrace();
            currencyManager = new InMemoryCurrencyManager();
        }
    }

    private void registerCommands(RegisterCommandsEvent event) {
        ShopCommands.register(event.getDispatcher());
    }
}