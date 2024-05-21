package com.example.live.excel.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.live.excel.helper.HelperDExcelPelanggan;
import com.example.live.pelanggan.Pelanggan;
import com.example.live.pelanggan.PelangganRepository;
@Service
public class ServiceDPelanggan {
  @Autowired
  PelangganRepository pelanggan;

  public void save(MultipartFile file) {
    try {
      List<Pelanggan> tutorials = HelperDExcelPelanggan.toExcelPelanggans(file.getInputStream());
      pelanggan.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public List<Pelanggan> getAllPelanggans() {
    return pelanggan.findAll();
  }
}
