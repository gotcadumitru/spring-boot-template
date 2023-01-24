package com.dima.demo.websocket;

import com.dima.demo.user.User;
import com.dima.demo.user.UserService;
import com.dima.demo.websocket.HelperClasses.WebRTCAnswer;
import com.dima.demo.websocket.HelperClasses.WebRTCEndCall;
import com.dima.demo.websocket.HelperClasses.WebRTCOffer;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class WebSocketController {
    private final WebSocketHelper webSocketHelper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    @MessageMapping("/addUser")
    @SendTo("/topic/public/update-users")
    public List<User> addUser() {
        List<User> users = webSocketHelper.getConnectedUsers();
        return users;
    }


    @MessageMapping("/call-user")
    public void callUser(@Payload @NotNull WebRTCOffer webRTCOffer, Principal principal) {
        String currentUserEmail =principal.getName();
        User currentUser = userService.getByEmail(currentUserEmail);
        webRTCOffer.setUser(currentUser);
        simpMessagingTemplate.convertAndSendToUser(webRTCOffer.getEmail(),"/call-made",webRTCOffer);
    }

    @MessageMapping("/make-answer")
    public void makeAnswer(@Payload @NotNull WebRTCAnswer webRTCAnswer, Principal principal) {
        String currentUserEmail =principal.getName();
        User currentUser = userService.getByEmail(currentUserEmail);
        webRTCAnswer.setUser(currentUser);
        simpMessagingTemplate.convertAndSendToUser(webRTCAnswer.getEmail(),"/answer-made",webRTCAnswer);
    }
    @MessageMapping("/end-call")
    public void endCall(@Payload @NotNull WebRTCEndCall webRTCEndCall,Principal principal) {
        String currentUserEmail =principal.getName();
        simpMessagingTemplate.convertAndSendToUser(currentUserEmail,"/end-call",webRTCEndCall);
        simpMessagingTemplate.convertAndSendToUser(webRTCEndCall.getEmail(),"/end-call",webRTCEndCall);
    }
}
