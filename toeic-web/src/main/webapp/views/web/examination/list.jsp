<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="urlList" value="/danh-sach-bai-thi.html"/>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form action="${urlList}" method="get" id="formUrl">
    <div class="wrap">
        <div class="main">
            <div class="content">
                <div class="col span_2_of_3">
                    <div class="contact-form">
                        <div>
                                <span>
                                    <input name="pojo.name" type="text" class="textbox" value="${items.pojo.name}"/>
                                </span>
                        </div>
                        <div>
                            <button class="btn btn-sm btn-success">
                                <fmt:message key="label.search" bundle="${lang}"/>
                            </button>
                        </div>
                    </div>
                </div>
                <c:forEach var="item" items="${items.listResult}">
                    <div class="image group">
                        <div class="grid news_desc">
                            <h3>${item.name}</h3>
                            <c:if test="${not empty sessionUser}">
                                <c:url value="/bai-thi-thuc-hanh.html" var="detailUrl">
                                    <c:param name="examinationId" value="${item.examinationId}"/>
                                </c:url>
                                <h4><span><a href="${detailUrl}"><fmt:message key="label.do.examniation" bundle="${lang}"/> </a></span></h4>
                            </c:if>
                            <c:if test="${empty sessionUser}">
                                <c:url value="/login.html" var="loginUrl">
                                    <c:param name="action" value="login"/>
                                </c:url>
                                <h4><span><a href="${loginUrl}"><fmt:message key="label.login.to.do.examniation" bundle="${lang}"/> </a></span></h4>
                            </c:if>

                        </div>
                    </div>
                </c:forEach>
                <ul id="pagination-demo" class="pagination-sm"></ul>
            </div>
        </div>
    </div>
    <input type="hidden" id="page" name="page"/>
</form>
<script type="text/javascript">
    var totalPages = ${items.totalPages};
    var startPage = ${items.page};
    var visiblePages = ${items.maxPageItems};
    $(document).ready(function () {
    });
    $(function () {
        var obj = $('#pagination-demo').twbsPagination({
            totalPages: totalPages,
            startPage: startPage,
            visiblePages: visiblePages,
            onPageClick: function (event, page) {
                if (page != startPage) {
                    $('#page').val(page);
                    $('#formUrl').submit();
                }
            }
        });
    });
</script>
</body>
</html>