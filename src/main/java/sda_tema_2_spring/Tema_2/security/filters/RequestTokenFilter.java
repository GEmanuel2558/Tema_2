package sda_tema_2_spring.Tema_2.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sda_tema_2_spring.Tema_2.business.service.ICredentialsService;
import sda_tema_2_spring.Tema_2.data.entity.PermissionEntity;
import sda_tema_2_spring.Tema_2.data.entity.UserEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class RequestTokenFilter extends OncePerRequestFilter {

    @Autowired
    private ICredentialsService credentials;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        final String emailHeader = request.getHeader("Email");
        String extractedToken;
        Optional<UserEntity> user = Optional.empty();

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            extractedToken = requestTokenHeader.substring(7);
            user = credentials.findUserByToken(extractedToken);
            if (!user.isPresent() || null == emailHeader || emailHeader.trim().equals("") || !user.get().getEmail().equals(emailHeader)) {
                filterChain.doFilter(request, response);
            }
        } else {
            logger.warn("Token does not begin with Bearer String");
        }

        if (user.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<PermissionEntity> allPermissionsForThisUser = credentials.returnAllPermissionForUser(user.get().getId());
            if (!allPermissionsForThisUser.isEmpty()) {

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(user.get().getUserName())
                        .password(user.get().getPassword())
                        .authorities(allPermissionsForThisUser)
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, allPermissionsForThisUser);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                SecurityContextHolder.clearContext();
                response.sendError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
