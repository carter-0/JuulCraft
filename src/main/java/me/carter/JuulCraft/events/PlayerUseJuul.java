package me.carter.JuulCraft.events;

import me.carter.JuulCraft.JuulCraft;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class PlayerUseJuul implements Listener {
    private JuulCraft mainClass = JuulCraft.getPlugin(JuulCraft.class);

    @EventHandler
    public void onPlayerUseJuul(PlayerItemConsumeEvent e) {
        if (e.getItem().getItemMeta().getDisplayName().equals("Juul")) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 2.0F);
            e.getPlayer().spawnParticle(Particle.REDSTONE, e.getPlayer().getLocation().add(new Vector(0, 1.7, 0)), 3000, dustOptions);
            e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE+"You have taken a hit of the Juul!");

            ItemStack newItemForData = e.getItem();
            PersistentDataContainer persistentDataContainer = newItemForData.getItemMeta().getPersistentDataContainer();

            if (persistentDataContainer.get(mainClass.key, PersistentDataType.INTEGER) != null) {
                int dur = persistentDataContainer.get(mainClass.key, PersistentDataType.INTEGER);

                ItemStack newItemA = e.getItem();
                ItemMeta newMeta = newItemA.getItemMeta();
                newMeta.getPersistentDataContainer().set(mainClass.key, PersistentDataType.INTEGER, dur + 1);
                newItemA.setItemMeta(newMeta);

                e.setItem(newItemA);

                if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.POTION) {
                    e.getPlayer().getInventory().setItemInMainHand(newItemA);
                } else if (e.getPlayer().getInventory().getItemInOffHand().getType() == Material.POTION) {
                    e.getPlayer().getInventory().setItemInOffHand(newItemA);
                }
                
                if ((dur + 1) > 9) {
                    ItemStack brokenJuul = new ItemStack(Material.GLASS_BOTTLE);
                    ItemMeta brokenMeta = brokenJuul.getItemMeta();
                    brokenMeta.addEnchant(Enchantment.PIERCING, 10, true);
                    brokenMeta.setDisplayName("Empty Juul");

                    brokenJuul.setItemMeta(brokenMeta);

                    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.POTION) {
                        e.getPlayer().getInventory().setItemInMainHand(brokenJuul);
                    } else if (e.getPlayer().getInventory().getItemInOffHand().getType() == Material.POTION) {
                        e.getPlayer().getInventory().setItemInOffHand(brokenJuul);
                    }
                    e.getPlayer().sendMessage(ChatColor.RED+"Oh No! You ran out of Juul Pods");
                }
            } else {
                ItemStack ActualNewItem = e.getItem();

                ItemMeta newItem = ActualNewItem.getItemMeta();
                newItem.getPersistentDataContainer().set(mainClass.key, PersistentDataType.INTEGER, 1);
                ActualNewItem.setItemMeta(newItem);

                e.setItem(ActualNewItem);
                if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.POTION) {
                    e.getPlayer().getInventory().setItemInMainHand(ActualNewItem);
                } else if (e.getPlayer().getInventory().getItemInOffHand().getType() == Material.POTION) {
                    e.getPlayer().getInventory().setItemInOffHand(ActualNewItem);
                }
            }
            e.setCancelled(true);
        }
    }
}
