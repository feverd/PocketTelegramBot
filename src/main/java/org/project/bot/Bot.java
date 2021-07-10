package org.project.bot;

import org.project.dao.PocketUserDao;
import org.project.entity.PocketCode;
import org.project.entity.PocketUser;
import org.project.pocket.data.AddItemData;
import org.project.pocket.data.AppCodeData;
import org.project.pocket.request.PocketRequest;
import org.project.service.ApplicationPropertiesReader;
import org.project.service.BotMessagesReader;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityManager;

public class Bot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;
    private PocketUserDao pocketUserDao;
    private PocketRequest pocketRequest;


    public Bot(EntityManager manager) {
        this.botToken = ApplicationPropertiesReader.getProperty("bot.token");
        this.botUsername = ApplicationPropertiesReader.getProperty("bot.name");
        pocketUserDao = new PocketUserDao(manager);
        pocketRequest = new PocketRequest();
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
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            Long id = update.getMessage().getFrom().getId();
            message.setChatId(id.toString());

            String updateMessageText = update.getMessage().getText();


            if (isCommand(updateMessageText)) {
                message.setText(executeCommandAndGetMessage(updateMessageText, id));
            } else {
                if (isUrl(updateMessageText) && isUser(id)) {
                    message.setText(sendUrlAndGetMessage(updateMessageText, id));
                } else {
                    message.setText(BotMessagesReader.getProperty("bot.message.help"));
                }
            }
            sendMessage(message);
        }

    }


    private boolean isCommand(String updateMessageText) {
        boolean result = false;
        if (updateMessageText.startsWith("/")) result = true;
        return result;
    }

    private boolean isUrl(String updateMessageText) {
        boolean result = false;
        if (updateMessageText.startsWith("http")
                && updateMessageText.contains("/")
                && updateMessageText.contains(":")
                && updateMessageText.contains(".")) result = true;
        return result;
    }

    private boolean isUser(Long id) {
        boolean result = true;
        if (pocketUserDao.getByKey(id) == null) result = false;
        return result;
    }

    private String executeCommandAndGetMessage(String updateMessageText, Long id) {
        String message = BotMessagesReader.getProperty("bot.message.invalidCommand");

        switch (updateMessageText) {
            case "/start":
                if (isUser(id)) {
                    message = BotMessagesReader.getProperty("bot.message.alreadyUser");
                } else {
                    PocketCode pocketCode = pocketRequest.getAppCode(
                            new AppCodeData(
                                    ApplicationPropertiesReader
                                            .getProperty("bot.redirect")));

                    PocketUser user = new PocketUser();
                    user.setId(id);
                    user.setPocketAppCode(pocketCode);
                    pocketUserDao.add(user);

                    message = BotMessagesReader.getProperty("bot.message.start")
                            + "\nhttps://getpocket.com/auth/authorize?request_token="
                            + pocketCode.getCode()
                            + "&redirect_uri="
                            + ApplicationPropertiesReader.getProperty("bot.redirectUri")
                            + id;
                }
                break;

            case "/help":
                message = BotMessagesReader.getProperty("bot.message.help");
                break;

            case "/delete":
                pocketUserDao.deleteByKey(id);
                message = BotMessagesReader.getProperty("bot.message.delete");
                break;
        }

        return message;

    }

    private String sendUrlAndGetMessage(String updateMessageText, Long id) {

        String token = pocketUserDao.getByKey(id).getAccessToken();

        String message = "Invalid URL or server error";

        if (pocketRequest.addItem(
                new AddItemData(updateMessageText, token))) {

            message = BotMessagesReader.getProperty("bot.message.successSave");
        }
        return message;
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}








