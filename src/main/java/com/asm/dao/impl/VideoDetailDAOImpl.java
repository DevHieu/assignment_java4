package com.asm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asm.dao.VideoDetailDAO;
import com.asm.dto.VideoDetailDTO;
import com.asm.utils.XJpa;

public class VideoDetailDAOImpl implements VideoDetailDAO {

  EntityManager em = XJpa.getEntityManager();

  @Override
  public VideoDetailDTO findById(String id) {
    String jpql = "SELECT new com.asm.dto.VideoDetailDto(v.id, v.title, v.poster, v.views, v.description, ) "
        + "FROM Video v WHERE v.id = :id";

    TypedQuery<VideoDetailDTO> query = em.createQuery(jpql, VideoDetailDTO.class);
    query.setParameter("id", id);

    return query.getSingleResult();
  }

  @Override
  public List<VideoDetailDTO> findByPage(String userId, int page, int offset) {
    String jpql = "SELECT new com.asm.dto.VideoDetailDTO("
        + "v.id, v.title, v.poster, v.views, v.description, "
        + "CASE WHEN EXISTS (SELECT f FROM Favorite f WHERE f.video.id = v.id AND f.user.id = :userId) then true else false END"
        + ") "
        + "FROM Video v "
        + "WHERE v.active = TRUE ";

    TypedQuery<VideoDetailDTO> query = em.createQuery(jpql, VideoDetailDTO.class);
    query.setParameter("userId", userId);
    query.setFirstResult((page - 1) * offset);
    query.setMaxResults(offset);

    return query.getResultList();
  }

  @Override
  public List<VideoDetailDTO> findAll(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
  }

}