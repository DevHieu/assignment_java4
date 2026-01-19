package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.ShareDAO;
import com.asm.entity.Share;
import com.asm.utils.XJpa;

public class ShareDAOImpl implements ShareDAO {

  EntityManager em = XJpa.getEntityManager();

  @Override
  protected void finalize() throws Throwable {
    em.close();
  }

  @Override
  public List<Share> findAll() {
    String sql = "SELECT s FROM Share s";
    TypedQuery<Share> query = em.createQuery(sql, Share.class);
    return query.getResultList();
  }

  @Override
  public Share findById(Long id) {
    return em.find(Share.class, id);
  }

  @Override
  public void create(Share item) {
    try {
      em.getTransaction().begin();
      em.persist(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public void update(Share item) {
    try {
      em.getTransaction().begin();
      em.merge(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public void deleteById(Long id) {
    Share user = em.find(Share.class, id);
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
    String sql = "SELECT COUNT(s) FROM Share s";
    TypedQuery<Long> query = em.createQuery(sql, Long.class);
    return query.getSingleResult().intValue();
  }

  @Override
  public List<Share> getShareStatisByTitle(String title) {
    // String sql = "SELECT new com.asm.dto.ShareStatis(u.fullname, u.email,
    // s.emails, s.shareDate) "
    // + "FROM Share s WHERE s.video.title LIKE :title";

    String sql = "SELECT s FROM Share s WHERE s.video.title LIKE :title";
    TypedQuery<Share> query = em.createQuery(sql, Share.class);
    query.setParameter("title", "%" + title + "%");
    return query.getResultList();
  }

}
