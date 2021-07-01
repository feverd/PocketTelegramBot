package org.project.bot;

import org.project.dao.PocketUserDao;
import org.project.entity.PocketCode;
import org.project.entity.PocketUser;
import org.project.pocket.commands.AddItemData;
import org.project.pocket.commands.AppCodeData;
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

    // вынести в настройки
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


            //TODO delete

            System.out.println(updateMessageText);
            System.out.println("@");

            if (isCommand(updateMessageText)) {

                if (isUser(id)) {
                    message.setText(BotMessagesReader.getProperty("bot.alreadyUser"));
                } else {
                    // TODO сделать метод executeCommand
                    message.setText(commandMessage(updateMessageText, id));
                }

            } else {

                if (isUrl(updateMessageText) && isUser(id)) {
                    message.setText(sendUrl(updateMessageText, id));
                } else {
                    message.setText(BotMessagesReader.getProperty("bot.acceptedMessages"));
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
                && updateMessageText.contains(":")) result = true;
        return result;
    }

    private boolean isUser(Long id) {
        boolean result = true;
        if (pocketUserDao.getByKey(id).getAccessToken() == null) result = false;
        return result;
    }

    private String commandMessage(String updateMessageText, Long id) {
        String message = BotMessagesReader.getProperty("bot.invalidCommand");


        // TODO SWITCH
        if (Commands.START.name
                .equals(updateMessageText)) {

            PocketCode pocketCode = pocketRequest.getAppCode(
                    new AppCodeData(
                            ApplicationPropertiesReader
                                    .getProperty("bot.redirect")));

            PocketUser user = new PocketUser();
            user.setId(id);
            user.setPocketAppCode(pocketCode);
            pocketUserDao.add(user);


            // TODO delete
            System.out.println(pocketCode.getCode());
            System.out.println(updateMessageText + " " + id);

            message = "https://getpocket.com/auth/authorize?request_token="
                    + pocketCode.getCode()
                    + "&redirect_uri="
                    + ApplicationPropertiesReader.getProperty("bot.redirectUri")
                    + id;

        } else if (Commands.HELP.name
                .equals(updateMessageText)) {
            message = "help command in progress";
        } else if (Commands.DELETE.name
                .equals(updateMessageText)) {
            message = "delete command in progress";
        }


        return message;
    }

    private String sendUrl(String updateMessageText, Long id) {
        String token = pocketUserDao.getByKey(id).getAccessToken();

        String message = "Invalid URL or server error";
        if (pocketRequest.addItem(
                new AddItemData(updateMessageText, token))) {

            message = "Don't forget about your pocket links, the are waiting for you";
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


    enum Commands {
        // TODO узнать модно ли грузить из файла в перечисления
        START("/start", ""),
        DELETE("/delete", ""),
        HELP("/help", "");

        private String name;
        private String message;

        Commands(String name, String message) {
            this.name = name;
            this.message = message;
        }
    }
}








