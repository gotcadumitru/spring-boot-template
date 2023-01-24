package com.dima.demo.websocket.HelperClasses;


import com.dima.demo.user.User;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class WebRTCAnswer {
    @Nullable
    User user;
    String email;
    RTCSessionDescriptionInit answer;
}
