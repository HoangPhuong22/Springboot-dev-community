package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.MessageDAO;
import com.zerocoder.devsearch.entity.Message;
import com.zerocoder.devsearch.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDAO messageDAO;
    @Override
    @Transactional
    public void saveMessage(Message theMessage) {
        messageDAO.saveMessage(theMessage);
    }

    @Override
    public Message getMessage(Long theId) {
        return messageDAO.getMessage(theId);
    }

    @Override
    @Transactional
    public void deleteMessage(Long theId) {
        messageDAO.deleteMessage(theId);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    @Override
    public List<Message> getReceivedMessages(Long id) {
        try
        {
            return messageDAO.getReceivedMessages(id);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateMessage(Message theMessage) {
        messageDAO.updateMessage(theMessage);
    }
}
