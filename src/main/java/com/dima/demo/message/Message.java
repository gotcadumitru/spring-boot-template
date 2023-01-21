package com.dima.demo.message;

import com.dima.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String text;

    private Boolean isMessageRead;

    public Message(User sender, User receiver, String text, Boolean isMessageRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.isMessageRead = isMessageRead;
    }

}
