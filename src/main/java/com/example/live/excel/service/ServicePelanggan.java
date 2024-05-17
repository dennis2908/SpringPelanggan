package com.example.live.excel.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.live.excel.helper.HelperPelanggan;
import com.example.live.pelanggan.Pelanggan;
import com.example.live.pelanggan.PelangganRepository;

@Service
public class ServicePelanggan {
  @Autowired
  private PelangganRepository pelangganRepository;

  public ByteArrayInputStream load() {
    List<Pelanggan> pelanggans = pelangganRepository.findAll();

    ByteArrayInputStream in = HelperPelanggan.pelanggansToExcel(pelanggans);
    return in;
  }

}
