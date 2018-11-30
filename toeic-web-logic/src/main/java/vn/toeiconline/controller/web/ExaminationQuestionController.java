package vn.toeiconline.controller.web;

import vn.toeiconline.command.ExaminationQuestionCommand;
import vn.toeiconline.core.common.utils.SessionUtil;
import vn.toeiconline.core.dto.ExaminationQuestionDTO;
import vn.toeiconline.core.dto.ResultDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/bai-thi-thuc-hanh.html", "/bai-thi-dap-an.html"})
public class ExaminationQuestionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         if (SessionUtil.getInstance().getValue(request, WebConstant.LOGIN_NAME) == null) {
            response.sendRedirect("/login.html?action=login");
        } else {
             ExaminationQuestionCommand command = FormUtil.populate(ExaminationQuestionCommand.class, request);
             getExaminationQuestion(command);
             Integer number = 1;
             for (ExaminationQuestionDTO item: command.getListResult()) {
                 if (item.getCorrectAnswer() != null) {
                     item.setNumber(number);
                     number++;
                 }
             }
             request.setAttribute(WebConstant.LIST_ITEMS, command);
             RequestDispatcher rd = request.getRequestDispatcher("/views/web/examination/detail.jsp");
             rd.forward(request, response);
         }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SessionUtil.getInstance().getValue(request, WebConstant.LOGIN_NAME) == null) {
            response.sendRedirect("/login.html?action=login");
        } else {
            ExaminationQuestionCommand command = new ExaminationQuestionCommand();
            Integer examinationId = Integer.parseInt(request.getParameter("examinationId"));
            command.setExaminationId(examinationId);
            getExaminationQuestion(command);
            Integer number = 1;
            for (ExaminationQuestionDTO item: command.getListResult()) {
                if (request.getParameter("answerUser[" + item.getExaminationQuestionId() + "]") != null){
                    item.setAnswerUser(request.getParameter("answerUser[" + item.getExaminationQuestionId() + "]"));
                }
                if (item.getCorrectAnswer() != null) {
                    item.setNumber(number);
                    number++;
                }
            }
            String userName = (String) SessionUtil.getInstance().getValue(request, WebConstant.LOGIN_NAME);
            ResultDTO resultDTO = SingletonServiceUtil.getResultServiceInstance().save(userName, examinationId, command.getListResult());
            command.setListenScore(resultDTO.getListenScore());
            command.setReadingScore(resultDTO.getReadingScore());
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/examination/result.jsp");
            rd.forward(request, response);
        }
    }

    private void getExaminationQuestion(ExaminationQuestionCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        Object[] objects = SingletonServiceUtil.getExaminationQuestionServiceInstance().findExaminationQuestionByProperties(properties, command.getSortExpression(), command.getSortDirection(), null, null, command.getExaminationId());
        command.setListResult((List<ExaminationQuestionDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
    }
}
