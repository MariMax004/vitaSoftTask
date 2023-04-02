package com.example.vitasofttask.filters;

import com.example.vitasofttask.application.user.domain.Customer;
import com.example.vitasofttask.application.user.domain.CustomerRepository;
import com.example.vitasofttask.utils.JwtUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION = "Authorization";
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;


    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtils.validateToken(token)) {
            System.out.println(jwtUtils.getWordForToken(token));

            Customer user = customerRepository.getUserById(Long.parseLong(jwtUtils.getWordForToken(token).getLogin()));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
