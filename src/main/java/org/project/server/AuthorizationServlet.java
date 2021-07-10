package org.project.server;

import org.project.dao.PocketUserDao;
import org.project.entity.PocketCode;
import org.project.entity.PocketUser;
import org.project.pocket.data.AccessTokenData;
import org.project.pocket.request.PocketRequest;
import org.project.service.ApplicationPropertiesReader;


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
    private PocketRequest pocketRequest;
    private EntityManager manager;


    public AuthorizationServlet() {
        this.pocketRequest = new PocketRequest();
        this.manager = getManager();
    }

    private EntityManager getManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        return factory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PocketUserDao pocketUserDao = new PocketUserDao(manager);

        Long id = Long.parseLong(req.getParameter("id"));

        PocketUser pocketUser = getUser(id, pocketUserDao);

        if (pocketUser.getPocketAppCode() == null) {
            resp.sendError(500, "Server error, try later");
        } else {




            getAndUpdateUser(manager, pocketUser.getPocketAppCode(), id);

            resp.sendRedirect(ApplicationPropertiesReader.getProperty("server.redirect"));
        }
    }

    private PocketUser getUser(Long id, PocketUserDao pocketUserDao) {
        return pocketUserDao.getByKey(id);
    }

    private void getAndUpdateUser(EntityManager manager, PocketCode pocketCode, Long id) {
        PocketUserDao pocketUserDao = new PocketUserDao(manager);

        if (getUser(id, pocketUserDao).getAccessToken() != null) {
            return;
        }

        PocketUser user = pocketRequest.getPocketUser(new AccessTokenData(pocketCode.getCode()));
        user.setId(id);
        user.setPocketAppCode(pocketCode);

        pocketUserDao.update(user);
    }

}
