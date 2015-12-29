package net.itsprime;

import com.skype.Skype;
import com.skype.SkypeException;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jham on 12/28/2015.
 */
public class CMDs implements CommandExecutor {

    String theMessage;
    Map<String, String> list = new HashMap<>();
    String friendRequestMSG = "Hello, my name is HQGaming-Skype-Bot! Seems like you're a staff member.";
    boolean notOnList = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("skype")) {
                if (args.length == 0) {

                }
            }
            if (args[0].equalsIgnoreCase("message") && (args.length > 2)) {
                try {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        builder.append(" ").append(args[i]);
                        theMessage = builder.toString();
                    }
                    for (Map.Entry<String, String> entry : list.entrySet()) {
                        if (entry.getValue().equalsIgnoreCase(args[1])) {
                            Skype.getUser(entry.getKey()).send(theMessage);
                            p.sendMessage("Message has been sent to " + Skype.getUser(args[1]) + " " + theMessage);
                        } else if (!(entry.getValue().equalsIgnoreCase(args[1]))) {
                            notOnList = true;
                            System.out.println("Message error plus " + args[1]);
                        }
                    }
                    if (notOnList == true) {
                        p.sendMessage("Wrong username. Try /skype list to see all active staff on Skype.");
                    }
                } catch (SkypeException e) {
                    e.printStackTrace();
                }
            }
            if(args[0].equalsIgnoreCase("add")) {
                if((args.length < 2) && (list.containsKey(args[1]) || list.containsValue(args[2]))){
                    p.sendMessage("This skype ID or player is already on the skype list.");
                    return true;
                }
                else if (!(list.containsKey(args[1])) && args.length == 2) {
                    setSkypeID(args[1], p);
                    getConfig().set("skypeID", args[1]);
                    getConfig().set("player", p.getName());
                    try {
                        this.sendFriendRequest(args[1]);
                    } catch (SkypeException e) {
                        e.printStackTrace();
                    }
                    p.sendMessage("SkypeID: " + args[1] + " has been added to the user " + p.getName());
                }
                else if (!(list.containsKey(args[1]) && list.containsValue(args[2])) && args.length == 3) {
                    System.out.println(args.length);
                    setSkypeID(args[1], args[2]);
                    for(int i=0; i < getConfig().getInt("list."); i++) {
                        getConfig().set("list." + i + ".skypeID", args[1]);
                        getConfig().set("list." + i + ".player", args[2]);
                    }
                    p.sendMessage("SkypeID: " + args[1] + " has been added to the user " + args[2]);
                    try {
                        this.sendFriendRequest(args[1]);
                    } catch (SkypeException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    p.sendMessage("Ussage: /skype add <skypeID> or /skype add <skypeID> <MC-Name>");
                }
            }
        }
        return true;
    }

    public String getList(Player p) throws SkypeException {
        for(Map.Entry<String,String> entry : list.entrySet()) {
            String _SkypeStatus = Skype.getUser(entry.getKey()).getStatus().toString();
            String _Name = entry.getValue();
            p.sendMessage(ChatColor.GREEN + "------------------------------------");
            p.sendMessage(ChatColor.GREEN + "Name: " + _Name);
            p.sendMessage(ChatColor.GREEN + "Status: " + _SkypeStatus);
            p.sendMessage(ChatColor.GREEN + "------------------------------------");
        }
        return null;
    }

    public void setSkypeID(String skypeID, Player p){
        list.put(skypeID, p.getName());
    }

    public void setSkypeID(String skypeID, String p){
        list.put(skypeID, p);
    }

    public void sendFriendRequest(String skypeId) throws SkypeException {
        Skype.getContactList().addFriend(skypeId, friendRequestMSG);
    }

}
