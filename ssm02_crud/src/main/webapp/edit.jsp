<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <%--引入bootstrap--%>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <%--引入jquery--%>
    <script type="text/javascript" src="static/js/jquery-1.4.2.min.js"></script>
</head>
<body>

</br>
</br></br>
</br></br>

<form class="form-horizontal" style="border: 1px" id="empUpdateForm">
    <div class="form-group">
        <label class="col-sm-4 control-label" style="color: #c12e2a;font-size: larger">修改员工</label>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">empName</label>
        <div class="col-sm-4">
            <p class="form-control-static" id="empName_update_static"></p>
            <span class="help-block"></span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">email</label>
        <div class="col-sm-4">
            <input type="text" name="email" class="form-control" id="email_update_input" placeholder="email@163.com">
            <span class="help-block"></span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">gender</label>
        <div class="col-sm-4">
            <label class="radio-inline">
                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
            </label>
            <label class="radio-inline">
                <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
            </label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">deptName</label>
        <div class="col-sm-4">
            <!-- 部门提交部门id即可 -->
            <select class="form-control" name="dId" id="dept_sel">
            </select>
        </div>
    </div>
</form>
<div class="text-center">
    <button type="button" class="btn btn-success" data-dismiss="modal" id="emp_add_modal_btn">查询部门</button>
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
</div>




<script>

    $("#emp_add_modal_btn").click(function(){
        getDepts("#dept_sel");
    });
    //查出所有的部门信息并显示在下拉列表中
    function getDepts(ele) {
        //清空之前下拉列表的值
        $(ele).empty();
        $.ajax({
            url: "/ssm02/depts",
            type: "GET",
            success: function (result) {
                //{"code":100,"msg":"处理成功！",
                //"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
                //console.log(result);
                //显示部门信息在下拉列表中
                //$("#empAddModal select").append("")
                $.each(result.extend.depts, function () {
                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo(ele);
                });
            }
        });
    }


    //校验表单数据
    function validate_add_form(){
        //1、拿到要校验的数据，使用正则表达式
        var empName = $("#empName_add_input").val();
        var regName = /(^[a-zA-Z0-9_-]{4,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
        if(!regName.test(empName)){
            //alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
            show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
            return false;
        }else{
            show_validate_msg("#empName_add_input", "success", "");
        };

        //2、校验邮箱信息
        var email = $("#email_add_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if(!regEmail.test(email)){
            //alert("邮箱格式不正确");
            //应该清空这个元素之前的样式
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确");

            return false;
        }else{
            show_validate_msg("#email_add_input", "success", "");
        }
        return true;
    }


    //显示校验结果的提示信息
    function show_validate_msg(ele,status,msg){
        //清除当前元素的校验状态
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if("success"==status){
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if("error" == status){
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }


    //校验用户名是否可用
    $("#empName_add_input").change(function(){
        //发送ajax请求校验用户名是否可用
        var empName = this.value;
        $.ajax({
            url:"${APP_PATH}/checkUser",
            data:"empName="+empName,
            type:"POST",
            success:function(result){
                if(result.code==100){
                    show_validate_msg("#empName_add_input","success","用户名可用");
                    $("#emp_save_btn").attr("ajax-va","success");
                }else{
                    show_validate_msg("#empName_add_input","error","用户名已经被使用！");
                    $("#emp_save_btn").attr("ajax-va","error");
                }
            }
        });
    });

    //回显员工信息
    function getEmp(id) {
        $.ajax({
            url:"${APP_PATH}/emp/"+id,
            type:"GET",
            success:function (result) {
                var empData = result.extend.emp;
                $("#empName_update_static").text(empData.empName);
                $("#empName_update_input").val(empData.email);
                $("#oo  input[name=gender]").val([empData.gender]);
                $("#").val([empData.dId]);
            }
        });
    }





</script>
</body>
</html>
