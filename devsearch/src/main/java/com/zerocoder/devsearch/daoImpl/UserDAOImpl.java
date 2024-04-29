package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.UserDAO;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;
    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getAllUserRoles() {
        TypedQuery<User> query = entityManager.createQuery("from User u left join fetch u.roles", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(Long id) {
        User user =  entityManager.find(User.class, id);
        if(entityManager.contains(user)) System.out.println("YESSS ZEROCODER");
        else System.out.println("NOOO ZEROCODER");
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        TypedQuery<User> query = entityManager.createQuery("from User where userName =: userName", User.class);
        query.setParameter("userName", userName);
        return query.getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User where email =: email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public User getUserRoleById(Long id) {
        TypedQuery<User>query = entityManager.createQuery("from User u left join fetch u.roles where u.id =: id", User.class);
        query.setParameter("id", id);
        User user = query.getSingleResult();
        if(entityManager.contains(user)) System.out.println("YESSS ZEROCODER");
        else System.out.println("NOOO ZEROCODER");
        return user;
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User checkUserByUserName(String userName, Long id) {
        TypedQuery<User> query = entityManager.createQuery("from User where userName =: userName and userId !=: id", User.class);
        query.setParameter("userName", userName);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public User checkUserByEmail(String email, Long id) {
        TypedQuery<User> query = entityManager.createQuery("from User where email =: email and userId !=: id", User.class);
        query.setParameter("email", email);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
