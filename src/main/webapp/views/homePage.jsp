<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
<meta charset="UTF-8" />
<title>JSP Page</title>
<!-- Bootstrap CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous" />
<!-- CSS custom -->
<link rel="stylesheet" href="../bootstrap/css/custom-dark.css" />

<!--  Bootstrap JS CDN -->
<script defer
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
	integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<link href="../styles/NavBar.css" rel="stylesheet" />
<style>
.carousel-caption {
	display: flex !important;
	left: 0;
	text-align: left;
	background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0),
		rgba(0, 0, 0, 0.8));
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding: 0 5em !important;
	height: 60% !important;
	bottom: 0;
}
</style>
</head>
<body>
	<div class="position-relative">
		<%@ include file="./components/navbar.jsp"%>
		<div class="container-fluid p-0">
			<header>
				<!-- data-bs-ride="carousel" -->
				<div id="carousel" class="carousel slide">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#carousel"
							data-bs-slide-to="0" class="active" aria-current="true"
							aria-label="Slide 1"></button>
						<button type="button" data-bs-target="#carousel"
							data-bs-slide-to="1" aria-label="Slide 2"></button>
						<button type="button" data-bs-target="#carousel"
							data-bs-slide-to="2" aria-label="Slide 3"></button>
					</div>
					<div class="carousel-inner" style="height: 90vh">
						<div class="carousel-item active h-100 w-100">
							<img src="../images/baner1.png"
								class="d-block w-100 h-100 object-fit-cover" alt="..." />
							<div class="carousel-caption d-md-block w-100 text-white p-0"
								style="height: 100%">
								<h1 style="font-size: 80px; margin-bottom: 0.5em">
									Interstella</h1>
								<h5>Some representative placeholder content for the first
									slide.</h5>
							</div>
						</div>
						<div class="carousel-item h-100 w-100">
							<img src="../images/baner2.jpg"
								class="d-block w-100 h-100 object-fit-cover" alt="..." />
							<div class="carousel-caption d-md-block w-100 text-white p-0"
								style="height: 100%">
								<h1 style="font-size: 80px; margin-bottom: 0.5em">Breaking
									Bad</h1>
								<h5>Some representative placeholder content for the first
									slide.</h5>
							</div>
						</div>
						<div class="carousel-item h-100 w-100">
							<img src="../images/baner3.jpg"
								class="d-block w-100 h-100 object-fit-cover" alt="..." />
							<div class="carousel-caption d-md-block w-100 text-white p-0"
								style="height: 100%">
								<h1 style="font-size: 80px; margin-bottom: 0.5em">Chainsaw
									man</h1>
								<h5>Some representative placeholder content for the first
									slide.</h5>
							</div>
						</div>
					</div>
					<button class="carousel-control-prev" type="button"
						data-bs-target="#carousel" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Previous</span>
					</button>
					<button class="carousel-control-next" type="button"
						data-bs-target="#carousel" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Next</span>
					</button>
				</div>
			</header>
			<section id="skit">
				<div class="bg-primary" style="height: 100vh"></div>
			</section>
			<section id="info">
				<div class="bg-warning" style="height: 100vh"></div>
			</section>
			<section id="contact">
				<div class="bg-success" style="height: 100vh"></div>
			</section>
			<section></section>
		</div>
	</div>
</body>
</html>
