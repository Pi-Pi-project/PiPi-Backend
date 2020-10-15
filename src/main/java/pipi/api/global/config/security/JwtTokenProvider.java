package pipi.api.global.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${secret.key}")
    private String secretKey;

    @Value("${secret.exp.access}")
    private Long accessTokenExp;

    @Value("${secret.exp.refresh}")
    private Long refreshTokenExp;

    @Value("${secret.header}")
    private String header;

    @Value("${secret.prefix}")
    private String prefix;

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExp))
                .setSubject(data)
                .claim("type", "accessToken")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(String data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExp))
                .setSubject(data)
                .claim("type", "refreshToken")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }
    
    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = authDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    public boolean isRefresh(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("type").equals("refreshToken");
    }
}
