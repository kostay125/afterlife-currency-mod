package com.after_life.after_life_mod.commands;

import com.after_life.after_life_mod.AfterLifeMod;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CurrencyCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("balance")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    int balance = AfterLifeMod.currencyStorage.getBalance(player.getUUID());
                    player.sendSystemMessage(Component.literal("Ваш баланс: " + balance + " монет"));
                    return 1;
                });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> adminAdd() {
        return Commands.literal("addmoney")
                .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            int amount = context.getArgument("amount", Integer.class);
                            AfterLifeMod.currencyStorage.addBalance(player.getUUID(), amount);
                            player.sendSystemMessage(Component.literal("Выдано " + amount + " монет"));
                            return 1;
                        }));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> adminRemove() {
        return Commands.literal("takemoney")
                .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            int amount = context.getArgument("amount", Integer.class);
                            if (AfterLifeMod.currencyStorage.subtractBalance(player.getUUID(), amount)) {
                                player.sendSystemMessage(Component.literal("Списано " + amount + " монет"));
                            } else {
                                player.sendSystemMessage(Component.literal("Недостаточно средств"));
                            }
                            return 1;
                        }));
    }
}
