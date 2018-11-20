package vn.toeiconline.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import vn.toeiconline.command.UserCommand;
import vn.toeiconline.core.common.utils.ExcelPoiUtil;
import vn.toeiconline.core.common.utils.SessionUtil;
import vn.toeiconline.core.common.utils.UploadUtil;
import vn.toeiconline.core.dto.RoleDTO;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.dto.UserImportDTO;
import vn.toeiconline.core.web.common.WebConstant;
import vn.toeiconline.core.web.utils.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/admin-user-list.html", "/ajax-admin-user-edit.html",
                            "/admin-user-import.html", "/admin-user-import-validate.html"})
public class UserController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private final String SHOW_IMPORT_USER = "show_import_user";
    private final String READ_EXCEL = "read_excel";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String IMPORT_USER_LIST = "import_user_list";
    private final String IMPORT_DATA = "import_data";
    ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserCommand command = FormUtil.populate(UserCommand.class, request);
        UserDTO pojo = command.getPojo();
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            Map<String, Object> mapProperty = new HashMap<String, Object>();
            RequestUtil.initSearchBean(request, command);
            Object[] objects = SingletonServiceUtil.getUserServiceInstance().findUserByProperty(mapProperty, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
            command.setListResult((List<UserDTO>) objects[1]);
            command.setTotalItems(Integer.parseInt(objects[0].toString()));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapMessage(bundle);
                WebCommonUtil.addDirectMessage(request, command.getCrudaction(), mapMessage);
            }
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/list.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (pojo != null && pojo.getUserId() != null) {
                command.setPojo(SingletonServiceUtil.getUserServiceInstance().findUserById(pojo.getUserId()));
            }
            command.setRoles(SingletonServiceUtil.getRoleServiceInstance().findAll());
            request.setAttribute(WebConstant.FORM_ITEM, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_USER)) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/importuser.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            List<UserImportDTO> userImportDTOS = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request, IMPORT_USER_LIST);
            command.setUserImportDTOS(userImportDTOS);
            command.setMaxPageItems(3);
            command.setTotalItems(userImportDTOS.size());
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/importuser.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UploadUtil uploadUtil = new UploadUtil();
            UserCommand command = FormUtil.populate(UserCommand.class, request);
            UserDTO pojo = command.getPojo();
            Set<String> titleValue = new HashSet<String>();
            titleValue.add("urlType");
            Object[] objects = uploadUtil.writeOrUpdateFile(request, titleValue, "excel");
            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setRoleId(command.getRoleId());
                    pojo.setRoleDTO(roleDTO);
                    if (pojo != null && pojo.getUserId() != null) {
                        SingletonServiceUtil.getUserServiceInstance().updateUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);
                    } else {
                        SingletonServiceUtil.getUserServiceInstance().saveUser(pojo);
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);
                    }
                }
                RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
                rd.forward(request, response);
            }
            if (objects != null && (Boolean)objects[0]) {
                String urlType = null;
                Map<String, String> mapValue = (Map<String, String>) objects[3];
                for (Map.Entry<String, String> item: mapValue.entrySet()) {
                    if (item.getKey().equals("urlType")) {
                        urlType = item.getValue();
                    }
                }
                if (urlType != null && urlType.equals(READ_EXCEL)) {
                    String fileName = objects[2].toString();
                    String fileLocation = objects[1].toString();
                    List<UserImportDTO> excelValue = returnValueFromExcel(fileLocation, fileName);
                    validateData(excelValue);
                    SessionUtil.getInstance().setValue(request, IMPORT_USER_LIST, excelValue);
                    response.sendRedirect("/admin-user-import-validate.html?urlType=validate_import");
                }
            }
            if (command.getUrlType() != null && command.getUrlType().equals(IMPORT_DATA)) {
                List<UserImportDTO> userImportDTOS = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request, IMPORT_USER_LIST);
                SingletonServiceUtil.getUserServiceInstance().saveUserImport(userImportDTOS);
                SessionUtil.getInstance().removeValue(request, IMPORT_USER_LIST);
                response.sendRedirect("/admin-user-list.html?urlType=url_list");
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
        }
    }

    private void validateData(List<UserImportDTO> excelValue) {
        Set<String> stringSet = new HashSet<String>();
        for (UserImportDTO item: excelValue) {
            validateRequireFiled(item);
            validateDuplicate(item, stringSet);
            SingletonServiceUtil.getUserServiceInstance().validateUserImport(excelValue);
        }
    }

    private void validateDuplicate(UserImportDTO item, Set<String> stringSet) {
        String message = "";

        if (!stringSet.contains(item.getUserName())) {
            stringSet.add(item.getUserName());
        } else {
            if (item.isValid()) {
                message += "<br/>";
                message += bundle.getString("label.user.name.duplicate");
                item.setValid(false);
                item.setMessage(message.substring(5));
            }
        }
    }


    private void validateRequireFiled(UserImportDTO item) {
        String message = "";
        if (StringUtils.isBlank(item.getUserName())) {
            message += "<br/>";
            message += bundle.getString("label.user.name.notempty");
        }
        if (StringUtils.isBlank(item.getPassword())) {
            message += "<br/>";
            message += bundle.getString("label.user.password.notempty");
        }
        if (StringUtils.isBlank(item.getFullName())) {
            message += "<br/>";
            message += bundle.getString("label.user.fullname.notempty");
        }
        if (StringUtils.isBlank(item.getRoleName())) {
            message += "<br/>";
            message += bundle.getString("label.user.rolename.notempty");
        }
        if (StringUtils.isNotBlank(message)) {
            item.setValid(false);
            item.setMessage(message.substring(5));
        }
    }

    private List<UserImportDTO> returnValueFromExcel(String fileLocation, String fileName) throws IOException {
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileLocation, fileName);
        Sheet sheet = workbook.getSheetAt(0);
        List<UserImportDTO> excelValue = returnDataFromExcel(sheet);
       
        return excelValue;
    }

    private List<UserImportDTO> returnDataFromExcel(Sheet sheet) {
        List<UserImportDTO> excelValue = new ArrayList<UserImportDTO>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            UserImportDTO userImportDTO = new UserImportDTO();
            userImportDTO.setUserName(ExcelPoiUtil.getCellValue(row.getCell(0)));
            userImportDTO.setPassword(ExcelPoiUtil.getCellValue(row.getCell(1)));
            userImportDTO.setFullName(ExcelPoiUtil.getCellValue(row.getCell(2)));
            userImportDTO.setRoleName(ExcelPoiUtil.getCellValue(row.getCell(3)));
            excelValue.add(userImportDTO);
        }
        return excelValue;
    }

    private Map<String,String> buildMapMessage(ResourceBundle bundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, bundle.getString("label.user.insert.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, bundle.getString("label.user.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, bundle.getString("label.user.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, bundle.getString("label.error"));
        return mapMessage;
    }
}
