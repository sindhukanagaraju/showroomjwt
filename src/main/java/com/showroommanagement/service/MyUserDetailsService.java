package com.showroommanagement.service;

import com.showroommanagement.repository.UserRepository;
import com.showroommanagement.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(Constant.USER_NOT_FOUND));
    }
}
