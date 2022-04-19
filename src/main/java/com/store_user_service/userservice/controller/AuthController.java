package com.store_user_service.userservice.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.store_user_service.userservice.dto.JwtDto;
import com.store_user_service.userservice.dto.LoginUser;
import com.store_user_service.userservice.dto.NewUser;
import com.store_user_service.userservice.entities.Rol;
import com.store_user_service.userservice.entities.User;
import com.store_user_service.userservice.enums.RolName;
import com.store_user_service.userservice.security.jwt.JwtProvider;
import com.store_user_service.userservice.service.RolService;
import com.store_user_service.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad new user data");
        if(userService.existsByUsername(newUser.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        if(userService.existsByEmail(newUser.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");

        //si no existe el usuario lo creo y si parametros estan correctos

        Set<Rol> rols = new HashSet<>();
        // user por defecto
        rols.add(rolService.getByRolName(RolName.ROLE_USER).get());
        // si es admin

        if(newUser.getRols().contains("admin"))
            rols.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());

        User user = User.builder().firstName(newUser.getFirstName()).lastName(newUser.getLastName())
        .username(newUser.getUsername()).email(newUser.getEmail()).password(passwordEncoder.encode(newUser.getPassword()))
        .birthday(newUser.getBirthday()).rols(rols)
        .build();
        //guardar usuario
        userService.save(user);


        return ResponseEntity.status(HttpStatus.CREATED).body("User saved");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Authentication authentication = null;
        // si el nombre de usario tiene un @ es un correo
        if(loginUser.getUsername().contains("@")){
            // obtengo el nombre de usuario con el correo buscando todo el usuarios
            if (userService.existsByEmail(loginUser.getUsername())) {
                    User user = userService.getByEmail(loginUser.getUsername());
                    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginUser.getPassword()));

            }else{
                return ResponseEntity.badRequest().build();
            }
        }else{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = JwtDto.builder().token(jwt).username(userDetails.getUsername())
                .authorities(userDetails.getAuthorities()).build();

        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
    }

}
