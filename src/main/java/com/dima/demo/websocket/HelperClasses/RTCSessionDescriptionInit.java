package com.dima.demo.websocket.HelperClasses;

import jakarta.annotation.Nullable;
import lombok.Data;


@Data
public class RTCSessionDescriptionInit {
    @Nullable
    String sdp;
    RTCSdpType type;
}
