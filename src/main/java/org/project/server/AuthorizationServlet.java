package org.project.server;

import org.project.dao.PocketCodeDao;
import org.project.dao.PocketUserDao;
import org.project.entity.PocketAppCode;
import org.project.entity.PocketUser;
import org.project.pocket.command.AccessTokenCmd;
import org.project.pocket.request.AccessTokenRequest;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/auth/*"})
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO обрабатывать исключение или пробрасывать ?

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        EntityManager manager = factory.createEntityManager();
        PocketUserDao pocketUserDao = new PocketUserDao(manager);
        PocketCodeDao pocketCodeDao = new PocketCodeDao(manager);

        // переделать чтоб каждый раз так не делать
        // тк каждый раз дропать тейбл ошибка у сервра. тк по запросу консюмер кее много результатов

        PocketAppCode pocketAppCode = pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550");
        if (pocketAppCode == null) {
            resp.sendError(500, "Server error, try later");
        } else {
            String code = pocketAppCode.getCode();

            System.out.println(code);
            // достать из репозитория
            AccessTokenRequest userTokenRequest =
                    new AccessTokenRequest(new AccessTokenCmd(pocketAppCode.getConsumerKey(), code));

            PocketUser user = userTokenRequest.getUser();
            user.setChatId(Long.parseLong(req.getParameter("chatId")));

            // test
            pocketUserDao.add(user);
            System.out.println(user);

            resp.sendRedirect("https://t.me/PostPocket_bot");
        }
    }


}
