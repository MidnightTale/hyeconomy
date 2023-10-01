![image](https://cdn.modrinth.com/data/HrYlg7yE/images/0971ab6c446a501992abca0b630864cd345f3cb6.png)
## Features
- Economy system for managing in-game currency (diamonds).
- Deposit and withdraw diamonds.
- Send diamonds to other players.
- Check your current balance.
- View a leaderboard of top players.
- Support [MiniMessage](https://docs.advntr.dev/minimessage/format.html) format 
- PlaceholderAPI supported

## Server Compatibility
- **Paper / Folia** and any forks.
- ⚠️ **Spigot**: untested.
- ⚠️ **ฺBukkit**: untested.

## Database Storage

Hyeconomy utilizes MariaDB for database storage. This ensures reliable and efficient data storage for player balances and transactions.

## Commands
### Player Command
- `/balance`: Check your current balance.
- `/balance <player>`: Check other current balance.
- `/deposit <amount>`: Deposit diamonds into your account.
- `/withdraw <amount>`: Withdraw diamonds from your account.
- `/send <player> <amount>`: Send diamonds to another player.
- `/top`: View the leaderboard of top players.
### Admin Command
- `/dgive <player> <amount>`: Give balance to player account.
- `/dtake <player> <amount>`: Take balance from player account.
- `/dset <player> <amount>`: Set player balance specific value.
- `/dreset <player>`: Reset specific player balance to 0.
- `/dreload`: Reload config and message.

## Permissions
- `hyeconomy.balance`: Permission to use the `/balance` command.
- `hyeconomy.balance.other`: Permission to use the `/balance` command check other.
- `hyeconomy.deposit`: Permission to use the `/deposit` command.
- `hyeconomy.withdraw`: Permission to use the `/withdraw` command.
- `hyeconomy.send`: Permission to use the `/send` command.
- `hyeconomy.top`: Permission to use the `/top` command.
- `hyeconomy.give`: Permission to use the `/dgive` command.
- `hyeconomy.take`: Permission to use the `/dtake` command.
- `hyeconomy.set`: Permission to use the `/dset` command.
- `hyeconomy.reset`: Permission to use the `/dreset` command.
- `hyeconomy.reload`: Permission to use the `/dreload` command.

## Configuration

You can customize various aspects of the plugin, including messages and database settings, by editing the `config.yml` and `messages.yml` files in the plugin's folder.

## PlaceholderAPI Integration
install [placeholderapi](https://www.spigotmc.org/resources/placeholderapi.6245/)
  - display a player's balance `%hyeconomy_balance%`.
  - display a specific player's balance by name, use `%hyeconomy_balance_PLAYERNAME%` (replace `PLAYERNAME` with the actual player's name).
  - display the username balance of the top 10 players, use `%hyeconomy_balance_top_username_1%`, `%hyeconomy_balance_top_username_2%`, and so on, up to `%hyeconomy_balance_top_username_10%`.
  - display the amount balance of the top 10 players, use `%hyeconomy_balance_top_amount_1%`, `%hyeconomy_balance_top_amount_2%`, and so on, up to `%hyeconomy_balance_top_amount_10%`.

Remember to replace `PLAYERNAME` with the actual player name you want to query in the second type of placeholder.

## Support

If you encounter any issues or have questions, please feel free to [open an issue](https://github.com/MidnightTale/hyeconomy/issues) on the GitHub repository.

## License

This plugin is open-source and available under the [MIT License](LICENSE).

## Developer API [![Latest Release](https://repo.hynse.xyz/api/badge/latest/releases/xyz/hynse/api-hyeconomy)](https://repo.hynse.xyz/api/latest/releases/xyz/hynse/api-hyeconomy)

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
  <version>1.2-SNAPSHOT-1</version>
</dependency>
```
Gradle Groovy DSL `build.gradle`
```gradlee
repositories {
    maven { url = "https://repo.hynse.xyz/repository/maven-releases/"}
}
dependencies { 
    compileOnly 'xyz.hynse:api-hyeconomy:1.2-SNAPSHOT-1'
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
hyeconomyAPI.setPlayerBalance(playerUUID, newBalance);

int amount = /*Amount want to add or subtract*/;
// Add a player's balance by UUID
HyeconomyAPI.addToPlayerBalance(UUID playerUUID, amount)
        
// subtract a player's balance by UUID
HyeconomyAPI.subtractFromPlayerBalance(UUID playerUUID, amount)
```
