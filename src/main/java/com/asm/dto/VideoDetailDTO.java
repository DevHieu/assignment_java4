package com.asm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDetailDTO {
  private String id;
  private String title;
  private String poster;
  private int views;
  private String description;
  private boolean liked;
}
