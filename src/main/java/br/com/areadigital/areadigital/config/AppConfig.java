package br.com.areadigital.areadigital.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 
 * This class defines the necessary beans for implementing JWT-based
 * authentication and authorization in a Spring application.
 */
@Configuration
public class AppConfig {

	/**
	 * 
	 * The value of the "jwt.secret" property from an external configuration file.
	 */
	@Value("${jwt.secret}")
	private String jwtSecret;

	/**
	 * 
	 * Returns an instance of the BCryptPasswordEncoder class, which is used for
	 * encoding passwords.
	 * 
	 * @return the BCryptPasswordEncoder instance
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 
	 * Returns an instance of the JwtAccessTokenConverter class, which is used for
	 * converting JWT tokens to access tokens.
	 * The JwtAccessTokenConverter instance is configured with the signing key from
	 * the jwtSecret field.
	 * 
	 * @return the JwtAccessTokenConverter instance
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtSecret);
		return tokenConverter;
	}

	/**
	 * 
	 * Returns an instance of the JwtTokenStore class, which is used for storing and
	 * retrieving JWT tokens.
	 * The JwtTokenStore instance is initialized with the accessTokenConverter()
	 * bean.
	 * 
	 * @return the JwtTokenStore instance
	 */
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
