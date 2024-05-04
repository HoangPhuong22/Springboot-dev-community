package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Message;

import java.util.List;

public interface MessageDAO {
    public void saveMessage(Message theMessage);
    public Message getMessage(Long theId);
    public void deleteMessage(Long theId);
    public List<Message> getAllMessages();
    public List<Message> getReceivedMessages(Long id);
    public void updateMessage(Message theMessage);
}
