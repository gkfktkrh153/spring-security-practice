<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insert title here</title>
</head>
<body>
    <h1>회원가입폼</h1>
    <hr>
    <form action="/join" method="post">
        <input type="text" name="username" placeholder="이름"><br/>
        <input type="password" name="password" placeholder="비밀번호"><br/>
        <input type="text" name="email" placeholder="이메일"><br/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"><br/>
        <button>회원가입</button>
    </form>

</body>
</html>