package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.VideoDAO;
import com.asm.entity.Video;
import com.asm.utils.XJpa;

public class VideoDAOImpl implements VideoDAO {

    private EntityManager em = XJpa.getEntityManager();

    @Override
    public List<Video> findAll() {
        String jpql = "SELECT v FROM Video v ORDER BY v.title";
        return em.createQuery(jpql, Video.class).getResultList();
    }

    @Override
    public Video findById(String id) {
        return em.find(Video.class, id);
    }

    @Override
    public void create(Video item) {
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
    public void update(Video item) {
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
    public void deleteById(String id) {
        Video video = em.find(Video.class, id);

        if (video == null)
            return;

        try {
            em.getTransaction().begin();
            em.remove(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Video> searchByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String jpql = "SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(:kw)";
        TypedQuery<Video> query = em.createQuery(jpql, Video.class);
        query.setParameter("kw", "%" + keyword.trim() + "%");
        return query.getResultList();
    }

    @Override
    public List<Video> findPage(int offset, int size) {
        String jpql = "SELECT v FROM Video v ORDER BY v.title";
        TypedQuery<Video> query = em.createQuery(jpql, Video.class);
        query.setFirstResult(offset); // offset đúng
        query.setMaxResults(size); // số lượng mỗi trang
        return query.getResultList();
    }

    @Override
    public int countAll() {
        String jpql = "SELECT COUNT(v) FROM Video v";
        return em.createQuery(jpql, Long.class).getSingleResult().intValue();
    }

    @Override
    public List<Video> getBannerVideo() {
        String jpql = "SELECT v FROM Video v WHERE v.isBanner = true";
        return em.createQuery(jpql, Video.class).getResultList();
    }

    @Override
    public void removeBanner(String videoId) {
        Video video = em.find(Video.class, videoId);

        if (video == null)
            return;

        try {
            em.getTransaction().begin();
            video.setBanner(false);
            em.merge(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
}
