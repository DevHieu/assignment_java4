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
  protected void finalize() throws Throwable {
    em.close();
  }

  @Override
  public List<User> findAll() {
    String sql = "SELECT f FROM Favorite f";
    TypedQuery<User> query = em.createQuery(sql, User.class);
    return query.getResultList();
  }

  @Override
  public User findById(String id) {
    return em.find(User.class, id);
  }

  @Override
  public void create(User item) {
    try {
      em.getTransaction().begin();
      em.persist(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public void update(User item) {
    try {
      em.getTransaction().begin();
      em.merge(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public void deleteById(String id) {
    User user = em.find(User.class, id);
    try {
      em.getTransaction().begin();
      em.remove(user);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public int countAll() {
    String sql = "SELECT COUNT(u) FROM User u";
    TypedQuery<Long> query = em.createQuery(sql, Long.class);
    return query.getSingleResult().intValue();
  }

}
