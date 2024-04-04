package com.cbl.encryptdycript.controller;

import com.cbl.encryptdycript.ResponseHandler;
import com.cbl.encryptdycript.response.ResponseDto;
import com.cbl.encryptdycript.service.EncryptionDecryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/back-service")
public class ApiController {

    private final EncryptionDecryptionService encryptionDecryptionService;

    @PostMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStatus() {
        try {
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, null);

        } catch (Exception ex) {
            return ResponseHandler.generateResponse("Request process Failed", HttpStatus.NOT_FOUND, null);

        }
    }

    @PostMapping("/encryption")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> dataEncryption(@RequestParam(value = "data",required = false) String data) {
        try {
            ResponseDto response = encryptionDecryptionService.dataEncryption(data);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);

        } catch (Exception ex) {
            return ResponseHandler.generateResponse("Request process Failed. " + ex.getMessage(), HttpStatus.NOT_FOUND, null);

        }
    }

    @PostMapping("/decryption")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> dataDecryption(@RequestParam(value = "data") String data) {
        try {
            ResponseDto response = encryptionDecryptionService.dataDecryption(data);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse("Request process Failed. "+ex.getMessage(), HttpStatus.NOT_FOUND, null);

        }
    }
}
