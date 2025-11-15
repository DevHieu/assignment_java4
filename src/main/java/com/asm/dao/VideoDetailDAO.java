package com.asm.dao;

import java.util.List;

import com.asm.dto.VideoDetailDTO;

public interface VideoDetailDAO {
  VideoDetailDTO findById(String id);

  List<VideoDetailDTO> findByPage(String userId, int page, int offset);

  List<VideoDetailDTO> findAll(String userId);

}
