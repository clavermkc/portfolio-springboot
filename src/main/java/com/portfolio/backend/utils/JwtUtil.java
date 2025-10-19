package com.portfolio.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * Utility class for handling JSON Web Tokens (JWT) operations.
 * <p>
 * This class provides methods to generate, validate, and extract claims from JWTs.
 * It uses the HMAC SHA-256 algorithm for signing tokens and includes methods to
 * check token validity, extract user information, and retrieve token expiration details.
 * </p>
 */
@Component
public class JwtUtil {

     /**
     * Generates a JWT token with extra claims and user details.
     * <p>
     * This method creates a JWT token that contains the provided extra claims, the user's
     * username as the subject, the issue date (current time), and an expiration time (7 days).
     * The token is signed using the HMAC SHA-256 algorithm and the provided signing key.
     * </p>
     * 
     * @param extraClaims Additional claims to include in the token.
     * @param details The {@link UserDetails} containing the username for the subject of the token.
     * @return The generated JWT token as a String.
     */
     @SuppressWarnings("deprecation")
    private String generateToken(Map<String, Object> extraClaims, UserDetails details){
        return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                    .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
    }

    /**
     * Generates a JWT token using the user details.
     * <p>
     * This method generates a JWT token using the provided {@link UserDetails}. It does not
     * include any additional claims.
     * </p>
     * 
     * @param userDetails The {@link UserDetails} to be used for the subject of the token.
     * @return The generated JWT token as a String.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Validates the JWT token against the provided user details.
     * <p>
     * This method checks if the username extracted from the token matches the username of the
     * provided {@link UserDetails} and ensures that the token is not expired.
     * </p>
     * 
     * @param token The JWT token to validate.
     * @param userDetails The {@link UserDetails} to validate against.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracts all claims from the JWT token.
     * <p>
     * This method parses the JWT token and retrieves all the claims contained in it.
     * </p>
     * 
     * @param token The JWT token to extract claims from.
     * @return The {@link Claims} extracted from the token.
     */
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }

    /**
     * Extracts a specific claim from the JWT token.
     * <p>
     * This method allows extracting a specific claim from the JWT token by applying the provided
     * claims resolver function.
     * </p>
     * 
     * @param token The JWT token to extract the claim from.
     * @param claimsResolvers A function to resolve the claim from the {@link Claims}.
     * @param <T> The type of the claim to extract.
     * @return The extracted claim of type {@code T}.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Extracts the username (subject) from the JWT token.
     * <p>
     * This method extracts the username, which is stored as the subject of the token.
     * </p>
     * 
     * @param token The JWT token to extract the username from.
     * @return The username (subject) extracted from the token.
     */
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     * <p>
     * This method retrieves the expiration date from the token's claims.
     * </p>
     * 
     * @param token The JWT token to extract the expiration date from.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if the JWT token is expired.
     * <p>
     * This method checks whether the token's expiration date is before the current date and time.
     * </p>
     * 
     * @param token The JWT token to check.
     * @return {@code true} if the token is expired, otherwise {@code false}.
     */
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Retrieves the signing key for signing and verifying JWT tokens.
     * <p>
     * This method provides the signing key used to sign the JWT tokens. It decodes the base64-encoded
     * key to produce a {@link Key} object.
     * </p>
     * 
     * @return The {@link Key} used to sign the JWT tokens.
     */
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B62506553685660597033733676397924422645294840406351");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}