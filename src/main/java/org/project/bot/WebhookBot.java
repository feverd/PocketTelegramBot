package org.project.bot;

import org.project.service.PropertiesReader;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WebhookBot extends TelegramWebhookBot {
    private String botToken;
    private String botUsername;

    public WebhookBot(String botToken, String botUsername) {
        PropertiesReader service = new PropertiesReader();
        this.botToken = service.getApplicationPropertyValue("bot.token");
        this.botUsername = service.getApplicationPropertyValue("bot.name");
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {

    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
