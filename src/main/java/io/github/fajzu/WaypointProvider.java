package io.github.fajzu;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.world.waypoint.TrackedWaypoint;
import com.github.retrooper.packetevents.util.PEVersion;
import com.google.common.base.Stopwatch;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaypointProvider {

    private final static Logger LOGGER = Logger.getLogger("waypoint-api");

    private WaypointService waypointService;
    private WaypointDispatcher waypointDispatcher;

    private TrackedWaypointFactory trackedWaypointFactory;

    private WaypointProvider(final Plugin plugin) {
        final Stopwatch stopWatch = Stopwatch.createStarted();

        if (!plugin.getServer().getPluginManager().isPluginEnabled("packetevents")) {
            WaypointConstants.DEPENDENCY_NOT_FOUND.forEach(string -> LOGGER.log(Level.SEVERE, string));

            plugin.getServer().shutdown();
            return;
        }

        if (PacketEvents.getAPI().getVersion().isOlderThan(PEVersion.fromString("1.21.5"))) {
            WaypointConstants.OLD_VERSION.forEach(string -> LOGGER.log(Level.SEVERE, string));

            plugin.getServer().shutdown();
            return;
        }

        final WaypointStyleResolver styleResolver = new WaypointStyleResolver();

        this.trackedWaypointFactory = new TrackedWaypointFactory(styleResolver);

        this.waypointService = new WaypointService();
        this.waypointDispatcher = new WaypointDispatcher(this.trackedWaypointFactory);

        LOGGER.log(Level.FINE, "Successfully Injected WaypointProvider in " + stopWatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static WaypointProvider init(final @NotNull Plugin plugin) {
        return new WaypointProvider(plugin);
    }

    public void createWaypoint(final @NotNull Player player,
                               final @NotNull Waypoint waypoint) {
        this.waypointService.create(player.getUniqueId(), waypoint);

        this.waypointDispatcher.track(player, waypoint);
    }

    public void removeWaypoint(final @NotNull Player player,
                               final @NotNull Waypoint waypoint) {
        this.waypointService.remove(player.getUniqueId(), waypoint);

        this.waypointDispatcher.hide(player, waypoint);
    }

    public Map<UUID, Waypoint> findAll(final @NotNull UUID uuid) {
        return this.waypointService.findAll(uuid);
    }

    public Waypoint find(final @NotNull UUID playerUUID, @NotNull UUID uuid) {
        return this.waypointService.find(playerUUID, uuid);
    }

    public Waypoint find(final @NotNull UUID uuid, @NotNull String name) {
        return this.waypointService.find(uuid, name);
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

    public WaypointService waypointService() {
        return this.waypointService;
    }

    public TrackedWaypointFactory trackedWaypointFactory() {
        return this.trackedWaypointFactory;
    }
}
