package br.com.alura.forum.controllers;

import br.com.alura.forum.models.dto.TokenDto;
import br.com.alura.forum.models.form.FormLogin;
import br.com.alura.forum.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private Logger logger = Logger.getLogger("authenticationController");

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid FormLogin formLogin){
        UsernamePasswordAuthenticationToken dadosLogin = formLogin.convert();
        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.generateToken(authentication);
            logger.log(Level.INFO, String.format("Token gerado: [%s]",token));
            return ResponseEntity.ok().body(new TokenDto(token, "Bearer"));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
