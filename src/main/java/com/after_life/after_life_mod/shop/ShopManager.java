package com.after_life.after_life_mod.shop;

import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ShopManager {
    public static final List<ShopItem> items = new ArrayList<>();
    private static final File configFile = new File("config/afterlife_shop.json");

    public static void load() {
        items.clear();
        if (!configFile.exists()) return;

        try (FileReader reader = new FileReader(configFile)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                String id = obj.get("id").getAsString();
                int count = obj.get("count").getAsInt();
                int price = obj.get("price").getAsInt();

                ResourceLocation itemId = ResourceLocation.parse(id);
                ItemStack stack = new ItemStack(BuiltInRegistries.ITEM.get(itemId), count);
                items.add(new ShopItem(stack, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        JsonArray array = new JsonArray();
        for (ShopItem item : items) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", BuiltInRegistries.ITEM.getKey(item.item.getItem()).toString());
            obj.addProperty("count", item.item.getCount());
            obj.addProperty("price", item.price);
            array.add(obj);
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(array, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}