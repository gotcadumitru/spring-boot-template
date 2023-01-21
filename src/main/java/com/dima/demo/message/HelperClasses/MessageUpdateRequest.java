package com.dima.demo.message.HelperClasses;


public record MessageUpdateRequest(Long id,String text, Boolean isMessageRead,Long senderId,Long receiverId) {
}
