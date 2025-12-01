package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.VideoDAO;
import com.asm.entity.Video;
import com.asm.utils.XJpa;

public class VideoDAOImpl implements VideoDAO {

	EntityManager em = XJpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
	}

	@Override
	public List<Video> findAll() {
		String sql = "SELECT f FROM Video f";
		TypedQuery<Video> query = em.createQuery(sql, Video.class);
		return query.getResultList();
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
		}
	}

	@Override
	public void deleteById(String id) {
		Video user = em.find(Video.class, id);
		try {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public int countAllVideos() {
		String sql = "SELECT COUNT(v) FROM Video v";
		TypedQuery<Long> query = em.createQuery(sql, Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public List<Object[]> searchVideo(String textSearch, String JPQL) {
		// TODO Auto-generated method stub
		String searchText = (textSearch == null) ? "%" : "%" + textSearch + "%";

		TypedQuery<Object[]> query = em.createQuery(JPQL, Object[].class);
		query.setParameter("text", searchText);

		return query.getResultList();
	}

}
