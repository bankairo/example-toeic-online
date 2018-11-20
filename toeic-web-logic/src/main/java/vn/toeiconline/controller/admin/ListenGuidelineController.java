package vn.toeiconline.controller.admin;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import vn.toeiconline.command.ListenGuidelineCommand;
import vn.toeiconline.core.common.utils.UploadUtil;
import vn.toeiconline.core.dto.ListenGuidelineDTO;
import vn.toeiconline.core.service.ListenGuidelineService;
import vn.toeiconline.core.service.impl.ListenGuidelineServiceImpl;
import vn.toeiconline.core.web.common.WebConstant;
import vn.toeiconline.core.web.utils.FormUtil;
import vn.toeiconline.core.web.utils.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//@WebServlet("/admin-guideline-listen-list.html")
@WebServlet(urlPatterns = {"/admin-guideline-listen-list.html", "/admin-guideline-listen-edit.html"})
public class ListenGuidelineController extends HttpServlet {
    private ListenGuidelineService listenGuidelineService = new ListenGuidelineServiceImpl();
    private final Logger log = Logger.getLogger(this.getClass());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuidelineCommand command = FormUtil.populate(ListenGuidelineCommand.class, req);

        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            excuteSearchListenGuideline(req, command);
            req.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
            rd.forward(req, resp);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
            rd.forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListenGuidelineCommand command = new ListenGuidelineCommand();
        ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
        UploadUtil uploadUtil = new UploadUtil();
        Set<String> titleValue = buildSetValue();

        Object[] objects = uploadUtil.writeOrUpdateFile(req, titleValue, WebConstant.LISTENGUIDELINE);
         if (objects != null && (Boolean) objects[0]) {
            Map<String, String> mapValue = (HashMap<String, String>) objects[3];
            if (mapValue != null) {
                command = returnValueListenGuidelintCommand(titleValue, command, mapValue);
            }
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_SUCCESS);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.guideline.listen.add.success"));
        } else {
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.error"));
        }
//        resp.sendRedirect("/admin-guideline-listen-edit.html?urlType=url_edit");
        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
        rd.forward(req, resp);
    }

    private void excuteSearchListenGuideline(HttpServletRequest req, ListenGuidelineCommand command) {
        RequestUtil.initSearchBean(req, command);
        Map<String, Object> property = buildProperty(command);
        Object[] objects = listenGuidelineService.findListenGuidelineByProperties(property, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ListenGuidelineDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));
    }

    private Map<String,Object> buildProperty(ListenGuidelineCommand command) {
        Map<String, Object> property = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(command.getPojo().getTitle())) {
            property.put("title", command.getPojo().getTitle());
        }
        return property;
    }

    private ListenGuidelineCommand returnValueListenGuidelintCommand(Set<String> titleValue, ListenGuidelineCommand command, Map<String,String> mapValue) {
        for (String item: titleValue) {
            if (mapValue.containsKey(item)) {
                if (item.equals("pojo.title")) {
                    command.getPojo().setTitle(mapValue.get(item));
                } else if (item.equals("pojo.content")){
                    command.getPojo().setContent(mapValue.get(item));
                }
            }
        }
        return command;
    }

    private Set<String> buildSetValue() {
        Set<String> titleValue = new HashSet<String>();
        titleValue.add("pojo.title");
        titleValue.add("pojo.content");
        return titleValue;
    }
}
