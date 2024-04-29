package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.MessageDAO;
import com.zerocoder.devsearch.entity.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {
    private EntityManager entityManager;
    @Autowired
    public MessageDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveMessage(Message theMessage) {
        entityManager.persist(theMessage);
    }

    @Override
    public Message getMessage(Long theId) {
        TypedQuery<Message> query = entityManager.createQuery("from Message m left join fetch m.sender join fetch m.receiver where m.message_id=:messageId", Message.class);
        query.setParameter("messageId", theId);
        return query.getSingleResult();
    }

    @Override
    public void deleteMessage(Long theId) {
        Message theMessage = entityManager.find(Message.class, theId);
        entityManager.remove(theMessage);
    }

    @Override
    public List<Message> getAllMessages() {
        TypedQuery<Message> query = entityManager.createQuery("from Message m left join fetch m.sender join fetch m.receiver", Message.class);
        return query.getResultList();
    }

    @Override
    public void updateMessage(Message theMessage) {
        entityManager.merge(theMessage);
    }
}
