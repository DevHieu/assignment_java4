package com.asm.dao;

import java.util.List;

import com.asm.entity.Video;

public interface VideoDAO extends CrudDAO<Video, String> {
  List<Video> getBannerVideo();

  List<Video> findAll();

  List<Video> searchByTitle(String keyword);

  List<Video> findPage(int page, int size);

  int countAll();

  void removeBanner(String videoId);

  public int countAllVideos();

  List<Object[]> searchVideo(String textSearch, String JPQL);

  boolean updateViews(String videoId);

  List<Video> find10RandomVideo();
}
