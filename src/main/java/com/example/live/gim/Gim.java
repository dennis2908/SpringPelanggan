package com.example.live.gim;

import com.example.live.rack.Rack;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "gims")
public class Gim implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;


  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "rack_id")
  private Rack rack;

  @Column(name = "publisher")
  private String publisher;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column(name = "yearPublish")
  private String yearPublish;

  //getters and setters

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

  public Rack getRackNo() {
    return rack;
  }

  public void setRackNo(Rack rack) {
    this.rack = rack;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getYearPublish() {
    return yearPublish;
  }

  public void setYearPublish(String yearPublish) {
    this.yearPublish = yearPublish;
  }
}
