package br.com.areadigital.areadigital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 
 * This class configures the resource server to protect endpoints based on their
 * required roles.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/**
	 * 
	 * The environment configuration for the server.
	 */
	@Autowired
	private Environment env;
	/**
	 * 
	 * The token store used to validate access tokens.
	 */
	@Autowired
	private JwtTokenStore tokenStore;
	/**
	 * 
	 * An array of public endpoints that do not require authentication.
	 */
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };
	/**
	 * 
	 * An array of endpoints that require either the OPERATOR or ADMIN role.
	 */
	private static final String[] OPERATOR_OR_ADMIN = { "/products/", "/categories/" };
	/**
	 * 
	 * An array of endpoints that require the ADMIN role.
	 */
	private static final String[] ADMIN = { "/users/**" };

	/**
	 * 
	 * Configures the token store for the resource server.
	 * 
	 * @param resources the ResourceServerSecurityConfigurer object to configure
	 */
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenStore(tokenStore);
	}

	/**
	 * 
	 * Configures the HTTP security for the resource server, setting access rules
	 * based on the required roles.
	 * 
	 * @param http the HttpSecurity object to configure
	 */
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers(PUBLIC).permitAll()
				.antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()
				.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
				.antMatchers(ADMIN).hasRole("ADMIN")
				.anyRequest().authenticated();

	}


}