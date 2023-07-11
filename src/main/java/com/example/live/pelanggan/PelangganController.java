package com.example.live.pelanggan;

import com.example.live.function.ResponseHandler;
import com.example.live.function.ResponseHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pelanggans")
public class PelangganController {

  @Autowired
  private PelangganRepository pelangganRepository;

  private ResponseHandler responseHandler;

  @GetMapping
  // public List<Pelanggan> getAllPelanggans() {
  //   return pelangganRepository.findAll();
  // }
  ResponseEntity<Object> getAllPelanggans() {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      pelangganRepository.findAll()
    );
  }

  @GetMapping("/{id}")
  // public Pelanggan getPelangganById(@PathVariable Long id) {
  //   return pelangganRepository.findById(id).get();
  // }

  ResponseEntity<Object> getPelangganById(@PathVariable Long id) {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      pelangganRepository.findById(id).get()
    );
  }

  @GetMapping("/findPelangganByName")
  ResponseEntity<Object> findPelangganByName(@RequestParam String name) {
    // System.out.println("ddddd",userRepository.findUserByName(name));
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      pelangganRepository.findPelangganByName(name)
    );
  }

  @PostMapping
  // public Pelanggan createPelanggan(@RequestBody Pelanggan pelanggan) {
  //   return pelangganRepository.save(pelanggan);
  // }

  ResponseEntity<Object> createPelanggan(@RequestBody Pelanggan pelanggan) {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      pelangganRepository.save(pelanggan)
    );
  }

  @PutMapping("/{id}")
  // public Pelanggan updatePelanggan(@PathVariable Long id, @RequestBody Pelanggan pelanggan) {
  //   Pelanggan existingPelanggan = pelangganRepository.findById(id).get();
  //   existingPelanggan.setName(pelanggan.getName());
  //   existingPelanggan.setEmail(pelanggan.getEmail());
  //   existingPelanggan.setAddress(pelanggan.getAddress());
  //   return pelangganRepository.save(existingPelanggan);
  // }

  ResponseEntity<Object> updatePelanggan(
    @PathVariable Long id,
    @RequestBody Pelanggan pelanggan
  ) {
    Pelanggan existingPelanggan = pelangganRepository.findById(id).get();
    existingPelanggan.setName(pelanggan.getName());
    existingPelanggan.setEmail(pelanggan.getEmail());
    existingPelanggan.setAddress(pelanggan.getAddress());
    existingPelanggan.setDateBirth(pelanggan.getDateBirth());
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      pelangganRepository.save(existingPelanggan)
    );
  }

  @DeleteMapping("/{id}")
  public String deletePelanggan(@PathVariable Long id) {
    try {
      pelangganRepository.findById(id).get();
      pelangganRepository.deleteById(id);
      return "Pelanggan deleted successfully";
    } catch (Exception e) {
      return "Pelanggan not found";
    }
  }
}
