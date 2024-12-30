package com.shop.e_comerce.security.Filters;

import ch.qos.logback.core.util.StringUtil;
import com.shop.e_comerce.security.UserDetails.ShopUserDetailsService;
import com.shop.e_comerce.security.securityUtils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private ShopUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal( @NotNull HttpServletRequest request,@NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
         String jwt=ParseJwt(request);
         System.out.println("hhhhhhhhhhhhhhhhhh");
         try {
             if(StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)){
                 String UserName=jwtUtil.getUserNameFromToken(jwt);
                 UserDetails userDetails=userDetailsService.loadUserByUsername(UserName);
                 Authentication auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                 SecurityContextHolder.getContext().setAuthentication(auth);
             }
         }catch (Exception e){
             System.out.println("unauthorized55555555555555555555555");
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

             response.getWriter().println("Internal Server Error");
         }
         filterChain.doFilter(request,response);

    }
    private String ParseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(headerAuth != null && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;

    }
}
