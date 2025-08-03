package com.after_life.after_life_mod.shop;

import com.after_life.after_life_mod.shop.gui.ShopMenu;
import com.after_life.after_life_mod.shop.gui.ShopMenuType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;

public class ShopCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("shop")
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    player.openMenu(new SimpleMenuProvider(
                            (id, inv, ply) -> new ShopMenu(id, inv),
                            Component.literal("Магазин")
                    ));
                return 1;
            });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> admin() {
        return Commands.literal("shopadmin")
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                player.sendSystemMessage(Component.literal("Админ-панель магазина (в разработке)"));
                return 1;
            });
    }
}