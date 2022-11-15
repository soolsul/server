package com.soolsul.soolsulserver.config;


import com.soolsul.soolsulserver.auth.Role;
import com.soolsul.soolsulserver.auth.common.JwtAuthenticationEntryPoint;
import com.soolsul.soolsulserver.auth.filter.FirstLoginAuthenticationFilter;
import com.soolsul.soolsulserver.auth.filter.JwtAuthenticationFilter;
import com.soolsul.soolsulserver.auth.handler.FirstLoginAuthenticationFailureHandler;
import com.soolsul.soolsulserver.auth.handler.FirstLoginAuthenticationSuccessHandler;
import com.soolsul.soolsulserver.auth.handler.JwtDeniedHandler;
import com.soolsul.soolsulserver.auth.provider.FirstLoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URI = {
            "/api/auth/register"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_URI).permitAll()
                .antMatchers("/api/**").permitAll()//.hasRole(Role.USER.name())
                .anyRequest()
                .authenticated()
                .and()
                .addFilterAt(firstLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FirstLoginAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtDeniedHandler());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public FirstLoginAuthenticationFilter firstLoginAuthenticationFilter() throws Exception {
        FirstLoginAuthenticationFilter firstLoginAuthenticationFilter = new FirstLoginAuthenticationFilter();
        firstLoginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        firstLoginAuthenticationFilter.setAuthenticationSuccessHandler(firstLoginAuthenticationSuccessHandler());
        firstLoginAuthenticationFilter.setAuthenticationFailureHandler(firstLoginAuthenticationFailureHandler());
        return firstLoginAuthenticationFilter;
    }

    @Bean
    public FirstLoginAuthenticationSuccessHandler firstLoginAuthenticationSuccessHandler() {
        return new FirstLoginAuthenticationSuccessHandler();
    }

    @Bean
    public FirstLoginAuthenticationFailureHandler firstLoginAuthenticationFailureHandler() {
        return new FirstLoginAuthenticationFailureHandler();
    }

    @Bean
    public JwtDeniedHandler jwtDeniedHandler() {
        return new JwtDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider firstLoginAuthenticationProvider() {
        return new FirstLoginAuthenticationProvider();
    }
}
