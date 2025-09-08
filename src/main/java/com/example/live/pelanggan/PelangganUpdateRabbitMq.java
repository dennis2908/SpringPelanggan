package com.example.live.pelanggan;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.lang.Long;

@Service
public class PelangganUpdateRabbitMq {

  /**
   * Assigns a Consumer to receive the messages whenever there is one.
   * @param message
   */
  @Autowired
  private PelangganRepository pelangganRepository;
  @RabbitListener(queues = {"update.pelanggan"})
  public void receiveUpdate(byte[] pelanggan){
    String str = new String(pelanggan, StandardCharsets.UTF_8);
    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(",")));
    Pelanggan pelangganData = new Pelanggan();
    
    try {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date date = formatter.parse(myList.get(3));
      Pelanggan existingPelanggan = pelangganRepository.findById(Long.parseLong(myList.get(4))).get();
      existingPelanggan.setName(myList.get(0));
      existingPelanggan.setEmail(myList.get(1));
      existingPelanggan.setAddress(myList.get(2));
      existingPelanggan.setDateBirth(date);
      pelangganRepository.save(existingPelanggan);
    } catch (Exception e) {
        e.printStackTrace();
    }
   
    System.out.println("Received Message:" + myList.get(4));
    System.out.println();
  }

}
