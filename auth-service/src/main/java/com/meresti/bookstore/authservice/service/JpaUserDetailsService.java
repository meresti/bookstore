package com.meresti.bookstore.authservice.service;

import com.meresti.bookstore.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::convertToSpringUser)
                .orElseThrow(() -> new UsernameNotFoundException("No user with name " + username));
    }

    private UserDetails convertToSpringUser(final com.meresti.bookstore.authservice.model.User user) {
        return new User(user.getUsername(),
                user.getPassword(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
    }

}
