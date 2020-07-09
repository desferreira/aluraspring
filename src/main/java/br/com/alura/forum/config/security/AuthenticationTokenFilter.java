package br.com.alura.forum.config.security;

import br.com.alura.forum.models.Usuario;
import br.com.alura.forum.repositories.UsuarioRepository;
import br.com.alura.forum.services.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter  extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = this.getToken(httpServletRequest);
        if (tokenService.isTokenValid(token)){
            this.authenticateClient(token);
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void authenticateClient(String token){
        Long id = tokenService.getId(token);
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isPresent()){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }


    private String getToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7, token.length());
    }

}
