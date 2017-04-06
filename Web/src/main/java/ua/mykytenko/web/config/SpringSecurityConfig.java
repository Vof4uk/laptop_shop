package ua.mykytenko.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Autowired
    public SpringSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // For Account in database.
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // The pages requires login as ADMIN or Client(USER).
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/orderList","/order", "/**/shopping_cart/confirm")//
                .access("hasRole('ROLE_USER')");

        http.authorizeRequests().antMatchers("/accountInfo", "/logout")//
                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        //TODO

        // For ADMIN only.
        http.authorizeRequests().antMatchers("/stock", "/admin/**/*").access("hasRole('ROLE_ADMIN')");
        //TODO


        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will throw.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Config for Login Form
        http.
                authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/accountInfo")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                // (Go to home page).
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/good_bye");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
