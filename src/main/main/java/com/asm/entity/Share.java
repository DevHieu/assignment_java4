package com.asm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "share")
public class Share {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @ManyToOne
  @JoinColumn(name = "videoId")
  private Video video;

  @Column(name = "emails")
  private String emails;

  @Column(name = "shareDate")
  @Temporal(TemporalType.DATE)
  private Date shareDate;
}
