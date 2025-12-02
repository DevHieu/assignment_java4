package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.asm.dao.HistoryDAO;
import com.asm.entity.History;
import com.asm.utils.XJpa;

public class HistoryDAOImpl implements HistoryDAO {

  private EntityManager em = XJpa.getEntityManager();

  @Override
  public List<History> findAll() {
    String jpql = "SELECT h FROM History h";
    return em.createQuery(jpql, History.class).getResultList();
  }

  @Override
  public History findById(Long id) {
    return em.find(History.class, id);
  }

  @Override
  public void create(History item) {
    try {
      em.getTransaction().begin();
      em.persist(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    }
  }

  @Override
  public void update(History item) {
    try {
      em.getTransaction().begin();
      em.merge(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    }
  }

  @Override
  public void deleteById(Long id) {
    try {
      em.getTransaction().begin();
      History history = em.find(History.class, id);
      if (history != null) {
        em.remove(history);
      }
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    }
  }

  @Override
  public int countAll() {
    String jpql = "SELECT COUNT(h) FROM History h";
    Long count = em.createQuery(jpql, Long.class).getSingleResult();
    return count.intValue();
  }

  @Override
  public History existsByUserIdAndVideoId(String userId, String videoId) {
    String jpql = "SELECT h FROM History h WHERE h.userId = :userId AND h.videoId = :videoId";
    List<History> results = em.createQuery(jpql, History.class)
        .setParameter("userId", userId)
        .setParameter("videoId", videoId)
        .getResultList();
    return results.isEmpty() ? null : results.get(0);
  }
}
