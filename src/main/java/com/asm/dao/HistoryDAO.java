package com.asm.dao;

import com.asm.entity.History;

public interface HistoryDAO extends CrudDAO<History, Long> {
  History existsByUserIdAndVideoId(String userId, String videoId);
}
