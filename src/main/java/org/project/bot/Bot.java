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
        this.botToken = PropertiesReader.getApplicationPropertyValue("bot.token");
        this.botUsername = PropertiesReader.getApplicationPropertyValue("bot.name");
        pocketCodeDao = new PocketCodeDao(manager);
        pocketUserDao = new PocketUserDao(manager);
    }

    // TODO Где запускать бота? в мейне или в классе бота ?

    /*public void start() {
        TelegramBotsApi bot = null;
        try {
            bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(this.);
        } catch (TelegramApiException e) {
            //LOGGER.error(e.getMessage());
        }
    }*/

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

            String updateMessageText = update.getMessage().getText();
            Long id = update.getMessage().getFrom().getId();


            //TODO delete
            System.out.println(updateMessageText);
            System.out.println("@");

            if (isCommand(updateMessageText)) {
                if (isUser(id)) {
                    message.setText("You are already my user \n" +
                            "just send URL, and I will save it in your Pocket :)");
                } else {
                    message.setText(commandMessage(updateMessageText, id));
                }
            } else {
                if (isUrl(updateMessageText) && isUser(id)) {
                    message.setText(sendUrl(updateMessageText, id));
                } else {
                    message.setText("I accept only URL or commands started with \"/\"");
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
        if (pocketUserDao.getByKey(id) == null) result = false;
        return result;
    }

    private String commandMessage(String updateMessageText, Long id) {
        String message = "Unfortunately, I know only /start and command...";
        if ("/start".equals(updateMessageText)) {
            // TODO read from properties
            String code = pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550").getCode();

            message = "https://getpocket.com/auth/authorize?request_token="
                    + code
                    + "&redirect_uri=http://localhost:8080/auth/auth?chatId="
                    + id;

        }
        return message;
    }

    private String sendUrl(String updateMessageText, Long id) {
        String token = pocketUserDao.getByKey(id).getAccessToken();

        String message = "Invalid URL or server error";
        if (PocketRequest.addItem(
                new AddItemCmd(updateMessageText,
                        "97779-9e5f86561e627ae0c473d550",
                        token))) {

            message = "Don't forget about your pocket links, the are waiting for you";
        }
        return message;
    }

    private void sendMessage(SendMessage sendMessage) {
        // TODO add logger
        try {
            execute(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
