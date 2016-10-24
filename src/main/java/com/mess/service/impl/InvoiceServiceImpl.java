package com.mess.service.impl;

import com.mess.dto.JourneyDto;
import com.mess.service.InvoiceService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jinshibao on 2016/10/14.
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static String DEFAULT_BILL_OWNER = "报销人";
    private static String DEFAULT_REASON = "加班";
    private static Integer DEFAULT_MINUTES = 20;

    @Override
    public void invoice(String billOwner, Integer minutes) {
        billOwner = billOwner == null ? DEFAULT_BILL_OWNER : billOwner;
        minutes = minutes == null ? DEFAULT_MINUTES : minutes;
        try {
            PDDocument document = PDDocument.load(new File("D://滴滴出行行程报销单2.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String documentText = pdfStripper.getText(document);
            List<JourneyDto> journeyDtos = new ArrayList<JourneyDto>();
            String[] documentTextArray = documentText.split("\r\n");
            for (String journeyStr : documentTextArray) {
                if (journeyStr.substring(0, 1).matches("^[0-9]*$")) {
                    String[] journeyArray = journeyStr.split(" ");
                    if (journeyArray[3].substring(0, 2).matches("^2[1-4]*$")) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        Date date = format.parse(journeyArray[3]);
                        date.setTime(date.getTime() + minutes * 60 * 1000);
                        String endTime = format.format(date);

                        JourneyDto journeyDto = new JourneyDto(journeyArray[2], journeyArray[3], endTime, DEFAULT_REASON, journeyArray[6],
                                journeyArray[7], journeyArray[9], "1", billOwner, "");
                        journeyDtos.add(journeyDto);
                    }
                }
            }
            writeExcel(journeyDtos);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeExcel(List<JourneyDto> journeyDtos) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        int i = 0;
        for (JourneyDto journeyDto : journeyDtos) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(journeyDto.getDate());
            row.createCell(1).setCellValue(journeyDto.getStartTime() + "-" + journeyDto.getEndTime());
            row.createCell(2).setCellValue(journeyDto.getReason());
            row.createCell(3).setCellValue(journeyDto.getStartPlace());
            row.createCell(4).setCellValue(journeyDto.getEndPlace());
            row.createCell(5).setCellValue(journeyDto.getMoney());
            row.createCell(6).setCellValue(journeyDto.getBillNum());
            row.createCell(7).setCellValue(journeyDto.getBillOwner());
            row.createCell(8).setCellValue(journeyDto.getRemark());
            i++;
        }
        FileOutputStream fout = new FileOutputStream("D://journeyDtos.xls");
        workbook.write(fout);
        fout.close();
    }
}
