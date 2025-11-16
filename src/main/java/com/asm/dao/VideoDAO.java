package com.asm.dao;

import com.asm.entity.Video;

public interface VideoDAO extends CrudDAO<Video, String> {
  public int countAllVideos();
}
