package com.store_user_service.userservice.security;

import com.store_user_service.userservice.security.jwt.JwtEntryPoint;
import com.store_user_service.userservice.security.jwt.JwtTokenFilter;
import com.store_user_service.userservice.service.UserDetailsServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImp userDetailsService;
    
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll() // para entrar aqui todos pueden entrar
        .anyRequest().authenticated() // para lo demas hay que estar autenticado
        .and() // y
        .exceptionHandling().authenticationEntryPoint(jwtEntryPoint) // envia el entry point
        .and() // y 
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // administrador de sesiones
    
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
