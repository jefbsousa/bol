package com.jef.bol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/boleto")
public class BolController {

    @Autowired
    private BolService bolService;

    @GetMapping("/generate")
    public ResponseEntity<String> getPdf() throws IOException {
        bolService.generateHtmlToPdf();
        return new ResponseEntity<>("OK" ,HttpStatus.OK);
    }
}
