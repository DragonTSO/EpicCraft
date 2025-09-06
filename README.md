# EpicCraftItem

Advanced crafting system for Minecraft 1.20.4 servers by DragonDirt.

[![GitHub](https://img.shields.io/badge/GitHub-DragonTSO-blue?style=flat-square&logo=github)](https://github.com/DragonTSO/EpicCraft)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.4-green?style=flat-square&logo=minecraft)](https://minecraft.net)
[![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)](https://openjdk.java.net)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red?style=flat-square&logo=apache-maven)](https://maven.apache.org)

## Features

- **Custom Crafting System**: Create complex crafting recipes with multiple requirements
- **Multi-level GUI**: Main menu, categories, item craft, and detail views
- **Probability System**: Set success/failure rates for crafting
- **Plugin Integration**: Support for AdvancedEnchantments, MMOItems, and FindItem
- **Item Return System**: Automatically return items when crafting fails
- **Admin Tools**: In-game editing and management tools
- **Debug Mode**: Comprehensive debugging system

## Requirements

- **Minecraft**: 1.20.4
- **Java**: 17+
- **Server**: Spigot/Paper
- **Optional Plugins**: AdvancedEnchantments, MMOItems, FindItem

## Installation

### From GitHub Releases
1. Download the latest JAR file from [releases](https://github.com/DragonTSO/EpicCraft/releases)
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configure the plugin using the generated config files

### From Source
1. Clone the repository:
   ```bash
   git clone https://github.com/DragonTSO/EpicCraft.git
   cd EpicCraft
   ```
2. Build the plugin:
   ```bash
   mvn clean package
   ```
3. Find the JAR file in `target/EpicCraftItem-1.1.jar`
4. Copy to your server's `plugins` folder

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/ecraft` | Open main menu | `epiccraftitem.user` |
| `/ecraft help` | Show help | `epiccraftitem.user` |
| `/ecraft open <category>` | Open category directly | `epiccraftitem.user` |
| `/ecraft edit category` | Edit categories | `epiccraftitem.edit` |
| `/ecraft edit item <category>` | Edit items in category | `epiccraftitem.edit` |
| `/ecraft debug` | Toggle debug mode | `epiccraftitem.admin` |
| `/ecraft reload` | Reload configuration | `epiccraftitem.admin` |

## Permissions

- `epiccraftitem.user` - Basic access to crafting features
- `epiccraftitem.edit` - Permission to edit craft recipes
- `epiccraftitem.admin` - Full administrative access

## Configuration

### config.yml
```yaml
# Debug mode
DefaultDebug: true

# Return CE Book settings
ReturnCeBook:
  Enable: true
  Success: 100
  Destroy: 0

# Return MMO Gem settings
ReturnMMOGem: true
ReturnCeBookWhenFailed: true
ReturnMMOGemWhenFailed: true

# Lore requirements display
LoreRequire:
  - "&f"
  - "&e> Yêu cầu: &a<amount>"
```

### categories.yml
```yaml
categories:
  weapons:
    name: "&c&lVũ khí"
    slot: 10
    item:
      type: "DIAMOND_SWORD"
      name: "&c&lVũ khí"
      lore:
        - "&7Danh mục vũ khí"
```

## Building

1. Clone the repository
2. Run `mvn clean package`
3. Find the JAR file in `target/` directory

## API

The plugin provides a comprehensive API for developers:

```java
// Get plugin instance
EpicCraftItem plugin = EpicCraftItem.getInstance();

// Get API instance
EpicCraftApi api = plugin.getEpicCraftApi();

// Create custom GUI
GuiMainMenu gui = api.createGuiMainMenu(player);

// Check plugin integrations
AEAPI aeApi = api.getAeapi();
MMOItemsAPI mmoApi = api.getMmoItemsAPI();
FindItemApi findApi = api.getFindItemApi();
```

## Package Structure

The plugin uses the package structure: `com.dragondirt.epiccraftitem.*`

## Events

The plugin fires custom events for integration:

- `PrepareCraftItemEvent` - Fired when craft is prepared
- `CraftEvent` - Fired when craft is successful
- `CraftMissingItemEvent` - Fired when craft fails due to missing items

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Support

For support, bug reports, or feature requests, please create an issue on the [GitHub repository](https://github.com/DragonTSO/EpicCraft/issues).

## Repository

- **GitHub**: [https://github.com/DragonTSO/EpicCraft](https://github.com/DragonTSO/EpicCraft)
- **Issues**: [https://github.com/DragonTSO/EpicCraft/issues](https://github.com/DragonTSO/EpicCraft/issues)
- **Releases**: [https://github.com/DragonTSO/EpicCraft/releases](https://github.com/DragonTSO/EpicCraft/releases)

## License

This project is licensed under the MIT License - see the LICENSE file for details.