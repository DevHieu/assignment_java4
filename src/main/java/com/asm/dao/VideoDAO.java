package com.asm.dao;

import java.util.List;

import com.asm.entity.Video;

public interface VideoDAO extends CrudDAO<Video, String> {
  List<Video> getBannerVideo();

  void removeBanner(String videoId);
}
