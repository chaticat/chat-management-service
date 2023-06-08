package com.chaticat.chatmanagementservice.security;

import com.chaticat.chatmanagementservice.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String accessToken = getAccessTokenFromRequest(request);
            String refreshToken = getRefreshTokenFromRequest(request);

            if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)
                    && tokenProvider.checkExpirationAccessToken(refreshToken)) {
                UUID userId = tokenProvider.getUserIdFromToken(accessToken);

                var userPrincipal = UserPrincipal.create(userId);
                var authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            var apiResponse = new ApiResponse(ex.getMessage());
            objectMapper.writeValue(response.getWriter(), apiResponse);
        }
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (StringUtils.hasText(token)) ? token : null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("RefreshToken");
    }

}
