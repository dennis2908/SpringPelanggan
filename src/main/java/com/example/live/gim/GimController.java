package com.example.live.gim;

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
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/gims")
public class GimController {

  @Autowired
  private GimRepository gimRepository;

  @GetMapping
  // public List<Gim> getAllGims() {
  //   return gimRepository.findAll();
  // } 
  ResponseEntity<Object> getAllGims() {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      gimRepository.findAll()
    );
  }

  @GetMapping("/{id}")
  // public Gim getGimById(@PathVariable Long id) {
  //   return gimRepository.findById(id).get();
  // }

  ResponseEntity<Object> getGimById(@PathVariable Long id) {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      gimRepository.findById(id).get()
    );
  }

  @PostMapping
  // public Gim createGim(@RequestBody Gim gim) {
  //   return gimRepository.save(gim);
  // }
  ResponseEntity<Object> createGim(@RequestBody Gim gim) {
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      gimRepository.save(gim)
    );
  }
  
  @PutMapping("/{id}")
  // public Gim updateGim(@PathVariable Long id, @RequestBody Gim gim) {
  //   Gim existingGim = gimRepository.findById(id).get();
  //   existingGim.setName(gim.getName());
  //   existingGim.setRackNo(gim.getRackNo());
  //   existingGim.setYearPublish(gim.getYearPublish());
  //   existingGim.setPublisher(gim.getPublisher());
  //   return gimRepository.save(existingGim);
  // }
  ResponseEntity<Object> updateGim(@PathVariable Long id, @RequestBody Gim gim) {
    Gim existingGim = gimRepository.findById(id).get();
    existingGim.setName(gim.getName());
    existingGim.setRackNo(gim.getRackNo());
    existingGim.setYearPublish(gim.getYearPublish());
    existingGim.setPublisher(gim.getPublisher());
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      gimRepository.save(existingGim)
    );
  }

  @DeleteMapping("/{id}")
  public String deleteGim(@PathVariable Long id) {
    try {
      gimRepository.findById(id).get();
      gimRepository.deleteById(id);
      return "Gim deleted successfully";
    } catch (Exception e) {
      return "Gim not found";
    }
  }
  
}
