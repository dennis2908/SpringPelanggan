package com.example.live.room;

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
@RequestMapping("/api/rooms")
public class RoomController {

  @Autowired
  private RoomRepository roomRepository;

  @GetMapping
  public List<Room> getAllRooms() {
    return roomRepository.findAll();
  } 

  @GetMapping("/{id}")
  public Room getRoomById(@PathVariable Long id) {
    return roomRepository.findById(id).get();
  }

  @PostMapping
  public Room createRoom(@RequestBody Room room) {
    return roomRepository.save(room);
  }
  
  @PutMapping("/{id}")
  public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
    Room existingRoom = roomRepository.findById(id).get();
    existingRoom.setName(room.getName());
    existingRoom.setBuildNo(room.getBuildNo());
    return roomRepository.save(existingRoom);
  }

  @DeleteMapping("/{id}")
  public String deleteRoom(@PathVariable Long id) {
    try {
      roomRepository.findById(id).get();
      roomRepository.deleteById(id);
      return "Room deleted successfully";
    } catch (Exception e) {
      return "Room not found";
    }
  }

  // @GetMapping("/findRoomRackByName")
  // ResponseEntity<Object> findRoomRackByName(@RequestParam String name) {
  //   // System.out.println("ddddd",userRepository.findUserByName(name));
  //   return ResponseHandler.generateResponse(
  //     HttpStatus.OK,
  //     true,
  //     "Success",
  //     roomRepository.findRoomRackByName(name)
  //   );
  // }
  @GetMapping("/findRoomRackByName")
  ResponseEntity<Object> findRoomRackByName(@RequestParam String name) {
    // System.out.println("ddddd",userRepository.findUserByName(name));
    return ResponseHandler.generateResponse(
      HttpStatus.OK,
      true,
      "Success",
      roomRepository.findRoomRackByName(name)
    );
  }
  
}