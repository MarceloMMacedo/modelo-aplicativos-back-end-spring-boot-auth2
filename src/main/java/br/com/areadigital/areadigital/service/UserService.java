package br.com.areadigital.areadigital.service;

import br.com.areadigital.areadigital.restrepository.PersonRestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

	private final PersonRestRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return repo.findByEmail(username);
	}

}
