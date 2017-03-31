<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>

<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/left.jsp"/>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">东方航空公司售票系统</h1>
        </div>
    </div>
    <%
        String contextPath = request.getContextPath();
    %>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">用户详情</h1>
            </div>
        </div>
        <div class="row">

            <div class="col-lg-12">
                <div class="col-lg-8">

                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">基本信息</div>
                            <div class="panel-body">
                                <p>
                                    昵称 : ${user.loginName}
                                </p>

                                <p>
                                    姓名 : ${user.name}
                                </p>

                                <p>手机号(phone) : ${user.phone}</p>
                            </div>
                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading">用户订单列表
                            </div>

                            <table class="table table-bordered table-hover table-condensed" style="font-size:12px;">
                                <thead>
                                <tr>
                                    <td><span>序号</span></td>
                                    <td><span>车次</span></td>
                                    <td><span>乘车人</span></td>
                                    <td><span>起始站</span></td>
                                    <td><span>终到站</span></td>
                                    <td><span>折扣值</span></td>
                                    <td><span>价格</span></td>
                                    <td><span>下单时间</span></td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${orders}" var="order">
                                    <tr>
                                        <td>${order.id}</td>
                                        <td>${order.name}</td>
                                        <td>${order.passenger}</td>
                                        <td>${order.startSite}</td>
                                        <td>${order.endSite}</td>
                                        <td>${order.discount}</td>
                                        <td>${order.price}</td>
                                        <td>${order.orderTime}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                        </div>

                        <div class="panel panel-default">
                            <div class="panel-heading">联系人列表
                            </div>

                            <table class="table table-bordered table-hover table-condensed" style="font-size:12px;">
                                <thead>
                                <tr>
                                    <td><span>姓名</span></td>
                                    <td><span>身份证</span></td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${contacts}" var="contact">
                                    <tr>
                                        <td>${contact.userName}</td>
                                        <td>${contact.idCardNumber}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>