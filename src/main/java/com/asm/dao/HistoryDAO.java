package com.asm.dao;

import java.util.List;

import com.asm.entity.History;

public interface HistoryDAO extends CrudDAO<History, Long> {
  History existsByUserIdAndVideoId(String userId, String videoId);
  List<History> findByUserId(String userId);
}
