<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header id="layout-header">
	<div class="clearfix container">
		<a href="/" id="logo"> </a>
		<div id="header-content">
			<nav id="main-nav">

				<ul class="clearfix">
					<li>
						<a href="${s:mvcUrl('CC#lista').build()}" rel="nofollow">
							<fmt:message key="menu.carrinho" > 
								<fmt:param value="${carrinho.quantidade }"/>
							</fmt:message>
						</a>
					</li>

					<security:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="${s:mvcUrl('PC#lista').build()}" rel="nofollow"><fmt:message key="menu.produto.lista" /></a></li>

						<li><a href="${s:mvcUrl('PC#form').build()}" rel="nofollow"><fmt:message key="menu.produto.cadastra" /></a></li>
					</security:authorize>
					
					<security:authorize access="!isAuthenticated()">
						<li><a href="<c:url value="/login"/>" rel="nofollow"><fmt:message key="menu.login" /></a></li>
					</security:authorize>
					<security:authorize access="isAuthenticated()">
						<li><a href="<c:url value="/logout"/>" rel="nofollow"><fmt:message key="menu.logout" /></a></li>
					</security:authorize>
				</ul>
			</nav>
		</div>
	</div>
</header>
<nav class="categories-nav">
	<ul class="container">
		<li class="category"><a href="${contextPath }"><fmt:message key="navegacao.categoria.home" /> </a></li>
		<li class="category"><a href="/collections/livros-de-agile"> <fmt:message key="navegacao.categoria.agile" /></a></li>
		<li class="category"><a href="/collections/livros-de-front-end"> <fmt:message key="navegacao.categoria.front_end" /></a></li>
		<li class="category"><a href="/collections/livros-de-games"> <fmt:message key="navegacao.categoria.games" /></a></li>
		<li class="category"><a href="/collections/livros-de-java"> <fmt:message key="navegacao.categoria.java" /></a></li>
		<li class="category"><a href="/collections/livros-de-mobile"> <fmt:message key="navegacao.categoria.mobile" /></a></li>
		<li class="category"><a href="/collections/livros-desenvolvimento-web"><fmt:message key="navegacao.categoria.web" /></a></li>
		<li class="category"><a href="/collections/outros"> <fmt:message key="navegacao.categoria.outros" /></a></li>
		<li class="category"><a href="?locale=pt_BR"> <fmt:message key="navegacao.pt" /></a></li>
		<li class="category"><a href="?locale=en_US"> <fmt:message key="navegacao.en" /></a></li>
		
	</ul>
</nav>