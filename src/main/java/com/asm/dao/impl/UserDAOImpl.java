package com.asm.dao.impl;

import com.asm.dao.UserDAO;
import com.asm.entity.User;
import com.asm.utils.XJpa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    private EntityManager em = XJpa.getEntityManager();

    @Override
    public List<User> findAll() {
        
        try {
            String jpql = "SELECT u FROM User u ORDER BY u.id";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        } finally {
        }
    }

    @Override
    public User findById(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
        }
    }

    @Override
    public void create(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi thêm user", e);
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi cập nhật user", e);
        }
    }

    @Override
    public void deleteById(String id) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        if (user != null) {
            try {
                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new RuntimeException("Lỗi khi xóa user", e);
            }
        }
    }

    @Override
    public List<User> searchByKeyword(String keyword) {
        EntityManager em = getEntityManager();
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return findAll();
            }
            String jpql = "SELECT u FROM User u WHERE LOWER(u.fullname) LIKE LOWER(:kw) OR LOWER(u.email) LIKE LOWER(:kw)";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("kw", "%" + keyword.trim() + "%");
            return query.getResultList();
        } finally {
        }
    }

    @Override
    public List<User> findByRole(boolean admin) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.admin = :role ORDER BY u.id";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("role", admin);
            return query.getResultList();
        } finally {
        }
    }

    @Override
    public List<User> findPage(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u ORDER BY u.id";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            // Không đóng
        }
    }

    @Override
    public int count() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            return query.getSingleResult().intValue();
        } finally {
            // Không đóng
        }
    }

    @Override
  public int countAll() {
    String sql = "SELECT COUNT(u) FROM User u";
    TypedQuery<Long> query = em.createQuery(sql, Long.class);
    return query.getSingleResult().intValue();
  }
}
