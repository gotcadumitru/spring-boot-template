package com.dima.demo.message.HelperClasses;


public record MessageCreateRequest(Long senderId, Long receiverId, String text) {
}
