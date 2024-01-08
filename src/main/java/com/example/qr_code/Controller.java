package com.example.qr_code;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/qr-code")
    public ResponseEntity<InputStreamResource> getQRCode(@RequestBody Info info) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qr-code.png");
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.IMAGE_PNG)
                .body(QrCodeGenerator.getQRCode(info));
    }
}
