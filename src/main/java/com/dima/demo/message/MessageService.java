package com.dima.demo.message;

import com.dima.demo.exception.ApiRequestException;
import com.dima.demo.message.HelperClasses.MessageCreateRequest;
import com.dima.demo.message.HelperClasses.MessageUpdateRequest;
import com.dima.demo.user.User;
import com.dima.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    public Message createNewMessage(MessageCreateRequest messageCreateRequest) {
        User sender = userRepository.findById(messageCreateRequest.senderId()).orElseThrow(()-> new ApiRequestException("Sender not found"));
        User receiver = userRepository.findById(messageCreateRequest.receiverId()).orElseThrow(()-> new ApiRequestException("Sender not found"));
        Message newMessage =  messageRepository.save(new Message(
                sender,
                receiver,
                messageCreateRequest.text(),
                false
        ));
        messageRepository.save(newMessage);
        return newMessage;
    }

    public List<Message> getAllUserMessages(Long userId) {
        return messageRepository.findAllBySenderIdOrReceiverId(userId,userId);
    }

    public Message updateMessage(MessageUpdateRequest messageUpdateRequest) {
        Message originalMessage = messageRepository.findById(messageUpdateRequest.id()).orElseThrow(()->new ApiRequestException("Message not found"));

        if(messageUpdateRequest.isMessageRead() != null){
            originalMessage.setIsMessageRead(messageUpdateRequest.isMessageRead());
        }
        if(messageUpdateRequest.receiverId() != null){
            User newReceiver = userRepository.findById(messageUpdateRequest.receiverId()).orElseThrow(()->new ApiRequestException("Receiver not found"));
            originalMessage.setReceiver(newReceiver);
        }
        if(messageUpdateRequest.senderId() != null){
            User newSender = userRepository.findById(messageUpdateRequest.senderId()).orElseThrow(()->new ApiRequestException("Sender not found"));
            originalMessage.setReceiver(newSender);
        }
        if(messageUpdateRequest.text() != null){
            originalMessage.setText(messageUpdateRequest.text());
        }
        return messageRepository.save(originalMessage);
    }

    public void setMessagesToBeRead(List<Long> messagesId) {
        messageRepository.updateMessagesToBeRead(messagesId);
    }
}
