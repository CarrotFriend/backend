package com.carrotfriend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String GRANT_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRATION = 30*60*1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 7*24*60*60*1000;

    @Value("${jwt.secret}")
    private String secret;
    private Key key;
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] decode = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public Claims getClaimFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }
    public Authentication getAuthentication(String token){
        Claims claims = getClaimFromToken(token);

        List<? extends GrantedAuthority> auth = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails user = new User(claims.getSubject(), "", auth);

        return new UsernamePasswordAuthenticationToken(user, "", auth);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public JwtToken createToken(Authentication auth){
        String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date accessTime = new Date(now.getTime()+ACCESS_TOKEN_EXPIRATION);
        Date refreshTime = new Date(now.getTime()+REFRESH_TOKEN_EXPIRATION);

        String accessToken = Jwts.builder()
                .setSubject(auth.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .setIssuedAt(now)
                .setExpiration(accessTime)
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(key)
                .setIssuedAt(now)
                .setExpiration(refreshTime)
                .compact();

        return JwtToken.builder()
                .grantType(GRANT_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiration(refreshTime)
                .build();
    }

    public String resolveToken(HttpServletRequest request){
        logger.info("headers : "+request.getHeader("content-type"));
        String header = request.getHeader(JwtFilter.AUTHORIZATION);
        if(StringUtils.hasText(header) && header.startsWith(GRANT_TYPE)){
            return header.substring(7);
        }
        return null;
    }
}
