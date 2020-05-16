package tsp.godseye.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.parser.ParseException;
import tsp.godseye.api.GodAPI;
import tsp.godseye.api.IPAPI;
import tsp.godseye.api.ResultSet;
import tsp.godseye.gui.Button;
import tsp.godseye.gui.PagedPane;
import tsp.godseye.util.FileUtils;
import tsp.godseye.util.GUtils;
import tsp.godseye.util.LogLevel;
import tsp.godseye.util.XMaterial;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class Command_godseye implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("godseye.admin")) {
                if (sender instanceof Player) {
                    GodAPI.openGodsEye((Player) sender);
                    return true;
                }
                GUtils.sendMessage(sender, "&cOnly in-game players can open godseye!");
                return true;
            }
            GUtils.sendMessage(sender, "&cNo permission!");
            return true;
        }
        String sub = args[0];
        if (sub.equalsIgnoreCase("version") || sub.equalsIgnoreCase("ver")) {
            GUtils.sendMessage(sender, "Running &6GodsEye &7on &6" + Bukkit.getServer().getName());
            GUtils.sendMessage(sender, "Version &6" + GUtils.CODENAME + " - " + GUtils.getInstance().getDescription().getVersion());
            GUtils.sendMessage(sender, "Created by &6" + GUtils.DEVELOPERS);
            return true;
        }
        if (!sender.hasPermission("godseye.admin")) {
            GUtils.sendMessage(sender, "&cNo permission!");
            return true;
        }
        // Help Menu
        if (sub.equalsIgnoreCase("help")) {
            GUtils.sendMessage(sender, "-=============== [ &b&lHelp Menu &7] ===============-");
            GUtils.sendMessage(sender, "&oParameters: &6[optional] &c<required>");
            GUtils.sendMessage(sender, " ");
            GUtils.sendMessage(sender, " &b- /godseye &7- Open GodsEye");
            GUtils.sendMessage(sender, " &b- /godseye &6[fetch] &c<user> &7- Check online user for information");
            GUtils.sendMessage(sender, "-===================================================-");
            return true;
        }
        // Check
        if (sub.equalsIgnoreCase("fetch")) {
            if (args.length >= 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    GUtils.sendMessage(sender, "&cUser not online!");
                    return true;
                }
                String ip = target.getAddress().getHostName();
                GUtils.sendMessage(sender, "Checking &6" + target.getName() + " &7(&6" + ip + "&7)");
                ResultSet results = null;
                if (FileUtils.getBoolean("fetch.advanced")) {
                    try {
                        results = new ResultSet(IPAPI.check(ip));
                    } catch (IOException | ParseException e) {
                        GUtils.sendMessage(sender, "&cSomething went wrong, check console!");
                        GUtils.log(LogLevel.ERROR, "Could not fetch information for " + target.getName() + "(" + ip + ") | Stack Trace: ");
                        e.printStackTrace();
                        return true;
                    }
                }
                GUtils.sendMessage(sender, "-=============== [ &5GodsEye &7| &5" + ip + " &7] ===============-");

                // Basic Information
                if (FileUtils.getBoolean("fetch.basic")) {
                    DecimalFormat df = new DecimalFormat(FileUtils.getString("decimalFormat"));
                    GUtils.sendMessage(sender, "Query results for &6" + target.getName());
                    GUtils.sendMessage(sender, " ");
                    GUtils.sendMessage(sender, "&o&5[ Basic Information ]");
                    GUtils.sendMessage(sender, "IP > &b" + ip);
                    GUtils.sendMessage(sender, "UUID > &b" + target.getUniqueId().toString());
                    GUtils.sendMessage(sender, "GameMode > " + target.getGameMode().toString());
                    GUtils.sendMessage(sender, "Health (Absorption) > &b" + target.getHealth() + "&7/&b" + target.getHealthScale() + " &7(&e" + target.getAbsorptionAmount() + "&7)");
                    GUtils.sendMessage(sender, "Food (Saturation) > &b" + target.getFoodLevel() + "&7/&b20 &7(&e" + target.getSaturation() + "&7)");
                    GUtils.sendMessage(sender, "XP > &b" + target.getExp() + " &7(&b" + target.getTotalExperience() + "&7)");GUtils.sendMessage(sender, "Speed (F:FlySpeed | W:WalkSpeed) > F:&b" + df.format(target.getFlySpeed()) + " &7| W:&b" + df.format(target.getWalkSpeed()));
                    GUtils.sendMessage(sender, "Air > &b" + target.getRemainingAir() + "&7/&b" + target.getMaximumAir());
                    GUtils.sendMessage(sender, "Client View Distance > &b" + target.getClientViewDistance());
                    GUtils.sendMessage(sender, "Location (X/Y/Z/F) > &b" + df.format(target.getLocation().getX()) + " " + df.format(target.getLocation().getY()) + " " + df.format(target.getLocation().getZ()) + " " + target.getFacing().name());
                    GUtils.sendMessage(sender, "Can Pickup Items > &b" + GUtils.replaceBoolean(target.getCanPickupItems()));
                    GUtils.sendMessage(sender, "Allowed Flight > &b" + GUtils.replaceBoolean(target.getAllowFlight()));
                    GUtils.sendMessage(sender, "Time (Offset) > &b" + target.getPlayerTime() + " &7(&e" + target.getPlayerTimeOffset() + "&7)") ;
                    GUtils.sendMessage(sender, "Locale > &b" + target.getLocale());
                    GUtils.sendMessage(sender, " ");
                }

                // Advanced Information
                if (FileUtils.getBoolean("fetch.advanced")) {
                    String empty = FileUtils.getString("emptyResultReplacement");
                    String message = results.getMessage();
                    String continent = results.getContinent().isEmpty() ? empty : results.getContinent();
                    String continentCode = results.getContinentCode().isEmpty() ? empty : results.getContinentCode();
                    String country = results.getCountry().isEmpty() ? empty : results.getCountry();
                    String countryCode = results.getCountryCode().isEmpty() ? empty : results.getCountryCode();
                    String region = results.getRegion().isEmpty() ? empty : results.getRegion();
                    String regionName = results.getRegionName().isEmpty() ? empty : results.getRegionName();
                    String city = results.getCity().isEmpty() ? empty : results.getCity();
                    String district = results.getDistrict().isEmpty() ? empty : results.getDistrict();
                    String zip = results.getZIP().isEmpty() ? empty : results.getZIP();
                    String lat = results.getLat().isEmpty() ? empty : results.getLat();
                    String lon = results.getLon().isEmpty() ? empty : results.getLon();
                    String timezone = results.getTimezone().isEmpty() ? empty : results.getTimezone();
                    String currency = results.getCurrency().isEmpty() ? empty : results.getCurrency();
                    String isp = results.getISP().isEmpty() ? empty : results.getISP();
                    String org = results.getOrg().isEmpty() ? empty : results.getOrg();
                    String as = results.getAS().isEmpty() ? empty : results.getAS();
                    String asname = results.getASName().isEmpty() ? empty : results.getASName();
                    String reverse = results.getASName().isEmpty() ? empty : results.getReverse();

                    GUtils.sendMessage(sender, "&o&5[ Advanced Information ]");
                    GUtils.sendMessage(sender, "Fetch Status > &6" + results.getStatus());
                    GUtils.sendMessage(sender, "Message > &6" + message);
                    GUtils.sendMessage(sender, "Continent > &6" + continent);
                    GUtils.sendMessage(sender, "Continent Code > &6" + continentCode);
                    GUtils.sendMessage(sender, "Country > &6" + country);
                    GUtils.sendMessage(sender, "Country Code > &6" + countryCode);
                    GUtils.sendMessage(sender, "Region > &6" + region);
                    GUtils.sendMessage(sender, "Region Name > &6" + regionName);
                    GUtils.sendMessage(sender, "City > &6" + city);
                    GUtils.sendMessage(sender, "District > &6" + district);
                    GUtils.sendMessage(sender, "ZIP > &6" + zip);
                    GUtils.sendMessage(sender, "Latitude > &6" + lat);
                    GUtils.sendMessage(sender, "Longitude > &6" + lon);
                    GUtils.sendMessage(sender, "Timezone > &6" + timezone);
                    GUtils.sendMessage(sender, "Currency > &6" + currency);
                    GUtils.sendMessage(sender, "ISP > &6" + isp);
                    GUtils.sendMessage(sender, "ORG > &6" + org);
                    GUtils.sendMessage(sender, "AS > &6" + as);
                    GUtils.sendMessage(sender, "AS Name > &6" + asname);
                    GUtils.sendMessage(sender, "Reverse > &6" + reverse);
                    GUtils.sendMessage(sender, "Mobile > &6" + GUtils.replaceBoolean(results.getMobile()));
                    GUtils.sendMessage(sender, "Hosting > &6" + GUtils.replaceBoolean(results.getHosting()));
                    GUtils.sendMessage(sender, "Proxy > &6" + GUtils.replaceBoolean(results.getProxy()));
                }

                GUtils.sendMessage(sender, "-=======================================================-");
                return true;
            }
            GUtils.sendMessage(sender, "&cInvalid Usage! Use '/godseye help' for help.");
            return true;
        }
        GUtils.sendMessage(sender, "&cInvalid Usage! Use '/godseye help' for help.");
        return true;
    }

}
