package com.after_life.after_life_mod;

import com.after_life.after_life_mod.shop.ShopCommands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import com.after_life.after_life_mod.currency.CurrencyStorage;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.after_life.after_life_mod.currency.DatabaseCurrencyStorage;
import java.sql.SQLException;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import com.after_life.after_life_mod.shop.gui.ShopScreenRegistrar;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;


@Mod(AfterLifeMod.MODID)
public class AfterLifeMod {
    public static final String MODID = "aftelifeuniversal";

    public static CurrencyStorage currencyStorage;
    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public AfterLifeMod(IEventBus modBus) {
        ShopMenuType.SHOP_MENU.get();
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }
    private void registerCommands(RegisterCommandsEvent event) {
        ShopCommands.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        try {
            AfterLifeMod.currencyStorage = new DatabaseCurrencyStorage();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}