<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="formUrl" value="/admin-exercise-question-edit.html"/>
<html>
<head>
    <title><fmt:message key="label.exercise.question.edit" bundle="${lang}"/></title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#"><fmt:message key="label.home" bundle="${lang}"/></a>
                </li>
                <li class="active"><fmt:message key="label.exercise.question.edit" bundle="${lang}"/></li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${not empty messageResponse}">
                        <div class="alert alert-block alert-${alert}">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                                ${messageResponse}
                        </div>
                    </c:if>
                    <form action="${formUrl}" method="post" enctype="multipart/form-data" id="formEdit">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.exercise.question.field.question" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="pojo.question" id="question" value="${item.pojo.question}" style="width: 800px"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.upload.image" bundle="${lang}"/></label>
                        <div class="col-sm-9">
                            <input type="file" name="file" id="uploadImage"/>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.view.image" bundle="${lang}"/></label>
                            <c:if test="${not empty item.pojo.image}">
                                <c:set var="image" value="/repository/${item.pojo.image}"/>
                            </c:if>
                            <div class="col-sm-9">
                                <img src="${image}" name="viewImage" id="viewImage" width="150px" height="150px"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.upload.audio" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <input type="file" name="fileAudio" id="uploadAudio"/>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="col-sm-12"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.view.audio" bundle="${lang}"/></label>
                            <c:if test="${not empty item.pojo.audio}">
                                <c:set var="audio" value="/repository/${item.pojo.audio}"/>
                            </c:if>
                            <div class="col-sm-9">
                                <audio controls src="${audio}" type="audio/mpeg" id="listenAudio" name="listenAudio">
                                </audio>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.exercise.question.correct.answer" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                                <c:if test="${not empty item.pojo.correctAnswer}">
                                    <select id="correctAnswer" name="pojo.correctAnswer">
                                        <option value="${item.pojo.correctAnswer}">${item.pojo.correctAnswer}</option>
                                        <c:forEach items="${item.answerList}" var="itemAnswer">
                                            <c:if test="${itemAnswer != item.pojo.correctAnswer}">
                                                <option value="${itemAnswer}">${itemAnswer}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:if>
                                <c:if test="${empty item.pojo.correctAnswer}">
                                    <select id="correctAnswer" name="pojo.correctAnswer">
                                        <option value=""><fmt:message key="label.choose.answer" bundle="${lang}"/></option>
                                        <c:forEach items="${item.answerList}" var="itemAnswer">
                                                <option value="${itemAnswer}">${itemAnswer}</option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                            </div>
                        </div>
                        <br/>
                        <br/>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.choose.exercise" bundle="${lang}"/></label>
                            <div class="col-sm-9">
                            <c:choose>
                                <c:when test="${not empty item.pojo.exerciseDTO.exerciseId}">
                                    <select id="exercise" name="pojo.exerciseDTO.exerciseId">
                                        <%--<option value="${item.pojo.exerciseDTO.exerciseId}">${item.pojo.exerciseDTO.name}</option>--%>
                                        <c:forEach items="${item.exercises}" var="itemExercise">
                                            <c:if test="${itemExercise.exerciseId == item.pojo.exerciseDTO.exerciseId}">
                                                <option value="${itemExercise.exerciseId}" selected>${itemExercise.name}</option>
                                            </c:if>
                                            <c:if test="${itemExercise.exerciseId != item.pojo.exerciseDTO.exerciseId}">
                                                <option value="${itemExercise.exerciseId}">${itemExercise.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select id="exercise" name="pojo.exerciseDTO.exerciseId">
                                        <option value=""><fmt:message key="label.choose.exercise" bundle="${lang}"/></option>
                                        <c:forEach items="${item.exercises}" var="itemExercise">
                                            <option value="${itemExercise.exerciseId}">${itemExercise.name}</option>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <input type="submit" class="btn btn-white btn-warning btn-bold" value="<fmt:message key="label.done" bundle="${lang}"/>"/>
                            </div>
                        </div>
                        <c:if test="${not empty item.pojo.exerciseQuestionId}">
                            <input type="hidden" name="pojo.exerciseQuestionId" value="${item.pojo.exerciseQuestionId}"/>
                        </c:if>
                            <input type="hidden" name="urlType" value="url_edit"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // var exerciseQuestionImage = null;
    // var exerciseQuestionAudio = null;
    <c:if test="${not empty item.pojo.image}">
        exerciseQuestionImage = "${item.pojo.image}";
    </c:if>
    <c:if test="${not empty item.pojo.audio}">
        exerciseQuestionAudio = "${item.pojo.audio}";
    </c:if>
    $(document).ready(function () {
        validateData();
        $("#uploadImage").change(function () {
            readURL(this, "viewImage");
        });
        $("#uploadAudio").change(function () {
           readURL(this, "listenAudio");
        });
    });
    
    function validateData() {
        $("#formEdit").validate({
            rules: [],
            messages: []
        });
        $("#question").rules("add", {
            required: true,
            messages: {
                required: "<fmt:message key='label.not.empty' bundle='${lang}'/>"
            }
        });

        <%--if (exerciseQuestionImage == null) {--%>
            <%--$("#uploadImage").rules("add", {--%>
                <%--required: true,--%>
                <%--messages: {--%>
                    <%--required: "<fmt:message key='label.not.empty' bundle='${lang}'/>"--%>
                <%--}--%>
            <%--});--%>
        <%--}--%>

        <%--if (exerciseQuestionAudio == null) {--%>
            <%--$("#uploadAudio").rules("add", {--%>
                <%--required: true,--%>
                <%--messages: {--%>
                    <%--required: "<fmt:message key='label.not.empty' bundle='${lang}'/>"--%>
                <%--}--%>
            <%--});--%>
        <%--}--%>

        $("#correctAnswer").rules("add", {
            required: true,
            messages: {
                required: "<fmt:message key='label.not.empty' bundle='${lang}'/>"
            }
        });
        $("#exercise").rules("add", {
            required: true,
            messages: {
                required: "<fmt:message key='label.not.empty' bundle='${lang}'/>"
            }
        });
    }

    function readURL(input, imageId) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#" + imageId).attr("src", reader.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>