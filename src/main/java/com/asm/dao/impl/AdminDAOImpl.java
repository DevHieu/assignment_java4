package com.asm.dao.impl;

import javax.persistence.EntityManager;

import com.asm.dao.AdminDAO;
import com.asm.dto.AdminStatDTO;
import com.asm.utils.XJpa;

public class AdminDAOImpl implements AdminDAO {

  EntityManager em = XJpa.getEntityManager();

  @Override
  public AdminStatDTO getAdminStat() {
    String jpql = "SELECT new com.asm.dto.AdminStatDTO("
        + " (SELECT COUNT(v) FROM Video v), "
        + " (SELECT COUNT(u2) FROM User u2), "
        + " (SELECT COUNT(f) FROM Favorite f), "
        + " (SELECT COUNT(s) FROM Share s) "
        + ") "
        + "FROM User u";

    return em.createQuery(jpql, AdminStatDTO.class)
        .setMaxResults(1)
        .getSingleResult();
  }

}
