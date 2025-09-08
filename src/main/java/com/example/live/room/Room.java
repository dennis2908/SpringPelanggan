package com.example.live.room;

import com.example.live.gim.Gim;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "buildNo")
  private Integer buildNo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getBuildNo() {
    return buildNo;
  }

  public void setBuildNo(Integer buildNo) {
    this.buildNo = buildNo;
  }
}
