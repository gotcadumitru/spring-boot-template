package com.dima.demo.websocket.HelperClasses;


import com.dima.demo.user.User;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class WebRTCOffer {
    @Nullable
    User user;
    String email;
    RTCSessionDescriptionInit offer;
}
