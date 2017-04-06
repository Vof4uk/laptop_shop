package ua.mykytenko.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.mykytenko.entities.Account;
import ua.mykytenko.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
    public class AuthentificationServiceImpl implements UserDetailsService {

        @Autowired
        private AccountRepository repository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Account account = repository.findByName(username);
            if (account == null) {
                throw new UsernameNotFoundException("Account " //
                        + username + " was not found in the database");
            }

            String role = account.getRole().name();

            List<GrantedAuthority> grantList = new ArrayList<>();

            // ROLE_EMPLOYEE, ROLE_MANAGER
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            grantList.add(authority);

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            UserDetails userDetails = (UserDetails) new  User(account.getName(), //
                    account.getPassword(), enabled, accountNonExpired, //
                    credentialsNonExpired, accountNonLocked, grantList);

            return userDetails;
        }

    }
