package vn.toeiconline.controller.web;

import org.apache.commons.lang.StringUtils;
import vn.toeiconline.command.ExerciseCommand;
import vn.toeiconline.core.dto.ExerciseDTO;
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

@WebServlet(urlPatterns = {"/danh-sach-bai-tap.html"})
public class ExerciseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseCommand command = FormUtil.populate(ExerciseCommand.class, request);
        ExerciseDTO pojo = command.getPojo();
        if (pojo.getExerciseId() != null) {

        } else {
            executeSearchExercise(request, command);
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/exercise/list.jsp");
            rd.forward(request, response);
        }

    }

    private void executeSearchExercise(HttpServletRequest request, ExerciseCommand command) {
        Map<String, Object> properties = buildProperties(command);
        command.setMaxPageItems(3);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getExerciseServiceInstance().findExerciseByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ExerciseDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems() / command.getMaxPageItems()));
    }

    private Map<String,Object> buildProperties(ExerciseCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        ExerciseDTO pojo = command.getPojo();
        if (StringUtils.isNotBlank(pojo.getName())) {
            properties.put("name", pojo.getName());
        }
        if (pojo.getType() != null) {
            properties.put("type", pojo.getType());
        }
        return properties;
    }
}
