Hyeconomy is a diamond economy plugin uses MariaDB for database storage and async.

## Features
- Economy system for managing in-game currency (diamonds).
- Deposit and withdraw diamonds.
- Send diamonds to other players.
- Check your current balance.
- View a leaderboard of top players.
- Efficient run asynchronously, ensuring that economy-related actions do not cause any lag on your server.

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

## Support

If you encounter any issues or have questions, please feel free to [open an issue](https://github.com/MidnightTale/hyeconomy/issues) on the GitHub repository.

## License

This plugin is open-source and available under the [MIT License](LICENSE).

