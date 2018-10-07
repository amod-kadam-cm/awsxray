
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="regInfoDAO"
	class="com.cloudmanthan.k8s.workshop.dao.RegisterDAO" />
<jsp:useBean id="regInfo"
	class="com.cloudmanthan.k8s.workshop.model.RegistrationInfo" />
<jsp:setProperty property="*" name="regInfo" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<%
		if (true == regInfoDAO.register(regInfo)) {

			response.sendRedirect("success.html");

		} else {
			response.sendRedirect("error.html");
		}
	%>

</body>
</html>