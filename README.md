
## Features
- Economy system for managing in-game currency (diamonds).
- Deposit and withdraw diamonds.
- Send diamonds to other players.
- Check your current balance.
- View a leaderboard of top players.
- Efficient run asynchronously, ensuring that economy-related actions do not cause any lag on your server.
- Support [MiniMessage](https://docs.advntr.dev/minimessage/format.html) format 
- PlaceholderAPI supported

## Server Compatibility
- **Paper / Folia** and any forks.
- ⚠️ **Spigot**: untested.
- ⚠️ **ฺBukkit**: untested.

## Database Storage

Hyeconomy utilizes MariaDB for database storage. This ensures reliable and efficient data storage for player balances and transactions.

## Installation

To install Hyeconomy on your server, follow these steps:

1. Download the plugin JAR file from the [Releases](https://github.com/MidnightTale/hyeconomy/releases) section.
2. Place the JAR file in your server's `plugins` folder.
3. Start or reload your server.

## Commands

- `/balance`: Check your current balance.
- `/deposit <amount>`: Deposit diamonds into your account.
- `/withdraw <amount>`: Withdraw diamonds from your account.
- `/send <player> <amount>`: Send diamonds to another player.
- `/top`: View the leaderboard of top players.

## Permissions

- `hyeconomy.balance`: Permission to use the `/balance` command.
- `hyeconomy.deposit`: Permission to use the `/deposit` command.
- `hyeconomy.withdraw`: Permission to use the `/withdraw` command.
- `hyeconomy.send`: Permission to use the `/send` command.
- `hyeconomy.top`: Permission to use the `/top` command.

## Configuration

You can customize various aspects of the plugin, including messages and database settings, by editing the `config.yml` and `messages.yml` files in the plugin's folder.

## PlaceholderAPI Integration
install [placeholderapi](https://www.spigotmc.org/resources/placeholderapi.6245/)
  - display a player's balance `%hyeconomy_balance%`.
  - display a specific player's balance by name, use `%hyeconomy_balance_PLAYERNAME%` (replace `PLAYERNAME` with the actual player's name).
  - display the balance of the top 10 players, use `%hyeconomy_balance_top_1%`, `%hyeconomy_balance_top_2%`, and so on, up to `%hyeconomy_balance_top_10%`.

Remember to replace `PLAYERNAME` with the actual player name you want to query in the second type of placeholder.

## Support

If you encounter any issues or have questions, please feel free to [open an issue](https://github.com/MidnightTale/hyeconomy/issues) on the GitHub repository.

## License

This plugin is open-source and available under the [MIT License](LICENSE).

## Developer API
### Information
Apache Maven `pom.xml`
```xml
<repository>
    <id>hyeconomy</id>
    <url>https://repo.hynse.xyz/repository/maven-releases/</url>
</repository>
<dependency>
  <groupId>xyz.hynse</groupId>
  <artifactId>api-hyeconomy</artifactId>
  <version>1.0-SNAPSHOT-4</version>
</dependency>
```
Gradle Groovy DSL `build.gradle`
```gradlee
repositories {
    maven { url = "https://repo.hynse.xyz/repository/maven-releases/"}
}
dependencies { 
    implementation 'xyz.hynse:api-hyeconomy:1.0-SNAPSHOT-4'
}
```


### Example
```java
import xyz.hynse.hyeconomy.API.HyeconomyAPI;

// when you need to get or set player balances:
HyeconomyAPI hyeconomyAPI = Hyeconomy.getAPI();

// Get a player's balance by UUID
UUID playerUUID = /* UUID of the player */;
int balance = hyeconomyAPI.getPlayerBalance(playerUUID);

// Set a player's balance by UUID
int newBalance = /* The new balance */;
hyeconomyAPI.setPlayerBalance(playerUUID, newBalance);```
