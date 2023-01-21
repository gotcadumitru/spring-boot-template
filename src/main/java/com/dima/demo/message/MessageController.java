package com.dima.demo.message;

import com.dima.demo.message.HelperClasses.MessageCreateRequest;
import com.dima.demo.user.User;
import com.dima.demo.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    private final SimpMessagingTemplate simpMessagingTemplate;
    @PostMapping
    public Message createMessage(@RequestBody MessageCreateRequest messageCreateRequest){
        return messageService.createNewMessage(messageCreateRequest);
    }

    @GetMapping(path = "/all/{userId}")
    public List<Message> getAllUserMessages(@PathVariable @NonNull Long userId){
        return messageService.getAllUserMessages(userId);
    }

    @GetMapping(path = "/chats")
    public List<Message> getAllChats(@PathVariable @NonNull Long userId){
        return messageService.getAllUserMessages(userId);
    }

    @PutMapping("/set-read")
    public void setMessageRead(@RequestBody List<Long> messagesId){
            messageService.setMessagesToBeRead(messagesId);
    }

    @PutMapping("/send-message")
    public Message sendMessage(@RequestBody MessageCreateRequest messageCreateRequest) {
        Message newMessage = messageService.createNewMessage(messageCreateRequest);

        if(newMessage.getReceiver()!=null){
            // message from moderator to user
            simpMessagingTemplate.convertAndSendToUser(newMessage.getReceiver().getEmail(),"/new-message-moderator",newMessage);
        }else{
            //message from user to moderator
            simpMessagingTemplate.convertAndSend("new-message-moderator", newMessage);
        }
        return newMessage;
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload MessageCreateRequest textMessageDTO) {
        // receive message from client
    }
}
