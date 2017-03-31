<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>

<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/left.jsp"/>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">东方航空公司售票系统</h1>
        </div>
    </div>

    <div class="col-lg-8">
        <div name="appType" class="input-group">
            <span class="input-group-addon">车次查询</span>

            <form id="reListDealByType" action="/api/trains" method="post">
                <div class="col-lg-4">
                    <span class="input-group-addon">车次名</span>
                    <input type="text" name="name" class="form-control" placeholder="Search"
                           value="${data.name}">
                </div>

                <div class="col-lg-4">
                    <span class="input-group-addon">起始站</span>
                    <input type="text" name="startSite" class="form-control" placeholder="起始站"
                           value="${data.startSite}">
                </div>

                <div class="col-lg-4">
                    <span class="input-group-addon">终到站</span>
                    <input type="text" name="endSite" class="form-control" placeholder="终到站"
                           value="${data.endSite}">
                </div>
                <div class="col-lg-2">
                    <button type="submit" class="btn btn-primary">查询</button>
                </div>
            </form>
        </div>
    </div>
    <div class="col-lg-12" style="margin: 5px"></div>
    <div class="col-lg-12" style="margin: 10px"></div>
    <div class="row">
        <div class="col-lg-12">
            <table class="table table-bordered table-hover table-condensed" style="font-size:12px;">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>车次名</td>
                    <td>起始站</td>
                    <td>终到站</td>
                    <td>余票</td>
                    <td colspan="3">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${trains}" var="data">
                    <tr>
                        <td>${data.id}</td>
                        <td>${data.name}</td>
                        <td>${data.startSite}</td>
                        <td>${data.endSite}</td>
                        <td>${data.seatsNumber}</td>
                        <td><a href="<%=contextPath%>/api/trainInfo/${data.id}">购买</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>