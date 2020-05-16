package tsp.godseye.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.parser.ParseException;
import tsp.godseye.gui.Button;
import tsp.godseye.gui.PagedPane;
import tsp.godseye.util.XMaterial;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GodAPI {

    public static ResultSet getResults(String ip) throws IOException, ParseException {
        return new ResultSet(IPAPI.check(ip));
    }

    public static void openGodsEye(Player player) {
        PagedPane gui = new PagedPane(4, 4, "&5&lGodsEye");
        Set<ItemStack> items = new HashSet<>();
        for (Player online : Bukkit.getOnlinePlayers()) {
            ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(online.getUniqueId()));
            meta.setDisplayName(ChatColor.GOLD + online.getName());
            if (online.hasPermission("godseye.admin")) {
                meta.setDisplayName(ChatColor.RED + online.getName());
            }
            head.setItemMeta(meta);
            items.add(head);
        }

        for (ItemStack item : items) {
            gui.addButton(new Button(item, e -> {
                HumanEntity who = e.getWhoClicked();

                if (who instanceof Player) {
                    Player p = (Player) who;
                    p.closeInventory();
                    Bukkit.dispatchCommand(p, "godseye fetch " + ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                }
            }));
        }

        gui.open(player);
    }

}
