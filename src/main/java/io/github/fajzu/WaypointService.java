package io.github.fajzu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WaypointService {

    private final Map<UUID, Map<UUID, Waypoint>> waypointMap = new ConcurrentHashMap<>();

    public void create(final UUID uuid,
                       final Waypoint waypoint) {
        this.waypointMap
                .computeIfAbsent(uuid, k -> new ConcurrentHashMap<>())
                .put(waypoint.uuid(), waypoint);
    }

    public void remove(final UUID uuid,
                       final Waypoint waypoint) {
        this.waypointMap.computeIfAbsent(uuid, k -> new HashMap<>()).remove(waypoint.uuid());
    }

    public Waypoint find(final UUID playerUUID,
                         final UUID uuid) {
        return this.waypointMap.computeIfAbsent(playerUUID, k -> new HashMap<>()).get(uuid);
    }

    public Waypoint find(final UUID playerUUID,
                         final String name) {
        return this.waypointMap
                .computeIfAbsent(playerUUID, k -> new HashMap<>())
                .values()
                .stream()
                .filter(waypoint -> waypoint.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }


    public Map<UUID, Waypoint> findAll(final UUID uuid) {
        return new ConcurrentHashMap<>(this.waypointMap.get(uuid));
    }

    public void removeAll(final UUID uuid) {
        this.waypointMap.remove(uuid);
    }
}
