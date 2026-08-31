package example;

import io.github.tamawish.rwr.api.RwrApi;
import io.github.tamawish.rwr.api.event.ResourceWorldPostResetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/** Compile-only consumer fixture using only Bukkit and the supported RWR API. */
final class RwrApiConsumer implements Listener {
    private final JavaPlugin plugin;

    RwrApiConsumer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    void inspectWorlds() {
        RwrApi.find(plugin.getServer()).ifPresent(api -> api.managedWorlds().forEach(world ->
                plugin.getLogger().info(world.id() + ": " + world.state())));
    }

    @EventHandler
    void onReset(ResourceWorldPostResetEvent event) {
        plugin.getLogger().info(event.getWorldId() + " reset success: " + event.isSuccessful());
    }
}
