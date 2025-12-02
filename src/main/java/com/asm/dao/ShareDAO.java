package com.asm.dao;

import java.util.List;

import com.asm.entity.Share;

public interface ShareDAO extends CrudDAO<Share, Long> {
  List<Share> getShareStatisByTitle(String title);
}
