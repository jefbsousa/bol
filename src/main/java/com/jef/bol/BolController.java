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

import javax.xml.transform.TransformerException;
import java.io.*;

@RestController
@RequestMapping("/boleto")
public class BolController {

    @Autowired
    private BolService bolService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> getPdf() throws IOException, TransformerException {
//        bolService.generateHtmlToPdf();

        byte[] pdfBytes = bolService.generateHtmlToPdf();
        InputStream targetStream = new ByteArrayInputStream(pdfBytes);

//        File file = new File(fileName);
//        FileUtils.writeByteArrayToFile(file, pdfBytes); //org.apache.commons.io.FileUtils
        InputStreamResource resource = new InputStreamResource(targetStream);
        MediaType mediaType = MediaType.parseMediaType("application/pdf");

        HttpHeaders headers = new HttpHeaders();
//        headers.add("content-disposition", "attachment; filename=" + "XXX.pdf");
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + "XXX.pdf");


//        return ResponseEntity.ok()
//                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "XXX.pdf")
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
////                .contentLength(file.length()) //length
//                .body(resource);

        return new ResponseEntity<byte[]>(
                pdfBytes, headers, HttpStatus.OK);

//        return new ResponseEntity<>( ,HttpStatus.OK);

    }
}
