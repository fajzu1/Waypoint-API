package io.github.fajzu;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.world.waypoint.TrackedWaypoint;
import com.github.retrooper.packetevents.util.PEVersion;
import com.google.common.base.Stopwatch;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaypointProvider {

    private final static Logger LOGGER = Logger.getLogger("waypoint-api");

    private WaypointDispatcher waypointDispatcher;

    private TrackedWaypointFactory trackedWaypointFactory;

    private WaypointProvider(final Plugin plugin) {
        final Stopwatch stopWatch = Stopwatch.createStarted();

        if (!plugin.getServer().getPluginManager().isPluginEnabled("packetevents")) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));

            PacketEvents.getAPI().load();
            return;
        }

        if (PacketEvents.getAPI().getVersion().isOlderThan(PEVersion.fromString("1.21.6"))) {
            WaypointConstants.OLD_VERSION.forEach(string -> LOGGER.log(Level.SEVERE, string));

            plugin.getServer().shutdown();
            return;
        }

        PacketEvents.getAPI().init();

        this.trackedWaypointFactory = new TrackedWaypointFactory();

        this.waypointDispatcher = new WaypointDispatcher(this.trackedWaypointFactory);

        LOGGER.log(Level.FINE, "Successfully Injected WaypointProvider in " + stopWatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static WaypointProvider init(final @NotNull Plugin plugin) {
        return new WaypointProvider(plugin);
    }

    public void track(final @NotNull Player player,
                      final @NotNull Waypoint waypoint) {
        this.waypointDispatcher.track(player, waypoint);
    }

    public void hide(final @NotNull Player player,
                     final @NotNull Waypoint waypoint) {
        this.waypointDispatcher.hide(player, waypoint);
    }

    public void update(final @NotNull Player player,
                       final @NotNull Waypoint waypoint) {
        this.waypointDispatcher.update(player, waypoint);
    }

    public TrackedWaypoint toTracked(final @NotNull Waypoint waypoint) {
        return trackedWaypointFactory.create(waypoint);
    }

    public WaypointDispatcher waypointFactory() {
        return this.waypointDispatcher;
    }

    public TrackedWaypointFactory trackedWaypointFactory() {
        return this.trackedWaypointFactory;
    }
}
