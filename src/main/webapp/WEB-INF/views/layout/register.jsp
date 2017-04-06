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
                    <form role="form" action="/api/register" method="post" onsubmit="onsubmitFn()" id="defaultForm">
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
                                <input type="password" name="repeatPassword" class="form-control" placeholder="确认密码"
                                       id="repeatPassword"
                                       value=""/>
                            </div>


                            <div class="form-group">
                                <select id="passengerSelected" class="form-control" name="sex">
                                    <option value="1" selected>男</option>
                                    <option value="0" selected>女</option>
                                </select>
                            </div>


                            <div class="form-group">
                                <div class="col-lg-6">
                                    <input class="form-control" type="text" placeholder="验证码"
                                           id="validateCode" name="validateCode"/>
                                </div>
                                <div class="col-lg-5">
                                    <img class="form-control" src="/api/validateCode" id="verification"
                                         onclick="refreshcode()">
                                </div>
                            </div>

                            <div class="form-group">
                                <button type="submit" id="validateBtn" class="btn btn-lg btn-success btn-block">注册
                                </button>
                            </div>
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

    function refreshcode() {
        document.getElementById("verification").src = "/api/validateCode?hehe=" + Math.random();
    }
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
                repeatPassword: {
                    validators: {
                        notEmpty: {
                            message: '请填写确认密码'
                        },
                        identical: {
                            field: 'password',
                            message: '确认密码与密码不一致'
                        },
                    }
                },
                validateCode: {
                    verbose: false,
                    validators: {
                        notEmpty: {
                            message: '请填写验证码'
                        },
                        stringLength: {
                            min: 4,
                            message: '验证码为4位'
                        },
                        remote: {
                            type: 'GET',
                            url: '/api/validateCode',
                            message: '验证码不匹配',
                            delay: 1000
                        }
                    }
                },
                idCardNumber: {
                    verbose: false,
                    validators: {
                        notEmpty: {
                            message: '请填写身份证号码'
                        },
                        stringLength: {
                            min: 15,
                            max: 18,
                            message: '身份证不少于15位,不高于18位'
                        },
                        remote: {
                            type: 'GET',
                            url: '/api/idCard/validate',
                            message: '身份证不合法或该ID已注册',
                            delay: 500
                        }
                    }
                }
            }
        })
        ;
    });

    /*
     * 当点击了确定下单的按钮后调用此方法
     * 然后执行表单校验
     * */

    function onsubmitFn() {
        var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        return bootstrapValidator.validate();
    }
</script>