package is442.portfolioAnalyzer.config;

import is442.portfolioAnalyzer.Token.TokenDAO;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Get the JwtAuthFilter method from the pkg

    @Autowired
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenDAO tokenDao;
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Get the Bearer token from the request header
        final String jwt;
        final String userEmail;

        // Return if not authorised
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // The index starts from 7 for the bearer token
        userEmail = jwtService.extractUsername(jwt); // Extract the userEmail from the jwt token

        // User not authenticated yet
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Check if user is in the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Check if user's token has not be revoked or expired
            var isTokenValid = tokenDao.findByToken(jwt)
                    .map(token -> !token.isExpired() && !token.isRevoked())
                    .orElse(false); // return false if no usable token

            // Check for valid JWT
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // Update security context for user with valid JWT
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
