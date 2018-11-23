package vn.toeiconline.controller.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import vn.toeiconline.command.UserCommand;
import vn.toeiconline.core.common.utils.SessionUtil;
import vn.toeiconline.core.dto.CheckLogin;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.service.UserService;
import vn.toeiconline.core.service.impl.UserServiceImpl;
import vn.toeiconline.core.web.common.WebConstant;
import vn.toeiconline.core.web.utils.FormUtil;
import vn.toeiconline.core.web.utils.SingletonServiceUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/login.html", "/logout.html"})
public class LoginController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (StringUtils.isNotBlank(action)) {
            if (action.equals(WebConstant.LOGIN)) {
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
                rd.forward(req, resp);
            } else if (action.equals(WebConstant.LOGOUT)) {
                SessionUtil.getInstance().removeValue(req, WebConstant.LOGIN_NAME);
                resp.sendRedirect("/home.html");
            }
        } else {
            resp.sendRedirect("/home.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class, req);
        UserDTO pojo = command.getPojo();
        CheckLogin login = SingletonServiceUtil.getUserServiceInstance().checkLogin(pojo.getName(), pojo.getPassword());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources");

            if (login.getExist()) {
                SessionUtil.getInstance().setValue(req, WebConstant.LOGIN_NAME, pojo.getName());
                    if (login.getRoleName().equals(WebConstant.ROLE_NAME_ADMIN)) {
                        resp.sendRedirect("/admin-home.html");
                    } else if (login.getRoleName().equals(WebConstant.ROLE_NAME_USER)) {
                       resp.sendRedirect("/home.html");
                    }
            } else {
                req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
                req.setAttribute(WebConstant.MESSAGE_RESPONSE, resourceBundle.getString("label.login.username.password.wrong"));
                RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
                rd.forward(req, resp);
            }
    }
}
