package com.example.live.excel.helper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.lang.Long;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Double;

import com.example.live.pelanggan.Pelanggan;

public class HelperDExcelPelanggan {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Name", "Email", "Address", "Date Birth" };
  static String SHEET = "Pelanggan";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<Pelanggan> toExcelPelanggans(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Pelanggan> pelanggans = new ArrayList<Pelanggan>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Pelanggan pelanggan = new Pelanggan();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
          pelanggan.setName(currentCell.getStringCellValue());
            break;

          case 1:
          pelanggan.setEmail(currentCell.getStringCellValue());
            break;

          case 2:
          pelanggan.setAddress(currentCell.getStringCellValue());
            break;

          case 3:
          SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
          try {
            Date date = originalFormat.parse(Double.toString(currentCell.getNumericCellValue()));
            pelanggan.setDateBirth(date);
            break;  

           }
           catch (Exception e) {
            //The handling for the code
           }
           default:
            break;
          }

          cellIdx++;
        }

        pelanggans.add(pelanggan);
      }

      workbook.close();

      return pelanggans;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
