<%@ page import="models.ModelLogin" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--  Library to use the JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    
<!DOCTYPE html>
<html lang="en">

  <jsp:include page="head.jsp"></jsp:include>

  <body>
  <!-- Pre-loader start -->
  <jsp:include page="pre-loader-start.jsp"></jsp:include>
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar-header.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="navbar-main-menu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      <jsp:include page="page-header.jsp"></jsp:include>
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">
                                        <div class="row">
                                        	<div class="col-md-12">
                                                <div class="card">
                                                    <div class="card-header">
                                                        <h4 style="font-family: 'Segoe ui'; color: #4488FA;">Cadastro do utilizador</h4>
                                                    </div>
                                                    <div class="card-block">
                                                        <form class="form-material" enctype="multipart/form-data" id="formUser" method="POST" action="<%= request.getContextPath() %>/ServletUserController">
                                                        	
                                                        	<input type="hidden" name="acao" id="acao" value="">
                                                        	
                                                        	<div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${dados.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID</label>
                                                            </div>
                                                        
                                                        	<div class="form-group form-default input-group mb-3">
                                                            	<div class="input-group-prepend">             
                                                            		<c:if test="${dados.photouser != null && dados.photouser != ''}">
                                                            			<img id="photoinbasis64" src="${dados.photouser}" alt="photo" width="70px">
                                                            		</c:if>
                                                            		<c:if test="${dados.photouser == '' || dados.photouser == null}">
                                                            			<img id="photoinbasis64" src="assets/images/photouser.png" alt="photo" width="70px">
                                                            		</c:if>
                                                            	</div>
																<input type="file" class="form-control form-control-sm" id="filephoto" name="filephoto" onchange="viewImg('photoinbasis64','filephoto');" accept="image/*" style="margin-top: 20px; margin-left: 10px;">
                                                            </div>
                                                        
                                                            <div class="form-group form-default">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required"  value="${dados.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${dados.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
	                                                            <select class="form-control" aria-label="perfil" name="perfil" id="perfil">
																  <option disabled="disabled">Selecione o perfil ...</option>
																  <option value="Administrador" <% 
																  									ModelLogin mdlLgn = (ModelLogin) request.getAttribute("dados");

																  									if (mdlLgn != null && mdlLgn.getPerfil().equals("Administrador")) {
																	  									out.print(" ");
																	  									out.print("selected=\"selected\"");
																	  									out.print(" ");
																	  								} %>>Administrador</option>
																  <option value="Financeiro" <% 											  
								  																	if (mdlLgn != null && mdlLgn.getPerfil().equals("Financeiro")) {
																	  									out.print(" ");
																	  									out.print("selected=\"selected\"");
																	  									out.print(" ");
																	  								} %>>Financeiro</option>
																  <option value="Comercial" <% 
																		  							if (mdlLgn != null && mdlLgn.getPerfil().equals("Comercial")) {
																	  									out.print(" ");
																	  									out.print("selected=\"selected\"");
																	  									out.print(" ");
																	  								} %>>Comercial</option>
																</select>
																<span class="form-bar"></span>
                                                                <label class="float-label">Perfil</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="text" name="username" id="username" class="form-control" required="required" autocomplete="off" value="${dados.username}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Username</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="password" name="password" id="password" class="form-control" required="required" autocomplete="off" value="${dados.password}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Password</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="radio" name="sexo" checked="checked" value="Masculino" id="masc" style="margin-right: 3px; margin-top: 5px;" <% 
																																						  									if (mdlLgn != null && mdlLgn.getSexo().equals("Masculino")) {
																																							  									out.print(" ");
																																							  									out.print("checked=\"checked\"");
																																							  									out.print(" ");
																																							  								} %>> <label for="masc">Masculino</label>
                                                                <input type="radio" name="sexo" value="Feminino" id="fem" style="margin: 5px 3px 0px 20px;" <% 
																															  									if (mdlLgn != null && mdlLgn.getSexo().equals("Feminino")) {
																																  									out.print(" ");
																																  									out.print("checked=\"checked\"");
																																  									out.print(" ");
																																  								} %>> <label for="fem">Feminino</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                            	<button type="button" class="btn btn-primary waves-effect waves-light" onclick="clearForm();">Novo utilizador</button> 
                                                            	<button class="btn btn-primary waves-effect waves-light">Guardar utilizador</button> 
                                                            	<button type="button" class="btn btn-primary waves-effect waves-light" onclick="deleteUserAjax();">Eliminar utilizador</button>
                                                            	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#searchWindow">Pesquisar por utilizador</button>
                                                            </div>
                                                            
                                                            <div class="form-group form-default">
                                                            	<h5 id="msg" style="font-family: 'Segoe ui'; font-weight: 400; font-size: .875em; color: #dc3545;">${msg}</h5>
                                                            </div>
                                                        </form>
                                                        
                                                        <div style="height: 300px; overflow: auto;">
															<table class="table" id="usersTableView">
															  <thead>
															    <tr>
															      <th scope="col">ID</th>
															      <th scope="col">Nome</th>
															      <th scope="col">E-mail</th>
															      <th scope="col">Visualizar</th>
															    </tr>
															  </thead>
															  <tbody>
															    <c:forEach items='${listarutilizadores}' var='listautlz'>
															    	<tr>
															    		<td><c:out value='${listautlz.id}'></c:out></td>
															    		<td><c:out value='${listautlz.nome}'></c:out></td>
															    		<td><c:out value='${listautlz.email}'></c:out></td>
															    		<td>
															    			<a style="height: 20px; font-size: 12px; padding-top: 0;" 
															    				class="btn btn-link" href="ServletUserController?acao=editarutilizador&id=${listautlz.id}">Ver</a>
															    		</td>
															    	</tr>
															    </c:forEach>
															  </tbody>
															</table>
													  	</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>    
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Warning Section Ends -->
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	<!-- Modal -->
	<div class="modal fade" id="searchWindow" tabindex="-1" role="dialog" aria-labelledby="searchModalWindow" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pesquisar por utilizador</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      
	        <div class="input-group mb-3">
			  	<input type="text" class="form-control" id="nameToSearch" placeholder="Nome do utilizador" aria-label="NameUser" aria-describedby="basic-addon2">
			  	<div class="input-group-append">
			    	<button class="btn btn-primary" type="button" onclick="searchUser();">Pesquisar</button>
			  	</div>
			</div>
			
			<div style="height: 300px; overflow: auto;">
				<table class="table" id="usersFoundTable">
				  <thead>
				    <tr>
				      <th scope="col">ID</th>
				      <th scope="col">Nome</th>
				      <th scope="col">Visualizar</th>
				    </tr>
				  </thead>
				  <tbody>
				    
				  </tbody>
				</table>
		  	</div>		
	      	<span id="total" style="font-size: 11px; color: #009CEA; margin-top:10px; border-top: 1px inset #009CEA; padding-top: 3px; padding-left: 5px; display: inline-block; width: 100%;"></span>
	      	
	      </div>
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">Fechar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script type="text/javascript">
		function clearForm() {
			// Return an array with the elements of the form
			var elementos = document.getElementById("formUser").elements;
			
			for (p = 0; p < elementos.length; ++p) {
				elementos[p].value = '';
			}
		}
		
		function deleteUser() {
			if (confirm("Tem a certeza que deseja eliminar o utilizador?")) {
				document.getElementById("formUser").method = 'GET';
				document.getElementById("acao").value = 'eliminar';
				document.getElementById("formUser").submit();
			}
		}
		
		function deleteUserAjax() {
			if (confirm("Tem a certeza que deseja eliminar o utilizador?")) {
				var urlAction = document.getElementById("formUser").action;
				var idUser = document.getElementById("id").value;
				
				// Use Jquery to work with Ajax
				$.ajax({
					
					method: "GET", // Tipo de envio
					url: urlAction, // Url da Servlet que vai executar
					data: "id=" + idUser + '&acao=eliminarajax', // Parâmetros a serem passados pelo método get
					success: function(response) {
						clearForm();
						document.getElementById('msg').textContent = response;
					}
					
				}).fail(function(xhr, status, errorThrown){ 
							// xhr- erro, status- estado do erro, errorThrown- tipo de exceção)
							alert("Erro ao eliminar o utilizador com ID: " + xhr.responseText);
						});
			}
		}
		
		function searchUser() {
			var nameToSearch = document.getElementById('nameToSearch').value;
			
			if (nameToSearch != null && nameToSearch != '' && nameToSearch.trim() != '') {
				var urlAction = document.getElementById("formUser").action;
				
				// Use Jquery to work with Ajax
				$.ajax({
					
					method: "GET", // Tipo de envio
					url: urlAction, // Url da Servlet que vai executar
					data: "nameToSearch=" + nameToSearch + '&acao=pesquisarutilizadorajax', // Parâmetros a serem passados pelo método get
					success: function(response) {
						var responseInJsonFormat = JSON.parse(response);
						
						// console.info(responseInJsonFormat) CTRL*SHIFT+J to see the results
						
						/******************** Use of Jquery ****************/
						
						// Remove all lines of the table from a previous search
						$('#usersFoundTable > tbody > tr').remove();
						
						for (var pos = 0; pos < responseInJsonFormat.length; pos++)  {
							$('#usersFoundTable > tbody').append(
									'<tr> <td>' + responseInJsonFormat[pos].id + '</td><td>' 
									+ responseInJsonFormat[pos].nome + '</td><td><button style="height: 20px; font-size: 12px; padding-top: 0;" '
									+ ' class="btn btn-link" type="button" onclick="editUser('
									+ responseInJsonFormat[pos].id + ')">Ver</button></td></tr>');
							
						}
						
						document.getElementById('total').textContent = "Total de registos: " + responseInJsonFormat.length;
						
					}
					
				}).fail(function(xhr, status, errorThrown){ 
							// xhr- erro, status- estado do erro, errorThrown- tipo de exceção)
							alert("Erro ao pesquisar o utilizador com o nome: " + xhr.responseText);
						});
			}
		}
		
		function editUser(id) {
			var urlAction = document.getElementById("formUser").action;
			
			// Redirect through the javascript to the actual Servlet with the parameters of the button that was clicked
			window.location.href = urlAction + "?id=" + id + "&acao=editarutilizador";
		}
		
		function viewImg(photoframe,photofile) {
			var preview = document.getElementById(photoframe); // Image frame
			var fileuser = document.getElementById(photofile).files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				preview.src = reader.result; // Load the image to the page
			}
			
			if (fileuser) {
				reader.readAsDataURL(fileuser); // Image preview
			}
			else {
				preview.src = '';
			}
		}
	</script>

</body>

</html>
