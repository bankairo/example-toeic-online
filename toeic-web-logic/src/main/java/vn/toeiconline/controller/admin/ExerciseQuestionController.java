package vn.toeiconline.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import vn.toeiconline.command.ExerciseQuestionCommand;
import vn.toeiconline.core.common.utils.ExcelPoiUtil;
import vn.toeiconline.core.common.utils.SessionUtil;
import vn.toeiconline.core.common.utils.UploadUtil;
import vn.toeiconline.core.dto.ExerciseDTO;
import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.dto.ExerciseQuestionImportDTO;
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

@WebServlet(urlPatterns = {"/admin-exercise-question-list.html", "/admin-exercise-question-edit.html",
                            "/admin-execise-question-import.html", "/admin-execise-question-import-validate.html"})
public class ExerciseQuestionController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
    private final String SHOW_IMPORT_EXERCISE_QUESTION = "show_import_exercise_question";
    private final String READ_EXCEL = "read_excel";
    private final String IMPORT_EXERCISE_QUESTION_LIST = "import_exercise_question_list";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String IMPORT_DATA = "import_data";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = FormUtil.populate(ExerciseQuestionCommand.class, request);

        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buildMapMessage(bundle);
                WebCommonUtil.addDirectMessage(request, command.getCrudaction(), mapMessage);
                command.setCrudaction(null);
            }
            getExerciseQuestion(request, command);
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/exercisequestion/list.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            ExerciseQuestionDTO pojo = command.getPojo();
            if (pojo != null) {
                if (pojo.getExerciseQuestionId() != null) {
                    ExerciseQuestionDTO exerciseQuestionDTO = SingletonServiceUtil.getExerciseQuestionServiceInstance().findExerciseQuestionById(pojo.getExerciseQuestionId());
                    List<ExerciseDTO> exerciseDTOS = SingletonServiceUtil.getExerciseServiceInstance().findAll();
                    List<String> answerList = buildAnswerList();
                    command.setPojo(exerciseQuestionDTO);
                    command.setExercises(exerciseDTOS);
                    command.setAnswerList(answerList);
                    request.setAttribute(WebConstant.FORM_ITEM, command);
                    RequestDispatcher rd = request.getRequestDispatcher("/views/admin/exercisequestion/edit.jsp");
                    rd.forward(request, response);
                } else {
                    List<ExerciseDTO> exerciseDTOS = SingletonServiceUtil.getExerciseServiceInstance().findAll();
                    List<String> answerList = buildAnswerList();
                    command.setExercises(exerciseDTOS);
                    command.setAnswerList(answerList);
                    request.setAttribute(WebConstant.FORM_ITEM, command);
                    RequestDispatcher rd = request.getRequestDispatcher("/views/admin/exercisequestion/edit.jsp");
                    rd.forward(request, response);
                }
            }
        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_EXERCISE_QUESTION)) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/exercisequestion/importexercisequestion.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            if (SessionUtil.getInstance().getValue(request, IMPORT_EXERCISE_QUESTION_LIST) != null) {
                List<ExerciseQuestionImportDTO> exerciseQuestionImportDTOS = (List<ExerciseQuestionImportDTO>) SessionUtil.getInstance().getValue(request, IMPORT_EXERCISE_QUESTION_LIST);
                command.setExerciseQuestionImportDTOS(returnListImport(exerciseQuestionImportDTOS, request, command));
            }
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/exercisequestion/importexercisequestion.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseQuestionCommand command = new ExerciseQuestionCommand();
        Set<String> titleValue = buildSetValue();
        UploadUtil uploadUtil = new UploadUtil();
        Object[] objects = uploadUtil.writeOrUpdateFile(request, titleValue, WebConstant.EXERCISE_QUESTION);
        boolean checkUpload = (Boolean) objects[0];
        String urlType = "";

        if (objects[3] != null) {
            Map<String, String> mapValue = (HashMap<String, String>) objects[3];
            for (Map.Entry<String, String> item : mapValue.entrySet()) {
                if (item.getKey().equals("urlType")) {
                    urlType = item.getValue();
                }
            }
        }
        if (urlType.equals(READ_EXCEL)) {

            if (!checkUpload) {
                response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
            } else {
                if (objects[1] != null && objects[2] != null) {
                    String fileLocation = objects[1].toString();
                    String fileName = objects[2].toString();
                    try {
                        List<ExerciseQuestionImportDTO> excelValue = returnValueFromExcel(fileLocation, fileName);
                        validateData(excelValue);
                        SessionUtil.getInstance().setValue(request, IMPORT_EXERCISE_QUESTION_LIST, excelValue);
                        response.sendRedirect("/admin-execise-question-import-validate.html?urlType=validate_import");
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
                    }
                }
            }
        } else if (urlType.equals(IMPORT_DATA)) {
            if (SessionUtil.getInstance().getValue(request, IMPORT_EXERCISE_QUESTION_LIST) != null) {
                List<ExerciseQuestionImportDTO> exerciseQuestionImportDTOS = (List<ExerciseQuestionImportDTO>) SessionUtil.getInstance().getValue(request, IMPORT_EXERCISE_QUESTION_LIST);
                SingletonServiceUtil.getExerciseQuestionServiceInstance().saveImportExerciseQuestionList(exerciseQuestionImportDTOS);
                SessionUtil.getInstance().removeValue(request, IMPORT_EXERCISE_QUESTION_LIST);
                response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list");
            }
        } else if (urlType.equals(WebConstant.URL_EDIT)) {
            if (!checkUpload) {
                response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
            } else {
                Map<String, String> mapValue = (HashMap<String, String>) objects[3];
                ExerciseQuestionDTO dto = new ExerciseQuestionDTO();
                returnValueForDto(dto, mapValue);
                if (dto != null) {
                    String imageName = "";
                    String audioName = "";
                    if (objects[2] != null) {
                        String[] fileNames = objects[2].toString().split(",");
                        List<String> extensionImageFile = new ArrayList<String>();
                        extensionImageFile.addAll(Arrays.asList("jpg", "png"));
                        List<String> extensionAudioFile = new ArrayList<String>();
                        extensionAudioFile.addAll(Arrays.asList("mp3", "wav"));
                        for (String item : fileNames) {
                            if (extensionImageFile.contains(item.substring(item.lastIndexOf(".") + 1).toLowerCase())) {
                                imageName = item;
                            } else if (extensionAudioFile.contains(item.substring(item.lastIndexOf(".") + 1).toLowerCase())) {
                                audioName = item;
                            }
                        }
                    }
                    if (dto.getExerciseQuestionId() != null) {
                        ExerciseQuestionDTO exerciseQuestionDTO = SingletonServiceUtil.getExerciseQuestionServiceInstance().findExerciseQuestionById(dto.getExerciseQuestionId());
                        if (!StringUtils.isNotBlank(imageName)) {
                            dto.setImage(exerciseQuestionDTO.getImage());
                        }
                        if (!StringUtils.isNotBlank(audioName)) {
                            dto.setAudio(exerciseQuestionDTO.getAudio());
                        }
                        dto.setCreatedDate(exerciseQuestionDTO.getCreatedDate());
                        dto = SingletonServiceUtil.getExerciseQuestionServiceInstance().update(dto);
                        if (dto != null) {
                            response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_update");
                        } else {
                            response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
                        }
                    } else {
                        try {
                            dto.setImage(imageName);
                            dto.setAudio(audioName);
                            SingletonServiceUtil.getExerciseQuestionServiceInstance().save(dto);
                            response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_insert");
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
                        }
                    }
                }
            }
        }
        if (request.getParameter("crudaction") != null && request.getParameter("crudaction").equals(WebConstant.REDIRECT_DELETE)) {
            List<Integer> ids = new ArrayList<Integer>();
            String[] checkList = request.getParameterValues("checkList");
            for (String item : checkList) {
                ids.add(Integer.parseInt(item));
            }
            Integer result = SingletonServiceUtil.getExerciseQuestionServiceInstance().delete(ids);
            if (result != ids.size()) {
                response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_error");
            } else {
                response.sendRedirect("/admin-exercise-question-list.html?urlType=url_list&&crudaction=redirect_delete");
            }
        }
    }

    private List<ExerciseQuestionImportDTO> returnListImport(List<ExerciseQuestionImportDTO> exerciseQuestionImportDTOS, HttpServletRequest request, ExerciseQuestionCommand command) {
        command.setMaxPageItems(3);
        command.setTotalItems(exerciseQuestionImportDTOS.size());
        RequestUtil.initSearchBean(request, command);
        Integer fromIndex = (command.getPage() - 1) * command.getMaxPageItems();
        if (fromIndex > command.getTotalItems()) {
            fromIndex = 0;
            command.setFirstItem(0);
            command.setPage(1);
        }
        Integer toIndex = fromIndex + command.getMaxPageItems();
        if (toIndex > command.getTotalItems()) {
            toIndex = command.getTotalItems();
        }
        return exerciseQuestionImportDTOS.subList(fromIndex, toIndex);
    }

    private void validateData(List<ExerciseQuestionImportDTO> excelValue) {
        for (ExerciseQuestionImportDTO item: excelValue) {
            validateRequired(item);
        }
        SingletonServiceUtil.getExerciseQuestionServiceInstance().validateImportExerciseQuestionImport(excelValue);
    }

    private void validateRequired(ExerciseQuestionImportDTO dto) {
        String message = "";
        if (StringUtils.isBlank(dto.getQuestion())) {
            message += "<br/>";
            message += bundle.getString("label.exercise.question.not.empty");
        }
        if (StringUtils.isBlank(dto.getCorrectAnswer())) {
            message += "<br/>";
            message += bundle.getString("label.exercise.correct.answer.not.empty");
        }
        if (StringUtils.isBlank(dto.getExerciseName())) {
            message += "<br/>";
            message += bundle.getString("label.exercise.name.not.empty");
        }
        if (StringUtils.isNotBlank(message)) {
            dto.setValid(false);
            dto.setMessage(message.substring(5));
        }
    }

    private List<ExerciseQuestionImportDTO> returnValueFromExcel (String fileLocation, String fileName) throws
        Exception {
            Workbook workbook = ExcelPoiUtil.getWorkBook(fileLocation, fileName);
            Sheet sheet = workbook.getSheetAt(0);
            List<ExerciseQuestionImportDTO> excelValue = returnDataFromExcel(sheet);
            return excelValue;
        }

        private List<ExerciseQuestionImportDTO> returnDataFromExcel (Sheet sheet){
            List<ExerciseQuestionImportDTO> excelValue = new ArrayList<ExerciseQuestionImportDTO>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                ExerciseQuestionImportDTO dto = new ExerciseQuestionImportDTO();
                dto.setQuestion(ExcelPoiUtil.getCellValue(row.getCell(0)));
                dto.setImage(ExcelPoiUtil.getCellValue(row.getCell(1)));
                dto.setAudio(ExcelPoiUtil.getCellValue(row.getCell(2)));
                dto.setCorrectAnswer(ExcelPoiUtil.getCellValue(row.getCell(3)));
                dto.setExerciseName(ExcelPoiUtil.getCellValue(row.getCell(4)));
                excelValue.add(dto);
            }
            return excelValue;
        }

        private Map<String, String> buildMapMessage (ResourceBundle bundle){
            Map<String, String> mapMessage = new HashMap<String, String>();
            mapMessage.put(WebConstant.REDIRECT_INSERT, bundle.getString("label.exercise.question.insert.success"));
            mapMessage.put(WebConstant.REDIRECT_UPDATE, bundle.getString("label.exercise.question.update.success"));
            mapMessage.put(WebConstant.REDIRECT_DELETE, bundle.getString("label.exercise.question.delete.success"));
            mapMessage.put(WebConstant.REDIRECT_ERROR, bundle.getString("label.error"));
            return mapMessage;
        }

        private void returnValueForDto (ExerciseQuestionDTO dto, Map < String, String > mapValue){
            for (Map.Entry<String, String> item : mapValue.entrySet()) {
                if (item.getKey().equals("pojo.question")) {
                    dto.setQuestion(item.getValue());
                }
                if (item.getKey().equals("pojo.correctAnswer")) {
                    dto.setCorrectAnswer(item.getValue());
                }
                if (item.getKey().equals("pojo.exerciseQuestionId")) {
                    dto.setExerciseQuestionId(Integer.parseInt(item.getValue()));
                }
                if (item.getKey().equals("pojo.exerciseDTO.exerciseId")) {
                    ExerciseDTO exerciseDTO = new ExerciseDTO();
                    exerciseDTO.setExerciseId(Integer.parseInt(item.getValue()));
                    dto.setExerciseDTO(exerciseDTO);
                }
            }
        }

        private void getExerciseQuestion (HttpServletRequest request, ExerciseQuestionCommand command){
            String whereClause = buildWhereClause(command);
            RequestUtil.initSearchBean(request, command);
            if (!whereClause.equals("not-exist-exercise")) {
                Object[] objects = SingletonServiceUtil.getExerciseQuestionServiceInstance().findExerciseQuestionByProperties(null, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems(), whereClause);
                command.setListResult((List<ExerciseQuestionDTO>) objects[1]);
                command.setTotalItems((objects[0] != null) ? Integer.parseInt(objects[0].toString()) : 0);
            }
        }

        private String buildWhereClause (ExerciseQuestionCommand command){
            StringBuilder whereClause = new StringBuilder();
            if (StringUtils.isNotBlank(command.getExerciseNameSearch())) {
                String sql = " AND LOWER(name) LIKE '%" + command.getExerciseNameSearch() + "%'";
                Object[] objects = SingletonServiceUtil.getExerciseServiceInstance().findExerciseByProperties(null, null, null, null, null, sql);
                List<ExerciseDTO> exerciseDTOS = (List<ExerciseDTO>) objects[1];

                if (exerciseDTOS != null && exerciseDTOS.size() > 0) {
                    int i = 0;
                    for (ExerciseDTO item : exerciseDTOS) {
                        if (i == 0) {
                            whereClause.append(" AND (exerciseid = " + item.getExerciseId() + " ");
                            i++;
                        } else {
                            whereClause.append(" OR exerciseid = " + item.getExerciseId() + " ");
                        }
                    }
                    whereClause.append(" ) ");
                } else {
                    whereClause.append("not-exist-exercise");
                }
            }
            return whereClause.toString();
        }

        private List<String> buildAnswerList () {
            List<String> answerList = new ArrayList<String>();
            answerList.add("A");
            answerList.add("B");
            answerList.add("C");
            answerList.add("D");
            return answerList;
        }

        private Set<String> buildSetValue () {
            Set<String> titleValue = new HashSet<String>();
            titleValue.add("pojo.question");
            titleValue.add("pojo.correctAnswer");
            titleValue.add("pojo.exerciseQuestionId");
            titleValue.add("pojo.exerciseDTO.exerciseId");
            titleValue.add("urlType");
            return titleValue;
        }
}
