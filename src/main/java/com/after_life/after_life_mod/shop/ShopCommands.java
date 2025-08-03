package com.after_life.after_life_mod.shop;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import com.after_life.after_life_mod.shop.gui.ShopMenu;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;

public class ShopCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("shop")
                        .executes(ctx -> {
                            if (!(ctx.getSource().getEntity() instanceof ServerPlayer player)) {
                                ctx.getSource().sendFailure(Component.literal("Только для игроков!"));
                                return 0;
                            }

                            player.openMenu(new SimpleMenuProvider(
                                    (id, inv, ply) -> new ShopMenu(ShopMenuType.SHOP_MENU.get(), id, inv),
                                    Component.literal("Магазин")
                            ));
                            return 1;
                        })
        );

        dispatcher.register(
                Commands.literal("shopadmin")
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> {
                            ctx.getSource().sendSuccess(
                                    () -> Component.literal("Админ-панель магазина"),
                                    false
                            );
                            return 1;
                        })
        );
    }
}