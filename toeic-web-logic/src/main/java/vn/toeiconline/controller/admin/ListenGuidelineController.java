package vn.toeiconline.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import vn.toeiconline.command.ListenGuidelineCommand;
import vn.toeiconline.core.common.utils.UploadUtil;
import vn.toeiconline.core.dto.ListenGuidelineDTO;
import vn.toeiconline.core.service.ListenGuidelineService;
import vn.toeiconline.core.service.impl.ListenGuidelineServiceImpl;
import vn.toeiconline.core.web.common.WebConstant;
import vn.toeiconline.core.web.utils.FormUtil;
import vn.toeiconline.core.web.utils.RequestUtil;
import vn.toeiconline.core.web.utils.SingletonServiceUtil;
import vn.toeiconline.core.web.utils.WebCommonUtil;

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
        ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
                List<Integer> ids = new ArrayList<Integer>();
                for (String item: command.getCheckList()) {
                    ids.add(Integer.parseInt(item));
                }
                Integer result = SingletonServiceUtil.getListenGuidelineServiceInstance().delete(ids);
                if (result != ids.size()) {
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);
                }
            }
            excuteSearchListenGuideline(req, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapMessage(bundle);
                WebCommonUtil.addDirectMessage(req, command.getCrudaction(), mapMessage);
            }
            req.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = req.getRequestDispatcher("/views/admin/listenguideline/list.jsp");
            rd.forward(req, resp);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (command.getPojo() != null && command.getPojo().getListenGuidelineId() != null) {
                int id = command.getPojo().getListenGuidelineId();
                ListenGuidelineDTO dto =
                        SingletonServiceUtil.getListenGuidelineServiceInstance().findListenGuidelineById("listenGuidelineId", id);
                command.setPojo(dto);
            }
            req.setAttribute(WebConstant.FORM_ITEM, command);
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
        boolean checkStatusUploadImage = (Boolean) objects[0];
        if (!checkStatusUploadImage) {
            resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
        } else {
            ListenGuidelineDTO dto = command.getPojo();
            if (StringUtils.isNotBlank(objects[2].toString())) {
                dto.setImage(objects[2].toString());
            }
            Map<String, String> mapValue = (HashMap<String, String>) objects[3];
            if (mapValue != null) {
                dto = returnValueOfDTO(dto, mapValue);
            }
            if (dto != null) {
                if (dto.getListenGuidelineId() != null) {
                    ListenGuidelineDTO listenGuidelineDTO = SingletonServiceUtil.getListenGuidelineServiceInstance().findListenGuidelineById("listenGuidelineId", dto.getListenGuidelineId());
                    if (dto.getImage() == null) {
                        dto.setImage(listenGuidelineDTO.getImage());
                    }
                    dto.setCreatedDate(listenGuidelineDTO.getCreatedDate());
                    dto = SingletonServiceUtil.getListenGuidelineServiceInstance().updateListenGuideline(dto);
                    if (dto != null) {
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_update");
                    } else {
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                } else {
                    try {
                        SingletonServiceUtil.getListenGuidelineServiceInstance().saveListenGuideline(dto);
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_insert");
                    } catch (ConstraintViolationException e) {
                        log.error(e.getMessage(), e);
                        resp.sendRedirect("/admin-guideline-listen-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                }
            }
        }
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

    private ListenGuidelineDTO returnValueOfDTO(ListenGuidelineDTO dto, Map<String,String> mapValue) {
         for (Map.Entry<String, String> item: mapValue.entrySet()) {
             if (item.getKey().equals("pojo.title")) {
                 dto.setTitle(item.getValue());
             } else if (item.getKey().equals("pojo.content")) {
                 dto.setContent(item.getValue());
             } else if (item.getKey().equals("pojo.listenGuidelineId")) {
                 dto.setListenGuidelineId(Integer.parseInt(item.getValue()));
             }
         }
         return dto;
    }


    private Set<String> buildSetValue() {
    Set<String> titleValue = new HashSet<String>();
    titleValue.add("pojo.title");
    titleValue.add("pojo.content");
    titleValue.add("pojo.listenGuidelineId");
    return titleValue;
    }
    private Map<String,String> buildMapMessage(ResourceBundle bundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, bundle.getString("label.listenguideline.insert.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, bundle.getString("label.listenguideline.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, bundle.getString("label.listenguideline.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, bundle.getString("label.error"));
        return mapMessage;
    }
}

