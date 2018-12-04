package vn.toeiconline.controller.web;

import vn.toeiconline.command.ExerciseQuestionCommand;
import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.web.common.WebConstant;
import vn.toeiconline.core.web.utils.FormUtil;
import vn.toeiconline.core.web.utils.RequestUtil;
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

@WebServlet(urlPatterns = {"/bai-tap-thuc-hanh.html","/ajax-bai-tap-dap-an.html"})
public class ExerciseQuestionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = FormUtil.populate(ExerciseQuestionCommand.class, request);
        getExerciseQuestion(command);
        request.setAttribute(WebConstant.LIST_ITEMS, command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/exercise/detail.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = FormUtil.populate(ExerciseQuestionCommand.class, request);
        getExerciseQuestion(command);
        for (ExerciseQuestionDTO item: command.getListResult()) {
            if (!command.getAnswerUser().equals(item.getCorrectAnswer())) {
                command.setCheckAnswer(true);
            }
        }

        request.setAttribute(WebConstant.LIST_ITEMS, command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/exercise/result.jsp");
        rd.forward(request, response);
    }

    private void getExerciseQuestion(ExerciseQuestionCommand command) {
        Map<String, Object> properties = buildProperties(command);
        command.setMaxPageItems(1);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getExerciseQuestionServiceInstance().findExerciseQuestionByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems(), null);
        command.setListResult((List<ExerciseQuestionDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems()/command.getMaxPageItems()));
    }

    private Map<String,Object> buildProperties(ExerciseQuestionCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if (command.getExerciseId() != null) {
            properties.put("exerciseId", command.getExerciseId());
        }
        return properties;
    }
}
