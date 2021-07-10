# Telegram Pocket Bot
____

This Telegram bot allows you to save links in your Pocket account using only Telegram!

## Bot
____

This bot uses [Telegram Bot Java Library](https://github.com/rubenlagus/TelegramBots), which is based on [Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api).
Bot implements long polling method at the development stage.

## Pocket
____
To communicate with Pocket, bot uses official [Pocket API](https://getpocket.com/developer/docs/overview) and implements key methods:
- Pocket [Authentication](https://getpocket.com/developer/docs/authentication)
- Pocket [Add](https://getpocket.com/developer/docs/v3/add)

## Database
____
To communicate with Database, bot implements DAO interface. That's mean that any kind of database can be used. 

## Server
____
For first steps in project as a server was chosen Jetty.

____
**For nowadays the project is under development and need some changes**


