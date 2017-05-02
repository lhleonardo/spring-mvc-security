<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Casa do Código</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<c:url value="/resources/css" var="cssPath" />
	<c:url value="/resources/js" var="jsPath" />
	<link rel="stylesheet" type="text/css" href="${cssPath }/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${cssPath }/bootstrap-theme.min.css">
	<link rel="stylesheet" type="text/css" href="${cssPath }/padrao.css">
</head>
<body>
	<header>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<c:url value='/'></c:url>">Casa do Código</a>
				</div>

				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value='/'></c:url>">Home</a></li>
						<li><a href="${s:mvcUrl('PC#form').build() }">Produtos</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="${s:mvcUrl('CC#lista').build() }"><span class="glyphicon glyphicon-shopping-cart"></span>Carrinho
								<span class="badge">${carrinho.quantidade }</span></a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<div class="container">
		<h1>Cadastrar novo produto</h1>
		<form:form action="${s:mvcUrl('PC#grava').build() }" method="post" commandName="produto" enctype="multipart/form-data">
			<div class="form-group">
				<label>Título</label>
				<form:input path="titulo" cssClass="form-control" />
				<form:errors path="titulo" />
			</div>
			<div class="form-group">
				<label>Descrição</label>
				<form:textarea path="descricao" cssClass="form-control" />
				<form:errors path="descricao" />
			</div>
			<div class="form-group">
				<label>Páginas</label>
				<form:input path="paginas" />
				<form:errors path="paginas" />
			</div>

			<div class="form-group">
				<label>Data de Lançamento</label>
				<form:input path="dataLancamento" cssClass="form-control" />
				<form:errors path="dataLancamento" />
			</div>
			<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
				<div class="form-group">
					<label>${tipoPreco}</label>
					<form:input path="precos[${status.index}].valor" cssClass="form-control" />
					<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}" />
				</div>
			</c:forEach>

			<div class="form-group">
				<label>Sumário</label> <input name="sumario" type="file" class="form-control" />
			</div>
			<button type="submit" class="btn btn-primary">Cadastrar</button>
		</form:form>
	</div>
	<script type="text/javascript" charset="UTF-8" src="${jsPath }/jquery.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${jsPath }/bootstrap.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${jsPath }/input-number/input-number.js"></script>
</body>
</html>