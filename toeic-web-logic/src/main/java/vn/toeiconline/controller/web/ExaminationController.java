package vn.toeiconline.controller.web;

import org.apache.commons.lang.StringUtils;
import vn.toeiconline.command.ExaminationCommand;
import vn.toeiconline.core.dto.ExaminationDTO;
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

@WebServlet("/danh-sach-bai-thi.html")
public class ExaminationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExaminationCommand command = FormUtil.populate(ExaminationCommand.class, request);
        executeExaminationSearch(command);
        request.setAttribute(WebConstant.LIST_ITEMS, command);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/examination/list.jsp");
        rd.forward(request, response);
    }

    private void executeExaminationSearch(ExaminationCommand command) {
        Map<String, Object> properties = buildProperties(command);
        command.setMaxPageItems(3);
        RequestUtil.initSearchBeanManual(command);
        Object[] objects = SingletonServiceUtil.getExaminationServiceInstance().findExaminationByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ExaminationDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        command.setTotalPages((int) Math.ceil((double) command.getTotalItems()/command.getMaxPageItems()));
    }

    private Map<String,Object> buildProperties(ExaminationCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();
        if (command.getPojo() != null && StringUtils.isNotBlank(command.getPojo().getName())) {
            properties.put("name", command.getPojo().getName());
        }
        return properties;
    }
}
