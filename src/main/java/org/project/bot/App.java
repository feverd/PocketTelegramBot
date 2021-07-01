package org.project.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        EntityManager manager = factory.createEntityManager();

        TelegramBotsApi bot = null;
        try {
            bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(new Bot(manager));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //LOGGER.error(e.getMessage());
        }

    }


}
