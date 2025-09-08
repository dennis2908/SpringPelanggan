package com.example.live.rack;

import com.example.live.gim.Gim;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "racks")
public class Rack implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "roomNo")
  private Integer roomNo;

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

  public Integer getRoomNo() {
    return roomNo;
  }

  public void setRoomNo(Integer roomNo) {
    this.roomNo = roomNo;
  }
}
