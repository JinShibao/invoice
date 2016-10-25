package com.mess.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by jinshibao on 2016/10/14.
 */

public interface InvoiceService {
    File invoice(MultipartFile billFile, String billOwner, Integer minutes) throws Exception;
}
