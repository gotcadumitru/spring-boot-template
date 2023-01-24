package com.dima.demo.websocket;

import com.dima.demo.user.User;
import com.dima.demo.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Configuration
public class WebSocketHelper {
    private final SimpUserRegistry simpUserRegistry;
    private final UserService userService;
    public List<User> getConnectedUsers(){
        Set<SimpUser> connectedUsers =  simpUserRegistry.getUsers();
        List<String> usernames= connectedUsers.stream().map(SimpUser::getName).collect(Collectors.toList());
        List<User> connectedToWsUsers = userService.getUsersByEmail(usernames);
        return connectedToWsUsers;
    }
}
