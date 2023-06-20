package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    //"resourcer/application.properties"
    @Value("${api.security.token.secret}")
    private String secret;

    //Create a JWT
    public String gerarToken(Usuario usuario){
        //System.out.println(secret);
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    //.withClaim("id", usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error generating JWT TOKEN.", exception);
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    //Verify a JWT
    public String getSubject (String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT Token! " , exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
