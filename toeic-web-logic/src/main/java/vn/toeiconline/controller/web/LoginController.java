package vn.toeiconline.controller.web;

import org.apache.log4j.Logger;
import vn.toeiconline.command.UserCommand;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.service.UserService;
import vn.toeiconline.core.service.impl.UserServiceImpl;
import vn.toeiconline.core.web.utils.FormUtil;
import vn.toeiconline.web.logic.common.WebConstant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login.html")
public class LoginController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class, req);
        UserDTO pojo = command.getPojo();
        UserService userService = new UserServiceImpl();
        try {
            if (userService.isUserExist(pojo) != null) {
                if (userService.findRoleByUser(pojo) != null && userService.findRoleByUser(pojo).getRoleDTO() != null) {
                    if (userService.findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_NAME_ADMIN)) {
                        req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_SUCCESS);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE, "Tài khoản là admin");
                    } else if (userService.findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_NAME_USER)) {
                        req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_SUCCESS);
                        req.setAttribute(WebConstant.MESSAGE_RESPONSE, "Tài khoản là user");
                    }
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, "Tên hoặc mật khẩu sai");
        }
        RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
        rd.forward(req, resp);
    }
}
