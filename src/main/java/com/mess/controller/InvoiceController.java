package com.mess.controller;

import com.mess.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Created by jinshibao on 2016/10/14.
 */

@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @RequestMapping("/invoice")
    public void invoice(String billOwner, Integer minutes) {
        invoiceService.invoice(billOwner, minutes);
    }

    @RequestMapping("/test")
    public void test(File file){
        System.out.println(file.toString());
    }
}
