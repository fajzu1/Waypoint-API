package io.github.fajzu;

import java.util.List;

public class WaypointConstants {

    public static final List<String> DEPENDENCY_NOT_FOUND = List.of(
            "Initialization failed due to the following issues:",
            "",
            "- Missing dependency: PacketEvents plugin was not found.",
            "  Please ensure that PacketEvents is installed and loaded before starting the server.",
            "",
            "Waypoint cannot be enabled until these issues are resolved."
    );

    public static final List<String> OLD_VERSION = List.of(
            "Initialization failed due to the following issues:",
            "",
            "- Unsupported server version: Your current version is too old.",
            "  Minimum supported: 1.21.5+.",
            "",
            "Waypoint cannot be enabled until these issues are resolved."
    );

}
