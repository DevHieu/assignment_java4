package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.FavoriteDAO;
import com.asm.dto.FavoriteStatisDTO;
import com.asm.dto.VideoDetailDTO;
import com.asm.entity.Favorite;
import com.asm.utils.XJpa;

public class FavoriteDAOImpl implements FavoriteDAO {

  EntityManager em = XJpa.getEntityManager();

  @Override
  protected void finalize() throws Throwable {
    em.close();
  }

  @Override
  public List<Favorite> findAll() {
    String sql = "SELECT f FROM Favorite f";
    TypedQuery<Favorite> query = em.createQuery(sql, Favorite.class);
    return query.getResultList();
  }

  @Override
  public Favorite findById(Long id) {
    return em.find(Favorite.class, id);
  }

  @Override
  public void create(Favorite item) {
    try {
      em.getTransaction().begin();
      em.persist(item);
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    }
  }

  @Override
  public void update(Favorite item) {
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
    Favorite user = em.find(Favorite.class, id);
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
    String jpql = "SELECT COUNT(f) FROM Favorite f";
    TypedQuery<Long> query = em.createQuery(jpql, Long.class);
    return query.getSingleResult().intValue();
  }

  @Override
  public List<VideoDetailDTO> get10MostLikeVideo() {
    String jpql = "SELECT NEW com.asm.dto.VideoDetailDTO(v.id, v.title, v.poster, v.views, v.description, SIZE(v.favorites), false) "
        + "FROM Favorite f JOIN f.video v "
        + "ORDER BY SIZE(v.favorites) DESC";

    TypedQuery<VideoDetailDTO> query = em.createQuery(jpql, VideoDetailDTO.class).setMaxResults(10);
    return query.getResultList();
  }

  @Override
  public List<FavoriteStatisDTO> getFavoriteStatis() {
    String jpql = "SELECT new com.asm.dto.FavoriteStatisDTO(v.title, COUNT(f), MIN(f.likeDate), MAX(f.likeDate)) from Favorite f JOIN f.video v GROUP BY v.title";
    TypedQuery<FavoriteStatisDTO> query = em.createQuery(jpql, FavoriteStatisDTO.class);
    return query.getResultList();
  }

  @Override
  public List<Favorite> getFavoriteUserStatisByTitle(String title) {
    String jpql = "SELECT f FROM Favorite f WHERE f.video.title LIKE :title";

    TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
    query.setParameter("title", "%" + title + "%");
    return query.getResultList();
  }

}
