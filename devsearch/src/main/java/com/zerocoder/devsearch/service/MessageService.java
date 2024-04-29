package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Message;

import java.util.List;

public interface MessageService {
    public void saveMessage(Message theMessage);
    public Message getMessage(Long theId);
    public void deleteMessage(Long theId);
    public List<Message> getAllMessages();
    public void updateMessage(Message theMessage);
}
