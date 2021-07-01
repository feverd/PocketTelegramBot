package org.project.server;

import org.project.dao.PocketUserDao;
import org.project.entity.PocketCode;
import org.project.entity.PocketUser;
import org.project.pocket.commands.AccessTokenData;
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
        // TODO так нормально делать ?
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-connection");
        return factory.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO обрабатывать исключение или пробрасывать ?

        PocketUserDao pocketUserDao = new PocketUserDao(manager);

        Long id = Long.parseLong(req.getParameter("id"));

        PocketUser pocketUser = getUser(id, pocketUserDao);

        if (pocketUser.getPocketAppCode() == null) {
            resp.sendError(500, "Server error, try later");
        } else {

            //TODO delete
            String code = pocketUser.getPocketAppCode().getCode();
            System.out.println("____________CODE___________");
            System.out.println(code + "code user: " + pocketUser.getId());


            //TODO создавать DAO в класе или отдельно ?
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


        // TODO ask about commands
        PocketUser user = pocketRequest.getPocketUser(new AccessTokenData(pocketCode.getCode()));
        user.setId(id);
        user.setPocketAppCode(pocketCode);



        pocketUserDao.update(user);

        //TODO delete*/
        System.out.println("__________FULL USER INFO__________");
        System.out.println(user);
    }

}
