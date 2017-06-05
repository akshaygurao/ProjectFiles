<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compose Message</title>
    </head>
    <body>
    <h1>Reply Message Page</h1>
        <form action='reply.htm' method="post">
            <p><b> From : </b> <c:out value='${sessionScope.userName}' /></p>
            <p><b> To : </b><c:out value="${param.to}" /></p>
            <p>Message : </p>
            <textarea name='message' rows='6' cols="40"></textarea><br />
            <input type='submit' value='send' />
            <input type='hidden' value ='${param.to}' name='to'/>
        </form>



    </body>
</html>
