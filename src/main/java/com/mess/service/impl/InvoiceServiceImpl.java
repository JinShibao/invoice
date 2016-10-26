package com.mess.service.impl;

import com.alibaba.fastjson.JSON;
import com.mess.dto.JourneyDto;
import com.mess.service.InvoiceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jinshibao on 2016/10/14.
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static Logger logger = LogManager.getLogger(InvoiceService.class);

    private static String DEFAULT_REASON = "加班";
    private static Integer DEFAULT_MINUTES = 20;

    @Override
    public File invoice(MultipartFile billFile, String billOwner, Integer minutes) throws Exception {
        minutes = minutes == null ? DEFAULT_MINUTES : minutes;
        List<JourneyDto> journeyDtos = new ArrayList<JourneyDto>();

        File file = new File("/home/jinshibao/var/sync/invoice/" + UUID.randomUUID() + ".pdf");
//        File file = new File("D://test.pdf");
        billFile.transferTo(file);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String documentText = pdfStripper.getText(document);
        logger.info(documentText);
        String[] documentTextArray = documentText.split("\n");
        for (int i = 0; i < documentTextArray.length; i++) {
            logger.info(documentTextArray[i]);
        }
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
        document.close();
        return writeExcel(journeyDtos);
    }

    private File writeExcel(List<JourneyDto> journeyDtos) throws Exception {
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
            logger.info("写入第" + i + "行数据 " + JSON.toJSONString(journeyDto));
        }
        String fileName = "/home/jinshibao/var/sync/invoice/" + UUID.randomUUID() + ".xls";
//        String fileName = "D://result.xls";
        FileOutputStream fout = new FileOutputStream(fileName);
        try {
            workbook.write(fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fout.close();
        }
        File file = new File(fileName);
        return file;
    }
}
