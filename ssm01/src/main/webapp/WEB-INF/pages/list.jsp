
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <h1 style="background-color: lime">查询了所有的账户信息</h1>
        <c:forEach items="${list}" var="account">
            ${account.name}
        </c:forEach>
</body>
</html>
