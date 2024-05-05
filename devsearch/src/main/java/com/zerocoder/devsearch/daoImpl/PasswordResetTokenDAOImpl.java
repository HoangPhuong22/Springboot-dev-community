package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.PasswordResetTokenDAO;
import com.zerocoder.devsearch.entity.PasswordResetToken;
import com.zerocoder.devsearch.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {

    private EntityManager entityManager;

    @Autowired
    public PasswordResetTokenDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void savePasswordResetToken(PasswordResetToken passwordResetToken) {
        entityManager.persist(passwordResetToken);
    }

    @Override
    public PasswordResetToken getPasswordResetTokenByToken(String token) {
        List<PasswordResetToken> tokens = entityManager.createQuery("select p from PasswordResetToken p where p.token = :token", PasswordResetToken.class)
                .setParameter("token", token)
                .getResultList();
        if (tokens.isEmpty()) {
            return null; // or throw new NoResultException("No PasswordResetToken found with the provided token");
        } else {
            return tokens.get(0);
        }
    }

    @Override
    public void deletePasswordResetToken(Long id) {
        PasswordResetToken passwordResetToken = entityManager.find(PasswordResetToken.class, id);
        entityManager.remove(passwordResetToken);
    }

    @Override
    public void deletePasswordResetTokenByUser(User user) {
        try {
            entityManager.createQuery("DELETE FROM PasswordResetToken p WHERE p.user = :user")
                    .setParameter("user", user)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}