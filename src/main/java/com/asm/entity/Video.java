package com.asm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "video")
public class Video {
  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "title")
  private String title;
  @Column(name = "poster")
  private String poster;
  @Column(name = "views")
  private int views;
  @Column(name = "description", columnDefinition = "TEXT")
  private String description;
  @Column(name = "isBanner")
  private boolean isBanner = false;
  @Column(name = "active")
  private boolean active = false;

  @OneToMany(mappedBy = "video")
  private List<Favorite> favorites;

  @OneToMany(mappedBy = "video")
  private List<Share> shares;
}
