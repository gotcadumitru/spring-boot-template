package com.dima.demo.security.config;

import com.dima.demo.user.User;
import com.dima.demo.user.UserRole;
import com.dima.demo.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.Collections;

@Component
@AllArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            String jwtToken = jwtUtils.getFromHeader(authHeader);

            User user = userService.getUserByJwtToken(jwtToken);

            accessor.setUser(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    null,
                    Collections.singleton((GrantedAuthority) () -> user.getRole().name()) // MUST provide at least one role
            ));
        }
        return message;
    }
}