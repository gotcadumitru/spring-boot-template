package com.dima.demo.registration.token;

import com.dima.demo.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/confirmation-token")
@AllArgsConstructor
public class ConfirmationTokenController {
    public final ConfirmationTokenService confirmationTokenService;
    @GetMapping(path = "all")
    public List<ConfirmationToken> getAllConfirmationTokens(){
        return confirmationTokenService.getAllConfirmationTokens();
    }

}
