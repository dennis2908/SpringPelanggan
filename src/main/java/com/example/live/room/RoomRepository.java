package com.example.live.room;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
//   public Room findById(int roomId);

  @Query("SELECT u.id,u.name,u.buildNo,k.id as idRack, k.name as nameRack FROM Room u join Rack k on u.id = k.roomNo where u.name like %?1%")
  List<Object[]> findRoomRackByName(String name);
}
