package br.com.areadigital.areadigital.config.components;


import br.com.areadigital.areadigital.models.*;
import br.com.areadigital.areadigital.restrepository.PersonRestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This class implements the TokenEnhancer interface to add custom claims to the
 * OAuth2 access token.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenEnhancer implements TokenEnhancer {

	/**
	 * 
	 * The repository for accessing user information.
	 */
	private final PersonRestRepository userRepository;

	/**
	 * 
	 * Adds custom claims to the OAuth2 access token, including the user's first
	 * name and ID.
	 * 
	 * @param accessToken    the access token to be enhanced
	 * 
	 * @param authentication the authentication associated with the access token
	 * 
	 * @return the enhanced access token
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		// Find the user associated with the access token
		Person user = userRepository.findByEmail(authentication.getName());

		// Add the user's first name and ID to the access token's additional information
		Map<String, Object> map = new HashMap<>();
		map.put("userFirstName", user.getFirstname());
		map.put("userId", user.getId());

		// Set the additional information on the access token
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);

		// Return the enhanced access token
		return accessToken;
	}
}