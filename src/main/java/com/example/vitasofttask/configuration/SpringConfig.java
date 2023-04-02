package com.example.vitasofttask.configuration;

import com.example.vitasofttask.errors.ErrorDescriptor;
import com.example.vitasofttask.filters.JwtFilter;
import com.example.vitasofttask.utils.HttpResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))
                .logoutSuccessUrl("/api/v1/logout.done").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/registration")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/logout")
                .permitAll()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().sameOrigin();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Конфигарация CORS.
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.setAlwaysUseFullPath(true);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * {@see AccessDeniedHandler}.
     */
    private AccessDeniedHandler accessDeniedHandler() {
        ErrorDescriptor errorDescription = ErrorDescriptor.ACCESS_DENIED;
        return (request, response, ex) -> HttpResponseUtils.writeError(response, errorDescription.applicationError(),
                HttpServletResponse.SC_FORBIDDEN);
    }

    /**
     * {@see AuthenticationEntryPoint}.
     */
    private AuthenticationEntryPoint authenticationEntryPoint() {
        ErrorDescriptor errorDescription = ErrorDescriptor.UNAUTHORIZED_ACCESS;
        return (request, response, ex) -> HttpResponseUtils.writeError(response, errorDescription.applicationError(),
                HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**"
    };


}

