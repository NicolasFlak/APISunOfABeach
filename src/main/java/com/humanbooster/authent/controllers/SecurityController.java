package com.humanbooster.authent.controllers;

import com.humanbooster.authent.config.JwtTokenUtils;
import com.humanbooster.authent.models.request.AuthRequest;
import com.humanbooster.authent.models.responses.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @PostMapping("/auth")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {

        //Authentifier l'utilisateur
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        //Générer un token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());

        //Retourner le token
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return new AuthResponse(token);
    }


    public void authenticate(String username, String password) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Les identifiants sont incorrrects");
        }

    }
}

