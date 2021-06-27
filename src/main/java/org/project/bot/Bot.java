package org.project.bot;

import org.project.dao.PocketCodeDao;
import org.project.dao.PocketUserDao;
import org.project.pocket.command.AddItemCmd;
import org.project.pocket.request.AddRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityManager;

public class Bot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;
    private EntityManager manager;

    // вынести в настройки
    public Bot(EntityManager manager) {
        this.botToken = "token";
        botUsername = "PostPocket_bot";
        this.manager = manager;
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

        PocketCodeDao pocketCodeDao = new PocketCodeDao(manager);
        PocketUserDao pocketUserDao = new PocketUserDao(manager);

        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());


            if (isCommand(update.getMessage().getText())) {

                if ("/start".equals(update.getMessage().getText())) {
                    if (isUser(update.getMessage().getChatId(), pocketUserDao)) {
                        message.setText("You are already my user \njust send URL, and I will save it in your Pocket acc :)");
                    } else {
                        String code = pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550").getCode();

                        message.setText("https://getpocket.com/auth/authorize?request_token=" +
                                code +
                                "&redirect_uri=http://localhost:8080/auth/auth?chatId=" +
                                update.getMessage().getChatId());
                    }
                } else {
                    message.setText("Unfortunately, I know only /start command...");
                }
            } else {

                //TODO вставить проверку авторизирован ли пользователь или еще нет
                if (isUrl(update.getMessage().getText())) {
                    System.out.println("@@@@@@@@@@@@@@@@");
                    System.out.println(update.getMessage().getChatId());
                    String token = pocketUserDao.getByKey(update.getMessage().getChatId()).getAccessToken();
                    System.out.println("@@@@@@@@@@@@@@@@");

                    if ( new AddRequest(
                            new AddItemCmd(update.getMessage().getText(),
                                    "97779-9e5f86561e627ae0c473d550",
                            token)).
                            addItem()    ) {
                        message.setText("Don't forget about your pocket links, the are waiting for you");
                    } else {
                        message.setText("Invalid URL or server error");
                    }


                    System.out.println("@@@@@@@@@@@@@@@@");



                } else {
                    message.setText("I accept only URL or commands started with /");
                }

            }


            System.out.println(update.getUpdateId());
            System.out.println(update.getMessage().toString());
            System.out.println();

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isCommand(String messageFromUser) {
        boolean result = false;
        // переделать на switch
        if (messageFromUser.startsWith("/")) result = true;
        return result;
    }

    private boolean isUrl(String text) {
        boolean result = false;
        if (text.startsWith("http") && text.contains("/") && text.contains(":")) result = true;
        return result;
    }

    private boolean isUser(Long chatId, PocketUserDao dao) {
        boolean result = false;
        if (dao.getByKey(chatId) != null) result = true;
        return result;
    }
}
