<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Casa do Código</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<c:url value="/resources/css" var="cssPath"/>
		<c:url value="/resources/js" var="jsPath"/>
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
					<li class="active"><a href="${s:mvcUrl('PC#form').build() }">Produtos</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<security:authorize access="isAuthenticated()">
							<p class="navbar-text">
								Conectado como
								<security:authentication property="principal.username" />
							</p>
						</security:authorize>
						<li><a href="${s:mvcUrl('CC#lista').build() }"><span class="glyphicon glyphicon-shopping-cart"></span>Carrinho
									<span class="badge">${carrinho.quantidade }</span></a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	
	<c:if test="${not empty success }">
		<div>${success }</div>
	</c:if>

	<c:if test="${not empty error }">
		<div>${error }</div>
	</c:if>


	<section class="container-fluid">
		<div class="row titulo">
			<div class="col-md-10">
				<h1 class="inline">Produtos cadastrados</h1>
			</div>
			<div class="col-md-2">
				<a href="${s:mvcUrl('PC#form').build() }" class="btn btn-success" role="button">Novo produto</a>
			</div>
		</div>
		
		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th>ID</th>
					<th>Titulo</th>
					<th>Descrição</th>
					<th>Páginas</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="produto">
					<tr>
						<td>${produto.getId()}</td>
						<td><a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build() }">${produto.titulo }</a></td>
						<td>${produto.descricao}</td>
						<td>${produto.paginas}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section>
	<script type="text/javascript" charset="UTF-8" src="${jsPath }/jquery.min.js"></script>
	<script type="text/javascript" charset="UTF-8" src="${jsPath }/bootstrap.min.js"></script>
</body>
</html>