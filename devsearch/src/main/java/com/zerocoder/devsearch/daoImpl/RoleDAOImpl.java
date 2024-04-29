package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.RoleDAO;
import com.zerocoder.devsearch.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private EntityManager entityManager;
    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> query = entityManager.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where id =: id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
