<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login da Casa do Código</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<c:url value="/resources/css" var="cssPath"/>
		<c:url value="/resources/js" var="jsPath"/>
		<c:url value="/resources/imagens" var="imgPath" />
		<link rel="stylesheet" type="text/css" href="${cssPath }/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="${cssPath }/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="${cssPath }/login/login.css">
	</head>
<body>
	<div class="container">
		<div class="row" id="pwd-container">
			<div class="col-md-4">
				<c:if test="${param.error != null}">
					<div>
						Failed to login.
						<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                  Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
						</c:if>
					</div>
				</c:if>
			</div>
			<div class="col-md-4">
				<section class="login-form">
					<form:form method="post" servletRelativeAction="/login" role="login">
						<img src="${imgPath }/cdc-logo.svg" class="img-responsive" alt="Logo da Casa do Código" />
						<input type="email" name="username" placeholder="Email" required class="form-control input-lg" />
						<input type="password" name="password" class="form-control input-lg" id="password" placeholder="Senha" required />
						<input type="checkbox" name="remember-me" id="remember-me"/>
						<label for="remember-me" style="color:white;">Lembrar os dados?</label>
						<button type="submit" class="btn btn-lg btn-primary btn-block">Entrar</button>
						<div class="links">
							<a href="#">Criar uma conta</a> <span class="ou">ou</span> <a href="#">recuperar a senha</a>
						</div>
					</form:form>
					<div class="form-links">
						<a href="#">casadocodigo.com.br</a>
					</div>
				</section>
			</div>
		</div>
	</div>
</body>
</html>