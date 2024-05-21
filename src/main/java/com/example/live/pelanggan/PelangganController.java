package com.example.live.pelanggan;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate; 
import org.springframework.stereotype.Component; 
import org.springframework.amqp.rabbit.annotation.RabbitListener; 
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.*;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Long;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.example.live.excel.service.ServicePelanggan;
import com.example.live.excel.service.ServiceDPelanggan;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import com.example.live.excel.message.MessagePelanggan;
import com.example.live.excel.helper.HelperDExcelPelanggan;



@RestController
@RequestMapping("/api/pelanggans")
public class PelangganController {

  @Autowired
  private PelangganRepository pelangganRepository;

  @Autowired private ServicePelanggan servicePelanggan;

  private ResponseHandler responseHandler;

  @Autowired private RabbitTemplate rabbitTemplate; 

  @Autowired 
  
  private ServiceDPelanggan serviceDPelanggan;

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

  @GetMapping("/downloadExcel")
  public ResponseEntity<Resource> getFile() {
    String pelanggan = "pelanggan";
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    String filename = pelanggan.concat(timeStamp).concat(".xls");
    InputStreamResource file = new InputStreamResource(servicePelanggan.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
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

    ArrayList<String> pelangganData = new ArrayList<String>();
    pelangganData.add(0, pelanggan.getName());
    pelangganData.add(1, pelanggan.getEmail());
    pelangganData.add(2, pelanggan.getAddress());
    String pattern = "yyyy-MM-dd";

    // Create an instance of SimpleDateFormat used for formatting 
    // the string representation of date according to the chosen pattern
    DateFormat df = new SimpleDateFormat(pattern);   
    // Using DateFormat format method we can create a string 
    // representation of a date with the defined format.
    String dateBirthAsString = df.format(pelanggan.getDateBirth());
    pelangganData.add(3, dateBirthAsString);
    String listString = String.join(",", pelangganData);
    byte[] data = listString.getBytes();

    rabbitTemplate.convertAndSend("pelanggan","save.pelanggan", data);
    return ResponseHandler.generateResponseXX(
      HttpStatus.OK,
      true,
      "Success"
    );
  }

  @PostMapping("/upload")
  public ResponseEntity<MessagePelanggan> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (HelperDExcelPelanggan.hasExcelFormat(file)) {
      try {
        serviceDPelanggan.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new MessagePelanggan(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        System.out.println(e);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessagePelanggan(message));
      }
    }

    message = "Please upload an excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessagePelanggan(message));
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

    ArrayList<String> pelangganData = new ArrayList<String>();
    pelangganData.add(0, pelanggan.getName());
    pelangganData.add(1, pelanggan.getEmail());
    pelangganData.add(2, pelanggan.getAddress());
    String pattern = "yyyy-MM-dd";

    // Create an instance of SimpleDateFormat used for formatting 
    // the string representation of date according to the chosen pattern
    DateFormat df = new SimpleDateFormat(pattern);   
    // Using DateFormat format method we can create a string 
    // representation of a date with the defined format.
    String dateBirthAsString = df.format(pelanggan.getDateBirth());
    pelangganData.add(3, dateBirthAsString);
    String IdStr = Long.toString(id);
    pelangganData.add(4, IdStr);
    String listString = String.join(",", pelangganData);
    byte[] data = listString.getBytes();
    rabbitTemplate.convertAndSend("update.pelanggan","update.pelanggan", data);
    return ResponseHandler.generateResponseXX(
      HttpStatus.OK,
      true,
      "Success"
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
