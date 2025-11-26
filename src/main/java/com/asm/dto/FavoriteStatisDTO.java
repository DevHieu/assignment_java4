package com.asm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteStatisDTO {
  private String title;
  private long likeCount;
  private Date latestDate;
  private Date oldestDate;
}
