package com.example.live.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.live.pelanggan.Pelanggan;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class HelperPelanggan {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Name", "Email", "Address", "Date Birth" };
  static String SHEET = "Pelanggan";

  public static ByteArrayInputStream pelanggansToExcel(List<Pelanggan> pelanggans) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);

      // Header
      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      int rowIdx = 1;
      for (Pelanggan pelanggan : pelanggans) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(pelanggan.getId());
        row.createCell(1).setCellValue(pelanggan.getName());
        row.createCell(2).setCellValue(pelanggan.getEmail());
        row.createCell(3).setCellValue(pelanggan.getAddress());

        String pattern = "yyyy-MM-dd";

        // Create an instance of SimpleDateFormat used for formatting 
        // the string representation of date according to the chosen pattern
        DateFormat df = new SimpleDateFormat(pattern);   
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        String dateBirthAsString = df.format(pelanggan.getDateBirth());
        row.createCell(4).setCellValue(dateBirthAsString);
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }
}
