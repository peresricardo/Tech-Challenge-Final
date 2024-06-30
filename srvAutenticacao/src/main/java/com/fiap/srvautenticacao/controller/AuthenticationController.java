package com.fiap.srvautenticacao.controller;

import com.fiap.srvautenticacao.entity.User;
import com.fiap.srvautenticacao.repository.UserRepository;
import com.fiap.srvautenticacao.request.UserAuthRequest;
import com.fiap.srvautenticacao.request.UserRequest;
import com.fiap.srvautenticacao.response.UserResponse;
import com.fiap.srvautenticacao.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint para registro de usuários, login e validação de token")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Efetua o login", method = "POST")
    public ResponseEntity login(@RequestBody @Valid UserAuthRequest data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário", method = "POST")
    public ResponseEntity register(@RequestBody @Valid UserRequest data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    @Operation(summary = "Validação de token", method = "GET")
    public String validateToken(@RequestParam("token") String token) {
        if (!tokenService.validateToken(token).isBlank()) {
            return "Token is valid";
        } else {
            return "Token is not valid";
        }
    }
}
