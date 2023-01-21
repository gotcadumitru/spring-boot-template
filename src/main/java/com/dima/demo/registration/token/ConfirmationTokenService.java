package com.dima.demo.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken> getConfirmationToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
    public void deleteConfirmationTokenById(Long tokenId){
        confirmationTokenRepository.deleteById(tokenId);
    }
    public void deleteConfirmationTokensByUserId(Long userId){
        confirmationTokenRepository.deleteAllByUserId(userId);
    }

    public List<ConfirmationToken> getAllConfirmationTokens() {
        return confirmationTokenRepository.findAll();
    }
}
