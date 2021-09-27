<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Basic Concepts - JavaServer Pages</title>
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<style>
		.formatar { margin: 2%; padding: 20px; border: 1px solid blue; border-radius: 10px; }
		.cab { margin-top: 5%; margin-bottom: 3%; color: blue; text-align: center; }
		.aviso { font-family: 'Segoe ui'; font-weight: 400; font-size: .875em; color: #dc3545; }
	</style>
</head>
<body>
	
	<div class="row gy-3">
		<div class="offset-md-3 col-md-6">
			<h1 class="cab">Course JSP - JavaServer Pages</h1>
		</div>
	</div>
	<div class="container">
		<form method="POST" action="ServletLogin" class="row g-3 formatar needs-validation" novalidate="novalidate">
			<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
			<div class="row gy-2">
				<div class="offset-md-4 col-md-4">
					User:
					<div class="input-group has-validation">
						<input type="text" name="username" class="form-control" required="required">
						<div class="invalid-feedback">
							Please, insert the name of the user!
						</div>
					</div>
				</div>
			</div>
			<div class="row gy-2">
				<div class="offset-md-4 col-md-4">
					Password:
					<div class="input-group has-validation">
						<input type="password" name="pass" class="form-control" required="required">
						<div class="invalid-feedback">
							Please, insert the password of the user.
						</div>
					</div>
				</div>
			</div>
			<div class="row gy-2">
				<div class="offset-md-4 col-md-4">
					<input type="submit" value="Aceder ao sistema" class="btn btn-primary w-100">
				</div>
			</div>
			<div class="row gy-1">
				<div class="offset-md-4 col-md-4">
					<h5 class="aviso">${msg}</h5>
				</div>
			</div>
		</form>
		
	</div>
	
	<!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

	<script type="text/javascript">
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function () {
	  		'use strict'

	  		// Fetch all the forms we want to apply custom Bootstrap validation styles to
	  		var forms = document.querySelectorAll('.needs-validation')

	  		// Loop over them and prevent submission
	  		Array.prototype.slice.call(forms)
	    	.forEach(function (form) {
	      		form.addEventListener('submit', function (event) {
	        		if (!form.checkValidity()) {
	          			event.preventDefault()
	          			event.stopPropagation()
	        		}

	        		form.classList.add('was-validated')
	      		}, false)
	    	})
		})()
	</script>
</body>
</html>