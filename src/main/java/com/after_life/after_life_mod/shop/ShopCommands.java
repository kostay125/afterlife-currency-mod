package com.after_life.after_life_mod.shop;

import com.after_life.after_life_mod.currency.CurrencyManager;
import com.after_life.after_life_mod.db.DatabaseCurrencyManager;
import com.after_life.after_life_mod.shop.gui.AdminShopMenu;
import com.after_life.after_life_mod.shop.gui.ShopMenu;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.UUID;

public class ShopCommands {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final CurrencyManager currencyManager;

    // Инициализация менеджера валюты при загрузке класса
    static {
        try {
            currencyManager = new DatabaseCurrencyManager();
            LOGGER.info("Currency manager initialized successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize currency database", e);
        }
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // Команда /shop
        dispatcher.register(Commands.literal("shop")
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    player.openMenu(new SimpleMenuProvider(
                            (containerId, inventory, p) -> new ShopMenu(containerId, inventory),
                            Component.literal("Магазин")
                    ));
                    return 1;
                })
        );

        // Команда /shopadmin
        dispatcher.register(Commands.literal("shopadmin")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> {
                    ServerPlayer admin = ctx.getSource().getPlayerOrException();
                    admin.openMenu(new SimpleMenuProvider(
                            (containerId, inventory, p) -> new AdminShopMenu(containerId, inventory),
                            Component.literal("Админ-панель магазина")
                    ));
                    return 1;
                })
        );

        // Команда /balance
        dispatcher.register(Commands.literal("balance")
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    int balance = currencyManager.getBalance(player.getUUID());
                    ctx.getSource().sendSuccess(
                            () -> Component.literal("Ваш баланс: " + balance),
                            false
                    );
                    return 1;
                })
                .then(Commands.argument("player", EntityArgument.player())
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> {
                            ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                            int balance = currencyManager.getBalance(target.getUUID());
                            ctx.getSource().sendSuccess(
                                    () -> Component.literal("Баланс игрока " + target.getName().getString() + ": " + balance),
                                    true
                            );
                            return 1;
                        })
                )
        );

        // Команда /addmoney
        dispatcher.register(Commands.literal("addmoney")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes(ctx -> {
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                                    int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                    currencyManager.addBalance(target.getUUID(), amount);
                                    ctx.getSource().sendSuccess(
                                            () -> Component.literal("Добавлено " + amount + " валюты игроку " + target.getName().getString()),
                                            true
                                    );
                                    return 1;
                                }))
                )
        );

        // Команда /takemoney
        dispatcher.register(Commands.literal("takemoney")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                .executes(ctx -> {
                                    ServerPlayer target = EntityArgument.getPlayer(ctx, "player");
                                    int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                    boolean success = currencyManager.removeBalance(target.getUUID(), amount);

                                    if (success) {
                                        ctx.getSource().sendSuccess(
                                                () -> Component.literal("Списано " + amount + " валюты у игрока " + target.getName().getString()),
                                                true
                                        );
                                    } else {
                                        ctx.getSource().sendFailure(Component.literal("Недостаточно средств у игрока"));
                                    }
                                    return success ? 1 : 0;
                                }))
                )
        );
    }
}