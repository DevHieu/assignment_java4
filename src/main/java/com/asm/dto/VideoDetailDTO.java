package com.asm.dto;

import java.util.Date;

import lombok.Data; 

@Data
public class VideoDetailDTO {
  private String id;
  private String title;
  private String poster;
  private int views;
  private String description;
  private int totalLike;
  private boolean liked;
  private Date viewDate; 
  public VideoDetailDTO() {
  }

  public VideoDetailDTO(String id, String title, String poster, int views, String description, int totalLike, boolean liked) {
      this.id = id;
      this.title = title;
      this.poster = poster;
      this.views = views;
      this.description = description;
      this.totalLike = totalLike;
      this.liked = liked;
      this.viewDate = null; 
  }

  public VideoDetailDTO(String id, String title, String poster, int views, String description, int totalLike, boolean liked, Date viewDate) {
      this(id, title, poster, views, description, totalLike, liked); // Gọi constructor 7-argument
      this.viewDate = viewDate;
  }
}