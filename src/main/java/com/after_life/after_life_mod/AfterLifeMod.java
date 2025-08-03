package com.after_life.after_life_mod;

import com.after_life.after_life_mod.client.ClientModEvents;
import com.after_life.after_life_mod.currency.CurrencyStorage;
import com.after_life.after_life_mod.currency.DatabaseCurrencyStorage;
import com.after_life.after_life_mod.db.InMemoryCurrencyStorage;
import com.after_life.after_life_mod.shop.ShopCommands;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
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
    public static CurrencyStorage currencyStorage;

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public AfterLifeMod(IEventBus modEventBus, ModContainer modContainer) {
        // Регистрация системы меню
        ShopMenuType.MENUS.register(modEventBus);

        // Регистрация команд
        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        // Инициализация клиентской части
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientModEvents.init(modEventBus, modContainer);
        }

        // Регистрация конфигурации (если нужно)
        // modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        ShopCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            try {
                currencyStorage = new DatabaseCurrencyStorage();
            } catch (SQLException e) {
                e.printStackTrace();
                // Фолбэк на in-memory хранилище если БД недоступна
                currencyStorage = new InMemoryCurrencyStorage();
                System.err.println("Failed to initialize database, using in-memory storage");
            }
        });
    }
}