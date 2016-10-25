package com.mess.controller;

import com.mess.service.InvoiceService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

/**
 * Created by jinshibao on 2016/10/14.
 */

@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @RequestMapping("/invoice")
    public String invoice(HttpServletResponse response, MultipartFile billFile, String billOwner, Integer minutes) {
        File resultFile;
        OutputStream outputStream;
        try {
            resultFile = invoiceService.invoice(billFile, billOwner, minutes);
            outputStream = response.getOutputStream();
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            outputStream.write(FileUtils.readFileToByteArray(resultFile.getAbsoluteFile()));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }

}
