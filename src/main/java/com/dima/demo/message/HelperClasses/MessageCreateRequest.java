package com.dima.demo.message.HelperClasses;


import jakarta.annotation.Nullable;

public record MessageCreateRequest(Long senderId,@Nullable Long receiverId, String text) {
}
