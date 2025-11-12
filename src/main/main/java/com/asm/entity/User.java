package com.asm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "password")
  private String password;

  @Column(name = "fullname")
  private String fullname;

  @Column(name = "email")
  private String email;

  @Column(name = "admin")
  private Boolean admin = false;

  @OneToMany(mappedBy = "user")
  private List<Favorite> favorites;

  @OneToMany(mappedBy = "user")
  private List<Share> shares;
}
