<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../include/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">欢迎注册!</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="/api/register" method="post" id="defaultForm">
                        <fieldset>

                            <div class="form-group">
                                <input class="form-control" value="${data.loginName}" placeholder="昵称/登录名"
                                       name="loginName" type="text" autofocus>
                            </div>


                            <div class="form-group">
                                <input class="form-control" value="${data.name}" placeholder="姓名"
                                       name="name" type="text">
                            </div>

                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="手机号" name="phone"
                                       value="${data.phone}"/>
                            </div>

                            <div class="form-group">
                                <input class="form-control" value="${data.idCardNumber}" placeholder="身份证"
                                       name="idCardNumber" id="idCardNumber" type="text">
                            </div>

                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="密码" name="password" value=""/>
                            </div>


                            <div class="form-group">
                                <img src="/api/validateCode" name="validateCode">
                            </div>
                            <button type="submit" id="validateBtn" class="btn btn-lg btn-success btn-block">注册</button>
                        </fieldset>
                    </form>
                    <div style="color: red">
                        ${msg}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    /*
     *表单校验
     */
    $(function () {
        $('form').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '请填写您的姓名'
                        }
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: '您应该填写手机'
                        },
                        stringLength: {
                            min: 11,
                            message: '联系方式应该不少于11位'
                        }
                    }
                },
                loginName: {
                    validators: {
                        notEmpty: {
                            message: '请填写您的登录名'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '请填写密码'
                        },
                        stringLength: {
                            min: 6,
                            message: '密码不少于6位'
                        }
                    }
                },
                idCardNumber: {
                    validators: {
                        notEmpty: {
                            message: '请填写身份证号码'
                        },
                        stringLength: {
                            min: 15,
                            max: 18,
                            message: '身份证不少于15位'
                        }
                    }
                }
            }
        });
    });

    /*
     * 当点击了确定下单的按钮后调用此方法
     * 然后执行表单校验
     * */
    $('#validateBtn').click(function () {
        $('#defaultForm').bootstrapValidator('validate');
    });
</script>