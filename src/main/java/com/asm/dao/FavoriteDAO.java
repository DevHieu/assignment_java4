package com.asm.dao;

import java.util.List;

import com.asm.dto.FavoriteStatisDTO;
import com.asm.dto.VideoDetailDTO;
import com.asm.entity.Favorite;

public interface FavoriteDAO extends CrudDAO<Favorite, Long> {
  List<VideoDetailDTO> get10MostLikeVideo();

  List<FavoriteStatisDTO> getFavoriteStatis();

  List<Favorite> getFavoriteUserStatisByTitle(String title);
}
