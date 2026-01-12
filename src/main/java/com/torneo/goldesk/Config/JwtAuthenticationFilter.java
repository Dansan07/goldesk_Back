package com.torneo.goldesk.Config;

import com.torneo.goldesk.Exception.AuthException;
import com.torneo.goldesk.Service.Authenticator.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(JwtService jwtService, HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.resolver = resolver;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // Agregamos las rutas que NO deben pasar por la validación de JWT
        return path.startsWith("/api/auth/") || path.startsWith("/api/organizadores/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extraerClaims(token);
            String cedula = claims.getSubject();
            String nombreRol = claims.get("rol",String.class);

            // Convertir el ID de rol a una autoridad de Spring Security (ej. "ROLE_ADMIN")
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+ nombreRol));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            cedula,
                            null,
                            authorities
                    );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);

        }catch (Exception e){
            resolver.resolveException(request,response,null,new AuthException("Token inválido o expirado"));
        }
    }
}
