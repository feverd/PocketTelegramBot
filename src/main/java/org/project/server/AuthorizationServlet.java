package org.project.server;

import org.project.dao.PocketCodeDao;
import org.project.dao.PocketUserDao;
import org.project.entity.PocketAppCode;
import org.project.entity.PocketUser;
import org.project.pocket.commands.AccessTokenCmd;
import org.project.pocket.request.PocketRequest;
import org.project.service.PropertiesReader;


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

        //TODO where to create manager? in methods or in class fields
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        EntityManager manager = factory.createEntityManager();
        PocketCodeDao pocketCodeDao = new PocketCodeDao(manager);


        PocketAppCode pocketAppCode = pocketCodeDao.getByKey("97779-9e5f86561e627ae0c473d550");
        if (pocketAppCode == null) {
            resp.sendError(500, "Server error, try later");
        } else {
            String code = pocketAppCode.getCode();

            System.out.println(code);


            getAndSaveUser(manager, pocketAppCode, Long.parseLong(req.getParameter("id")));

            resp.sendRedirect(PropertiesReader.getApplicationPropertyValue("server.redirect"));
        }
    }


    private boolean isUser(Long id, PocketUserDao pocketUserDao) {
        boolean result = true;
        if (pocketUserDao.getByKey(id) == null) result = false;
        return result;
    }

    private void getAndSaveUser(EntityManager manager, PocketAppCode pocketAppCode, Long id) {
        PocketUserDao pocketUserDao = new PocketUserDao(manager);

        if (isUser(id, pocketUserDao)) {
            return;
        }


        // TODO ask about commands
        PocketUser user = PocketRequest.getPocketUser(new AccessTokenCmd(/*pocketAppCode.getConsumerKey(), */pocketAppCode.getCode()));
        user.setId(id);


        pocketUserDao.add(user);

        //TODO delete
        System.out.println(user);
    }

}
