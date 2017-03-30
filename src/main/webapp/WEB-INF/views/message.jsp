<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="false" %>
<%
    String contextPath = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + contextPath + "/";
%>
<%--用户下单成功提示页面--%>
<jsp:include page="include/header.jsp"/>
<body>
<%--bootstrap的一个modal可以理解为一个小窗口--%>
<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <%--窗体的header--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <%--窗体title--%>
                <h4 class="modal-title">message</h4>
            </div>
            <%--窗体内容--%>
            <div class="modal-body">
                <p>${msg}</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="cancel" class="btn btn-default" data-dismiss="modal">关闭</button>

                <button type="button" id="ensure" class="btn btn-primary">返回上一页</button>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" language="JavaScript">
    /*js脚本,用于在进入页面就执行窗体的show方法让窗体展示出来,因为窗体是默认隐藏的*/
    $(function () {
        //设置窗体为显示
        $('#myModal').modal('show');

        //当点击确定按钮执行的代码
        $('#ensure').click(function () {
            history.go(-1);
        });
    });
</script>
