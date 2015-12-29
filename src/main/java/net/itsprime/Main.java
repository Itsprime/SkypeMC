package net.itsprime;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Jham on 12/28/2015.
 */
public class Main extends JavaPlugin {

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        try {
            new SkypeLogin().skypeLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getCommand("skype").setExecutor(new CMDs());
    }

    public void onDisable() {
        saveConfig();
    }


}
