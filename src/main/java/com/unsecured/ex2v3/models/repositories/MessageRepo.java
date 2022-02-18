package com.unsecured.ex2v3.models.repositories;

import com.unsecured.ex2v3.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Integer> {

    List<Message> findAllByCharRoomName(String chatRoomName);
}
