package com.store_user_service.userservice.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store_user_service.userservice.service.UserDetailsServiceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

// valida se el token es valido
public class JwtTokenFilter extends OncePerRequestFilter{
    private final static Logger logger =  LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    UserDetailsServiceImp userDetailsService;

    @Autowired
    JwtProvider jwtProvider;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {
        
        try{
            String token = getToken(request);
            // si token no es null y es valido
            if(token != null && jwtProvider.validateToken(token)){
                //obtengo el token y obtengo el username
                String username = jwtProvider.getUsernameFromToken(token);
                //obtengo los detalles del token
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch(Exception e){
            logger.error("fail doFilter " +e.getLocalizedMessage());
            e.printStackTrace();
        }

        // si no tiro ninguna exception
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }

    
    
}
