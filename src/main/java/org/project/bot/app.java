package org.project.bot;

import org.project.dao.PocketCodeDao;
import org.project.entity.PocketAppCode;
import org.project.pocket.command.AppCodeCmd;
import org.project.pocket.request.AppCodeRequest;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class app {
    public static void main(String[] args) {

        // TODO сделать проверку на код для приложения в БД

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        EntityManager manager = factory.createEntityManager();
        PocketCodeDao pocketCodeDao = new PocketCodeDao(manager);

        if (pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550") == null) {
                /*manager.find(PocketCode.class, "97779-9e5f86561e627ae0c473d550") == null) {*/
            System.out.println("gg vp");

            AppCodeRequest request = new AppCodeRequest(new AppCodeCmd("97779-9e5f86561e627ae0c473d550", "https://t.me/PostPocket_bot"));
            PocketAppCode pocketAppCode = request.getAppCode();



            pocketCodeDao.add(pocketAppCode);

            //System.out.println(pocketCodeDao.getByKey().toString());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            System.out.println(pocketAppCode);
        }


        TelegramBotsApi bot = null;
        try {
            bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(new Bot(manager));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

}
