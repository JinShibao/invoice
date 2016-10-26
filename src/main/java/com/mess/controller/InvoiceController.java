package com.mess.controller;

import com.mess.service.InvoiceService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
        OutputStream os = null;
        try {
            resultFile = invoiceService.invoice(billFile, billOwner, minutes);
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=invoice.xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            os.write(FileUtils.readFileToByteArray(resultFile.getAbsoluteFile()));
            os.flush();
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return "success";
    }

}
