<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Restaurant Profile</title>
<!-- Bootstrap Core CSS -->
<link
	href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
	rel='stylesheet' type='text/css'>

<!-- Plugin CSS -->
<link
	href="${contextPath}/resources/vendor/magnific-popup/magnific-popup.css"
	rel="stylesheet">

<!-- Theme CSS -->
<link href="${contextPath}/resources/css/creative.min.css"
	rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body id="page-top">

	<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> Menu <i
						class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand page-scroll" href="#page-top">Prandium</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="${contextPath}/logout.htm">LogOut</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Login</h4>
				</div>
				<div class="modal-body">
					<div class="row omb_row-sm-offset-3">
						<div class="col-xs-12 col-sm-6">
							<form class="omb_loginForm" method="post"
								action="/controller/user/login.htm" autocomplete="off">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user"></i></span>
									<input type="text" class="form-control" name="username"
										placeholder="username">
								</div>
								<span class="help-block"></span>

								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-lock"></i></span>
									<input type="password" class="form-control" name="password"
										placeholder="Password">
								</div>


								<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
							</form>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>




	<header>
		<div class="header-content">
			<div class="header-content-inner">
				<h1 id="homeHeading">Restaurant Profile Page</h1>
				<hr>
				<img height="300" width="300" src="${restaurant.filename}" />
				<h2>${restaurant.restaurantName}</h2>
				
					<h3>${restaurant.restaurantLocation}</h3>
					
					<br />
					
				
			</div>
		</div>
	</header>

<section class="bg-primary" id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 text-center">
					<h2 class="section-heading">Reviews</h2>
					<hr class="light">
					<form class="navbar-form"
						action="${contextPath}/review/review.htm?username=${user.firstName}"
						method="post">
						
						<table class="table">
							<thead>
								<tr>
									<td><h3>
											<b>Review User Name</b>
										</h3></td>
									<td><h3>
											<b>Review Message</b>
										</h3></td>
									<td><h3>
											<b>Review Date</b>
										</h3></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${listOfReviews}" var="review">
									<tr>
										<td>${review.username}</td>
										<td>${review.reviewMessage}</td>
										<td>${review.reviewDate}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>


						<br /> Review: <input type="text" name="reviewMessage" size="30" class="form-control"/>

						<input type="submit" value="Add Review"
							class="btn btn-primary btn-xl page-scroll" />


					</form>
				</div>
			</div>
		</div>
	</section>

	<!-- jQuery -->
	<script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Plugin JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script
		src="${contextPath}/resources/vendor/scrollreveal/scrollreveal.min.js"></script>
	<script
		src="${contextPath}/resources/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

	<!-- Theme JavaScript -->
	<script src="${contextPath}/resources/js/creative.min.js"></script>

</body>
</html>