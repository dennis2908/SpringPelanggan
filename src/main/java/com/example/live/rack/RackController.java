package com.example.live.rack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/racks")
public class RackController {

  @Autowired
  private RackRepository rackRepository;

  @GetMapping
  public List<Rack> getAllRacks() {
    return rackRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Rack getRackById(@PathVariable Long id) {
    return rackRepository.findById(id).get();
  }

  @PostMapping
  public Rack createRack(@RequestBody Rack rack) {
    return rackRepository.save(rack);
  }
  
  @PutMapping("/{id}")
  public Rack updateRack(@PathVariable Long id, @RequestBody Rack rack) {
    Rack existingRack = rackRepository.findById(id).get();
    existingRack.setName(rack.getName());
    existingRack.setRoomNo(rack.getRoomNo());
    return rackRepository.save(existingRack);
  }

  @DeleteMapping("/{id}")
  public String deleteRack(@PathVariable Long id) {
    try {
      rackRepository.findById(id).get();
      rackRepository.deleteById(id);
      return "Rack deleted successfully";
    } catch (Exception e) {
      return "Rack not found";
    }
  }
  
}
