package models;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String username;
	private String password;
	private boolean useradmin;
	private String perfil;
	private String sexo;
	private String photouser;
	private String extensionphotouser;
	
	// Getters
	public Long getId() { return this.id; }
	public String getNome() { return this.nome; }
	public String getEmail() { return this.email; }
	public String getUsername() { return this.username; }
	public String getPassword() { return this.password; }
	public boolean isUserAdmin() { return this.useradmin; }
	public String getPerfil() { return this.perfil; }
	public String getSexo() { return this.sexo; }
	public String getPhotoUser() { return this.photouser; }
	public String getExtensionPhotoUser() { return this.extensionphotouser; }
	
	// Setters
	public void setId(Long id) { this.id = id; }
	public void setNome(String nome) { this.nome = nome; }
	public void setEmail(String email) { this.email = email; }
	public void setUsername(String username) { this.username = username; }
	public void setPassword(String password) { this.password = password; }
	public void setUserAdmin(boolean useradmin) { this.useradmin = useradmin; }
	public void setPerfil(String perfil) { this.perfil = perfil; }
	public void setSexo(String sexo) { this.sexo = sexo; }
	public void setPhotoUser(String photouser) { this.photouser = photouser; }
	public void setExtensionPhotoUser(String extension) { this.extensionphotouser = extension; }
	
	// New methods
	public boolean isNewUser() {
		if (this.id == null) {
			return true; // Action: insert new user
		}
		else if (this.id != null && this.id > 0) {
			return false; // Action: update an existent user
		}
		return id == null;
	}
}
