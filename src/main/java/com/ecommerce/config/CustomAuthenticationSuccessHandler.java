package  com.ecommerce.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = request.getContextPath();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE")) {
                redirectURL = "";
                break;
            } else if (authority.getAuthority().equals("")) {
                redirectURL = "";
                break;
            } else if (authority.getAuthority().equals("ROLE_VERIFIER")) {
                redirectURL = "";
                break;
            } else if (authority.getAuthority().equals("ROLE_APPROVER")) {
                redirectURL = "";
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
