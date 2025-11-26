package com.asm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatDTO {
  long videoCount;
  long userCount;
  long likeCount;
  long shareCount;
}