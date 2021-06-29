package org.project.bot;

import org.project.dao.PocketCodeDao;
import org.project.dao.PocketUserDao;
import org.project.pocket.commands.AddItemCmd;
import org.project.pocket.request.PocketRequest;
import org.project.service.PropertiesReader;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityManager;

public class Bot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;
    private PocketCodeDao pocketCodeDao;
    private PocketUserDao pocketUserDao;

    // вынести в настройки
    public Bot(EntityManager manager) {
        PropertiesReader service = new PropertiesReader();
        this.botToken = service.getPropertyValue("bot.token");
        this.botUsername = service.getPropertyValue("bot.name");
        pocketCodeDao = new PocketCodeDao(manager);
        pocketUserDao = new PocketUserDao(manager);
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

        // TODO переделать ли получаемые команды на паттерн команда и извлекать через интерфейс  ?

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());

            System.out.println(update.getMessage().getText());
            System.out.println("@@@@@");

            if (isCommand(update.getMessage().getText())) {
                System.out.println("Команда");
                message.setText(commandMessage(update));

                System.out.println(message.getText());
            } else {

                //TODO вставить проверку авторизирован ли пользователь или еще нет
                if (isUrl(update.getMessage().getText())) {

                    message.setText(sendUrl(update));

                } else {
                    message.setText("I accept only URL or commands started with /");
                }

            }


            // TODO delete lines
            //System.out.println(update.getMessage().toString());
            System.out.println("@@@@@@@@            @@@@@@@@");

            // TODO add logger
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean isCommand(String messageFromUser) {
        boolean result = false;
        if (messageFromUser.startsWith("/")) result = true;
        return result;
    }

    private boolean isUrl(String text) {
        boolean result = false;
        if (text.startsWith("http") && text.contains("/") && text.contains(":")) result = true;
        return result;
    }

    private boolean isUser(Long chatId) {
        boolean result = true;
        if (pocketUserDao.getByKey(chatId) == null) result = false;
        return result;
    }

    private String commandMessage(Update update) {
        String message = "Unfortunately, I know only /start and /delete commands...";
        if ("/start".equals(update.getMessage().getText())) {
            if (isUser(update.getMessage().getChatId())) {
                message = "You are already my user \n" +
                        "just send URL, and I will save it in your Pocket :)";
            } else {
                // TODO read from properties
                String code = pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550").getCode();

                message = "https://getpocket.com/auth/authorize?request_token="
                        + code
                        + "&redirect_uri=http://localhost:8080/auth/auth?chatId="
                        + update.getMessage().getChatId();
            }
        }
        //TODO ломается авторизация (надо дропать и код приложения и просить снова все коды доступа)
        /*else if ("/delete".equals(update.getMessage().getText())) {
            pocketUserDao.deleteByKey(update.getMessage().getChatId());
            message = "Data about you was successfully deleted";
        }*/
        return message;
    }

    private String sendUrl(Update update) {
        // TODO delete lines
        System.out.println("@@@@@@@@@@@@@@@@");

        String token = pocketUserDao.getByKey(update.getMessage().getChatId()).getAccessToken();

        String message = "Invalid URL or server error";
        if ( PocketRequest.addItem(
                new AddItemCmd(update.getMessage().getText(),
                        "97779-9e5f86561e627ae0c473d550",
                        token))) {

            message = "Don't forget about your pocket links, the are waiting for you";
        }
        return message;
    }
}
