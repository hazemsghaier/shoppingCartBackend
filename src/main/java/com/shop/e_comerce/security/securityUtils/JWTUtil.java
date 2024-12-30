package com.shop.e_comerce.security.securityUtils;

import com.shop.e_comerce.security.UserDetails.ShopUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@Component
public class JWTUtil {
    @Value("${auth.token.secret}")
    private String secret;
    @Value("${auth.token.expiration}")
    private int expiration;
    public String generateToken(Authentication authentication){
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles= userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(userDetails.getEmail()).claim("id",userDetails.getId())
                .claim("roles",roles).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()+expiration))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public  boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;

        }catch (Exception e){
            return false;
        }

    }
}
