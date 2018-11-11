package vn.toeiconline.controller.admin;

import org.apache.commons.fileupload.FileUploadException;
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
//        ListenGuidelineCommand command = new ListenGuidelineCommand();
//        List<ListenGuidelineDTO> listenGuidelineDTOS = new ArrayList<ListenGuidelineDTO>();
//        ListenGuidelineDTO dto1 = new ListenGuidelineDTO();
//        dto1.setTitle("Bai hd nghe 1");
//        dto1.setContent("Noi dung bai hd nghe 1");
//        ListenGuidelineDTO dto2 = new ListenGuidelineDTO();
//        dto2.setTitle("Bai hd nghe 2");
//        dto2.setContent("Noi dung bai hd nghe 2");
//        listenGuidelineDTOS.add(dto1);
//        listenGuidelineDTOS.add(dto2);
//        command.setListResult(listenGuidelineDTOS);
//        command.setMaxPageItems(1);
//        command.setTotalItems(listenGuidelineDTOS.size());
//        req.setAttribute(WebConstant.LIST_ITEMS, command);
        ListenGuidelineCommand command = FormUtil.populate(ListenGuidelineCommand.class, req);
//        command.setMaxPageItems(2);
////        RequestUtil.initSearchBean(req, command);
////        Object[] objects = listenGuidelineService.findListenGuidelineByProperties(null, null, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
////        command.setListResult((List<ListenGuidelineDTO>) objects[1]);
////        command.setTotalItems(Integer.parseInt(objects[0].toString()));
        req.setAttribute(WebConstant.LIST_ITEMS, command);
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
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
        Set<String> titleValue = new HashSet<String>();
        titleValue.add("pojo.title");
        titleValue.add("pojo.content");
        try {
            Object[] objects = uploadUtil.writeOrUpdateFile(req, titleValue, WebConstant.LISTENGUIDELINE);
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_SUCCESS);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.guideline.listen.add.success"));
        } catch (FileUploadException e) {
            log.error(e.getMessage(), e);
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.error"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            req.setAttribute(WebConstant.ALERT, WebConstant.TYPE_ERROR);
            req.setAttribute(WebConstant.MESSAGE_RESPONSE, bundle.getString("label.error"));
        }
//        resp.sendRedirect("/admin-guideline-listen-edit.html?urlType=url_edit");
        RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/edit.jsp");
        rd.forward(req, resp);
    }
}
