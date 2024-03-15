package br.com.areadigital.areadigital.config;

import br.com.areadigital.areadigital.config.components.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Configuração do servidor de autorização OAuth2 que lida com as requisições de autenticação e autorização de recursos.

 * A anotação @Configuration indica que a classe fornece configurações para o container Spring.

 * A anotação @EnableAuthorizationServer habilita o servidor de autorização OAuth2.

 * A classe ResourceServerConfigurerAdapter estende AuthorizationServerConfigurerAdapter e fornece a implementação de seus métodos.

 * O servidor de autorização OAuth2 é configurado para:

 * Permitir o acesso à chave pública do token para todos os usuários
 * Verificar se um token de acesso é válido para um usuário autenticado
 * Configurar as informações do cliente (clientId, clientSecret, authorizedGrantTypes, accessTokenValiditySeconds)
 * Configurar o gerenciador de autenticação, o armazenamento de token e o conversor de token
 * Adicionar um TokenEnhancerChain, que adiciona informações personalizadas ao token de acesso
 */
@Configuration
@EnableAuthorizationServer
public class ResourceServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${jwt.duration}")
    private Integer jwtDuration;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private JwtTokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenEnhancer tokenEnhancer;

    /**
     * Configura as regras de segurança para o servidor de autorização OAuth2.
     * Permite que qualquer cliente acesse a chave pública do token e verifica se um usuário autenticado possui um token válido.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * Configura as informações do cliente que acessa o servidor de autorização OAuth2.
     * As informações incluem o clientId, clientSecret, authorizedGrantTypes e accessTokenValiditySeconds.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes("read", "write")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(jwtDuration);
    }

    /**
     * Configura as características do servidor de autorização OAuth2.

     * Define o gerenciador de autenticação, o armazenamento de token e o conversor de token.

     * Adiciona um TokenEnhancerChain, que adiciona informações personalizadas ao token de acesso.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(chain);
    }
}