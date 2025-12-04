package io.github.fajzu;

import com.github.retrooper.packetevents.resources.ResourceLocation;

public class WaypointStyleResolver {

    public ResourceLocation resolve(final Waypoint waypoint) {
        if (waypoint.style() != null) {
            return waypoint.style().resourceLocation();
        }

        return WaypointStyle.DEFAULT.resourceLocation();
    }
}