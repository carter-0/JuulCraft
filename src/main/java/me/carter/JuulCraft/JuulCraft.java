package me.carter.JuulCraft;

import me.carter.JuulCraft.events.PlayerUseJuul;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public final class JuulCraft extends JavaPlugin {
    public NamespacedKey key = new NamespacedKey(this, "dur");

    @Override
    public void onEnable() {
        // Juul Pod Recipe

        ItemStack juulPod = new ItemStack(Material.SUGAR, 1);
        ItemMeta juulMeta = juulPod.getItemMeta();

        ArrayList<String> lore = new ArrayList();
        lore.add("May Contain Carcinogens");
        juulMeta.setDisplayName("Juul Pod");
        juulMeta.addEnchant(Enchantment.PIERCING, 10, true);
        juulMeta.setLore(lore);

        juulPod.setItemMeta(juulMeta);

        ShapelessRecipe juulPodRecipe = new ShapelessRecipe(juulPod);
        juulPodRecipe.addIngredient(Material.BLACKSTONE);

        // Empty Juul -> Juul Recipe

        ItemStack bottle = new ItemStack(Material.POTION, 1);
        PotionMeta bottleMeta = (PotionMeta) bottle.getItemMeta();

        bottleMeta.setDisplayName("Juul");
        bottleMeta.addEnchant(Enchantment.PIERCING, 10, true);
        bottleMeta.setBasePotionData(new PotionData(PotionType.WATER));

        bottle.setItemMeta(bottleMeta);

        ShapelessRecipe bottleRecipe = new ShapelessRecipe(bottle);

        ItemStack brokenJuul = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta brokenMeta = brokenJuul.getItemMeta();
        brokenMeta.addEnchant(Enchantment.PIERCING, 10, true);
        brokenMeta.setDisplayName("Empty Juul");

        brokenJuul.setItemMeta(brokenMeta);

        bottleRecipe.addIngredient(new RecipeChoice.ExactChoice(brokenJuul));
        bottleRecipe.addIngredient(new RecipeChoice.ExactChoice(juulPod));

        // Juul Recipe

        ItemStack bottle1 = new ItemStack(Material.POTION, 1);
        PotionMeta bottleMeta1 = (PotionMeta) bottle1.getItemMeta();

        bottleMeta1.setDisplayName("Juul");
        bottleMeta1.addEnchant(Enchantment.PIERCING, 10, true);
        bottleMeta1.setBasePotionData(new PotionData(PotionType.WATER));

        bottle1.setItemMeta(bottleMeta1);

        ShapedRecipe bottleRecipe1 = new ShapedRecipe(bottle1);
        bottleRecipe1.shape(" % "," B "," % ");

        bottleRecipe1.setIngredient('%', Material.BLACKSTONE);
        bottleRecipe1.setIngredient('B', Material.GLASS_BOTTLE);

        // Registering Events / Recipes

        Bukkit.getServer().addRecipe(bottleRecipe);
        Bukkit.getServer().addRecipe(bottleRecipe1);
        Bukkit.getServer().addRecipe(juulPodRecipe);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerUseJuul(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
