package ru.rest.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.rest.demo.model.Userok;
import ru.rest.demo.repo.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Userok user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;

    }
}
