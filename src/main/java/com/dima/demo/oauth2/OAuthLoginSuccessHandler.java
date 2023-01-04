package com.dima.demo.oauth2;

import com.dima.demo.authentication.Provider;
import com.dima.demo.security.config.JwtUtils;
import com.dima.demo.user.User;
import com.dima.demo.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService  ;
    private final JwtUtils jwtUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        Provider oauth2ClientName = oauth2User.getOauth2ClientName();
        User user = userService.processOAuthPostLogin(oauth2User,oauth2ClientName);
        if (user != null) {
//                    response.sendRedirect("https://localhost:3000/?token=" + jwtUtils.generateToken(user));
            response.sendRedirect("https://localhost:3000/auth/login/?token=" + jwtUtils.generateToken(user));
        }
    }
}
