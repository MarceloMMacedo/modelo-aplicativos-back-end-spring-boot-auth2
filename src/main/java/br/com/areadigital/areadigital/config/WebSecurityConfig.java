package br.com.areadigital.areadigital.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**

 Configuration class for Spring Web Security.

 This class is responsible for defining and configuring the security mechanisms of the application.

 It enables web security and defines a customizer for web security, an authentication manager, and an authentication provider.

 It also provides a bean for the BCrypt password encoder and a UserDetailsService.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**

     The BCrypt password encoder used to encode user passwords.
     */
    private final BCryptPasswordEncoder passwordEncoder;
    /**

     The UserDetailsService used to load user-specific data.
     */
    private final UserDetailsService userDetailsService;
    /**

     Customizes Spring Web Security by ignoring certain endpoints.
     @return a WebSecurityCustomizer that ignores specified endpoints.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/actuator/")
                .antMatchers("/v2/api-docs", "/configuration/ui", "/webjars/")
                ;
    }
    /**

     Configures the AuthenticationManager.
     @param authConfig the AuthenticationConfiguration to use
     @return an AuthenticationManager
     @throws Exception if an error occurs while configuring the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    /**

     Configures the DaoAuthenticationProvider.

     @return a DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

}