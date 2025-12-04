<div align="center">
  <img src="/assets/banner.png" alt="Banner" style="max-width: 100%; height: auto;" />
</div>

---

## 🌟 Features

- Create, update, and remove waypoints
- Custom names, icons, colors, and styling
- Lightweight and fast integration
- Fully documented API structure

- ⏯ Video: [Link](https://www.youtube.com/watch?v=FkM1QbLYQs0)

## 🧰 Requirements

- Server version 1.21.6+
- [PacketEvents](https://github.com/retrooper/packetevents)
- [Java](https://www.java.com/en/) 21+

## 📦 Installation

Add the repository and dependency to your project:

#### Maven Repository

```xml
<repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/fajzu1/Waypoint-API</url>
</repository>
```

#### Maven Dependency

```xml
<dependency>
  <groupId>io.github.fajzu</groupId>
  <artifactId>waypoint-api</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

---

## ⚙️ Usage

#### Injecting a WaypointAPI into Plugin

```java
WaypointProvider waypointProvider = WaypointProvider.init(this);
```

#### Creating a Waypoint

```java
Waypoint waypoint = WaypointBuilder
      .builder()
      .name("Test")
      .location(new Location(
            "world",
            0,
            100,
            0
      ))
      .style(WaypointStyle.DEFAULT)
      .color(Color.BLACK)
      .build();

waypointProvider.sendWaypoint(player, waypoint);
```

#### Updating a Waypoint

```java
Waypoint waypoint = waypointProvider.find(player.getUniqueId(), "UUID");

waypoint.color(Color.BLUE);
waypoint.style(WaypointStyle.of("custom", "path"));
waypoint.location(new Location(
                 null,
                 10,
                 200,
                 10
));

waypointProvider.update(player, waypoint);
```

#### Removing a Waypoint

```java
Waypoint waypoint = waypointProvider.find(player.getUniqueId(), "UUID");

waypointProvider.removeWaypoint(player, waypoint);
```

---

## 🐞 Found a Bug?

Spotted an issue? Report it via Discord: **fizuxd** 💬

---

## 🗂️ TODO

- Add Bukkit Events handling for creating/updating/removing waypoint

## ❤️ Thanks for Your Support!

If you enjoy using Waypoint-API, leave a ⭐ on the GitHub repo and share it with your friends!
