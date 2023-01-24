package com.dima.demo.message;

import com.dima.demo.message.HelperClasses.MessageCreateRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    public Message createMessage(@RequestBody MessageCreateRequest messageCreateRequest) {
        return messageService.createNewMessage(messageCreateRequest);
    }

    @GetMapping(path = "/all/{userId}")
    public List<Message> getAllUserMessages(@PathVariable @NonNull Long userId) {
        return messageService.getAllUserMessages(userId);
    }

    @GetMapping(path = "/chats")
    public List<Message> getAllChats() {
        return messageService.getAllMessages();
    }

    @PutMapping("/set-read")
    public void setMessageRead(@RequestBody List<Long> messagesId) {
        messageService.setMessagesToBeRead(messagesId);
    }

    @PutMapping("/send-message")
    public Message sendMessage(@RequestBody MessageCreateRequest messageCreateRequest) {
        Message newMessage = messageService.createNewMessage(messageCreateRequest);

        //simpMessagingTemplate.convertAndSendToUser(newMessage.getSender().getEmail(), "new-message-simple-user", newMessage);
        simpMessagingTemplate.convertAndSend("/topic/new-message-moderator", newMessage);
        if (newMessage.getReceiver() != null) {
            // message from moderator to user
            simpMessagingTemplate.convertAndSendToUser(newMessage.getReceiver().getEmail(), "/new-message-simple-user", newMessage);
        } else {
            //message from user to moderator
            simpMessagingTemplate.convertAndSendToUser(newMessage.getSender().getEmail(), "/new-message-simple-user", newMessage);
        }
        return newMessage;
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload MessageCreateRequest textMessageDTO) {
        // receive message from client
    }
}
