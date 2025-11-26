package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.utils.XJpa;

public class UserDAOImpl implements UserDAO {

    EntityManager em = XJpa.getEntityManager();

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        String sql = "SELECT u FROM User u";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        return query.getResultList();
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        return em.find(User.class, id);
    }

    @Override
    public User create(User item) {
        // TODO Auto-generated method stub
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            System.out.println("User created: " + item.getFullname());

        } catch (Exception e) {
            // TODO: handle exception
            em.getTransaction().rollback();
        }
        return item;
    }

    @Override
    public void update(User item) {
        // TODO Auto-generated method stub
        try {
            em.getTransaction().begin();
            em.merge(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            em.getTransaction().rollback();
        }
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        User user = em.find(User.class, id);
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            em.getTransaction().rollback();
        }
    }

    @Override
    public boolean checkUsernameExist(String username) {
        // TODO Auto-generated method stub
        String jpql = "SELECT u FROM User u WHERE u.id = :id";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("id", username);
        return !query.getResultList().isEmpty();
    }

    @Override
    public boolean checkEmailExist(String email) {
        // TODO Auto-generated method stub
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }

}
