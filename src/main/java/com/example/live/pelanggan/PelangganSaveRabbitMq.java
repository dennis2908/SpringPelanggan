package com.example.live.pelanggan;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.example.live.function.SelfCreatedFunction;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.live.constants.PusherConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.io.File;
import org.springframework.core.io.ClassPathResource;
import jakarta.annotation.PostConstruct;
import com.pusher.rest.Pusher;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.live.log.LogData;
import com.example.live.log.LogDataRepository;

@Service
public class PelangganSaveRabbitMq {

  /**
   * Assigns a Consumer to receive the messages whenever there is one.
   * @param message
   */

   private Pusher pusher;
  @Autowired
  private PelangganRepository pelangganRepository;

  private SelfCreatedFunction selfCreatedFunction;

  @Autowired
  private LogDataRepository logDataRepository;

  @PostConstruct
  public void configure() {

    pusher = new Pusher(
				PusherConstants.PUSHER_APP_ID, 
				PusherConstants.PUSHER_APP_KEY, 
				PusherConstants.PUSHER_APP_SECRET
		);

    pusher.setCluster(PusherConstants.PUSHER_CLUSTER_KEY);
    pusher.setEncrypted(true);
  }
  @RabbitListener(queues = {"save.pelanggan"})
  public void receiveMessage(byte[] pelanggan){

    
    String str = new String(pelanggan, StandardCharsets.UTF_8);
    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(",")));
    Pelanggan pelangganData = new Pelanggan();
    
    try {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date date = formatter.parse(myList.get(3));
      pelangganData.setName(myList.get(0));
      pelangganData.setEmail(myList.get(1));
      pelangganData.setAddress(myList.get(2));
      pelangganData.setDateBirth(date);
      pelangganRepository.save(pelangganData);
      pusher.trigger("load_data", "save_data", "trigger_load_data");

      LogData dataLog = new LogData();
      dataLog.setId(selfCreatedFunction.generatedRandomStr());
      dataLog.setOperation("save.data");
      dataLog.setTable("pelanggan");
      logDataRepository.save(dataLog);
      System.out.println(logDataRepository.findAll());
      
    } catch (Exception e) {
        e.printStackTrace();
    }   
        System.out.println("Received Message:" + myList.get(3));
        System.out.println();
      }


}