<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
%>
<jsp:include page="../include/header.jsp"/>
<%--首页--%>
<div id="page-wrapper">
    <div class="row" style="margin: 5px; font-size: 18px">
        <span>购票</span>
    </div>
    <form id="defaultForm" class="form-horizontal" action="/api/ticket" method="post">
        <input type="hidden" name="id" value="${train.id}">
        <div class="form-group">
            <label for="trainId" class="col-sm-2 control-label">车次</label>
            <div class="col-sm-10">
                <input type="text" readonly="readonly" value="${train.name}" class="form-control" name="trainId"
                       id="trainId" placeholder="车次">
            </div>
        </div>

        <div class="form-group">
            <label for="startSite" class="col-sm-2 control-label">起始站</label>
            <div class="col-sm-10">
                <input type="text" readonly="readonly" value="${train.startSite}" class="form-control" name="startSite"
                       id="startSite" placeholder="起始站">
            </div>
        </div>

        <div class="form-group">
            <label for="endSite" class="col-sm-2 control-label">终到站</label>
            <div class="col-sm-10">
                <input type="text" readonly="readonly" value="${train.endSite}" class="form-control" name="endSite"
                       id="endSite" placeholder="终到站">
            </div>
        </div>

        <div class="form-group">
            <label for="seatsNumber" class="col-sm-2 control-label">余票</label>
            <div class="col-sm-10">
                <input type="text" readonly="readonly" value="${train.seatsNumber}" class="form-control"
                       name="seatsNumber" id="seatsNumber" placeholder="余票">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">选择乘车人</label>

            <div class="col-sm-7">
                <select id="passengerSelected" class="form-control" name="userId">
                    <option
                            selected>请选择乘车人
                    </option>
                    <%--循环展示--%>
                    <c:forEach items="${contacts}" var="item">
                        <option
                                value="${item.id}">${item.userName}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary btn-block btn-sm active">购买</button>
            </div>
        </div>
        <div style="color: red">
            ${msg}
        </div>
    </form>
</div>