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
    <%--web路径
     *不以‘/’开始的相对路径，以当前路径为基准
     以‘/’开始的相对路径，以服务器根路径开始的基准
    --%>
    <%--引入bootstrap--%>
    <link href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <%--引入jquery--%>
    <script type="text/javascript" src="/static/js/jquery-1.4.2.min.js"></script>
</head>
<body>
    <div class="container">
            <%--标题--%>
        <div class="row">
            <div class="col-md-12">
                <h1>SSM02_CRUD</h1>
            </div>
        </div>
            <%--两个按钮--%>
        <div class="row">
            <div class="col-md-4 col-md-offset-8">
                <button class="btn btn-success">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>
            <%--显示数据--%>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>department</th>
                        <th>操作</th>
                    </tr>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.gender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <button class="btn btn-success btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                删除
                            </button>
                        </th>
                    </tr>
                </c:forEach>
                </table>
            </div>
        </div>
            <%--分页信息--%>
        <div class="row">
            <%--分页的文字信息--%>
            <div class="col-md-6">
                <h3 style="color: blue">当前是第 ${pageInfo.pageNum} 页,总共有 ${pageInfo.pages} 页,一共有 ${pageInfo.total} 条记录。</h3>
            </div>
            <%--分页条--%>
            <div class="col-md-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">

                        <li><a href="/ssm02/emps?pn=1">首页</a></li>
                        <c:if test="${pageInfo.hasPreviousPage}">
                            <li>
                                <a href="/ssm02/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>


                    <c:forEach  items="${pageInfo.navigatepageNums}" var="page_Num">

                        <c:if test="${page_Num == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Num}</a></li>
                        </c:if>

                        <c:if test="${page_Num != pageInfo.pageNum}">
                            <li><a href="/ssm02/emps?pn=${page_Num }">${page_Num}</a></li>
                        </c:if>

                    </c:forEach>

                        <c:if test="${pageInfo.hasNextPage}">
                            <li>
                                <a href="/ssm02/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <li><a href="/ssm02/emps?pn=${pageInfo.pages}">末页</a></li>
                    </ul>
                </nav>
            </div>

        </div>

    </div>
</body>
</html>
