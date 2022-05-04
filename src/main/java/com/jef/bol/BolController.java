package com.jef.bol;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

@RestController
@RequestMapping("/boleto")
public class BolController {

    @Autowired
    private BolService bolService;

//    @GetMapping("/generate")
//    public ResponseEntity<InputStreamResource> getPdf(HttpServletResponse response) throws IOException, TransformerException {
////        bolService.generateHtmlToPdf();
//        byte[] pdfBytes = bolService.generateHtmlToPdf(response);
//        InputStream targetStream = new ByteArrayInputStream(pdfBytes);
//
////        File file = new File(fileName);
////        FileUtils.writeByteArrayToFile(file, pdfBytes); //org.apache.commons.io.FileUtils
//        InputStreamResource resource = new InputStreamResource(targetStream);
//        MediaType mediaType = MediaType.parseMediaType("application/pdf");
//
//        return ResponseEntity.ok()
//                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "XXX.pdf")
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
////                .contentLength(file.length()) //length
//                .body(resource);
//
////        return new ResponseEntity<>( ,HttpStatus.OK);
//
//    }

    @GetMapping("/generate")
    public void getPdf(HttpServletResponse response) throws IOException, TransformerException, ParserConfigurationException, SAXException {



        response.setHeader("Content-Disposition", "inline");

        bolService.generateHtmlToPdf(response);
    }




}
