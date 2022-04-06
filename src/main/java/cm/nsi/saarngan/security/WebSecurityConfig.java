package cm.nsi.saarngan.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * created by : Ravanely
 * create at : 06/04/2022, 10:17, mer.
 * saar-ngan
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new SaarNganUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider();
        autProvider.setUserDetailsService(userDetailsService());
        autProvider.setPasswordEncoder(passwordEncoder());

        return autProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
                .antMatchers("/categories/**", "/brands/**", "/articles/**", "/menus/**").hasAnyAuthority("Admin", "Editor")
                .antMatchers("/questions/**", "/reviews/**").hasAnyAuthority("Admin", "Assistant")
                .antMatchers("/customers/**", "/shipping/**", "/reports/**").hasAnyAuthority("Admin", "Salesperson")
                .antMatchers("/products/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .antMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .rememberMe()
                .key("AbcDefgHijKlmnOpqrsTU_1234567890")
                .tokenValiditySeconds(7 * 24 * 60 * 60);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}

