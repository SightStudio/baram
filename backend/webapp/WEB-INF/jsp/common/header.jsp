<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header id="gnb">
	<nav class="navbar" role="navigation" aria-label="main navigation">
		<div class="navbar-brand">
			<p class="navbar-item logo-container" > 
				<img class="logo" src="${IMG}/common/logo.png">
			</p> 
			
			<a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false"> 
				<span aria-hidden="true"></span> 
				<span aria-hidden="true"></span> 
				<span aria-hidden="true"></span>
			</a>
		</div>

		<div class="navbar-menu">
			<div class="navbar-start">
				<a class="navbar-item" href="${HOME}/index"> Home </a> 
			</div>
		</div>
	</nav>
</header>