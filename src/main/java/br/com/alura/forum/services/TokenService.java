package br.com.alura.forum.services;

import br.com.alura.forum.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {


    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API_ALURA")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


}
