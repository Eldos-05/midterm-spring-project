package comsep23.midtermspringproject.service;

import comsep23.midtermspringproject.config.MyUserDetails;
import comsep23.midtermspringproject.entity.User;
import comsep23.midtermspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = repository.findByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return new MyUserDetails(users.get(0));
    }
}