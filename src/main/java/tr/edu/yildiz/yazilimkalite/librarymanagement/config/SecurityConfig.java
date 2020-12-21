package tr.edu.yildiz.yazilimkalite.librarymanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tr.edu.yildiz.yazilimkalite.librarymanagement.service.PancakeUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PancakeUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String ADMIN = "ADMIN";
    private static final String EDITOR = "EDITOR";
    private static final String LIBRARIAN = "LIBRARIAN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/users/**", "/api/users/**").hasAnyAuthority(ADMIN)
                .antMatchers("/library-settings/**").hasAnyAuthority(ADMIN)
                .antMatchers("/members/**", "/api/members/**").hasAnyAuthority(LIBRARIAN)
                .antMatchers("/borrowings/**", "/api/borrowings/**").hasAnyAuthority(LIBRARIAN)

                    .antMatchers(HttpMethod.POST, "/books/**").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/books/add").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/books/edit/**").hasAuthority(EDITOR)
                    .antMatchers("/books/**", "/api/books/**").hasAnyAuthority(LIBRARIAN, EDITOR)
                    
                    .antMatchers(HttpMethod.POST, "/writers/**").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/writers/add").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/writers/edit/**").hasAuthority(EDITOR)
                    .antMatchers("/writers/**", "/api/writers/**").hasAnyAuthority(LIBRARIAN, EDITOR)
                    
                    .antMatchers(HttpMethod.POST, "/publishers/**").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/publishers/add").hasAuthority(EDITOR)
                    .antMatchers(HttpMethod.GET, "/publishers/edit/**").hasAuthority(EDITOR)
                    .antMatchers("/publishers/**", "/api/publishers/**").hasAnyAuthority(LIBRARIAN, EDITOR)
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
            .logout()
                .permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied")
            .and()
            .sessionManagement()
                .maximumSessions(1);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
