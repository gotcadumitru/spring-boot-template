package com.dima.demo.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message,Long> {
    @Transactional
    List<Message> findAllBySenderIdOrReceiverId(Long senderId, Long receiverId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.isMessageRead = true WHERE m.id IN :ids")
    void updateMessagesToBeRead(List<Long> ids);

}