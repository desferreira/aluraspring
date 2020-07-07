package br.com.alura.forum.models.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class FormLogin {

    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken convert(){
        return new UsernamePasswordAuthenticationToken(this.email, this.senha);
    }
}
