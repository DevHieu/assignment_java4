package com.asm.dao;

import java.util.List;

import com.asm.entity.Video;

public interface VideoDAO extends CrudDAO<Video, String> {
	public int countAllVideos();

	List<Object[]> searchVideo(String textSearch, String JPQL);
}
