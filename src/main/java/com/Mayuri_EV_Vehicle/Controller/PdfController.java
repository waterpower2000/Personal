//package com.Mayuri_EV_Vehicle.Controller;
//
//import java.io.ByteArrayInputStream;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Mayuri_EV_Vehicle.service.PdfService;
//
//
//
//@RestController
//@RequestMapping("/pdf")
//public class PdfController {
//    private PdfService pdfService;
//
//    public PdfController(PdfService pdfService) {
//        this.pdfService = pdfService;
//
//    }
//
//    @GetMapping("/salepdf")
//    public ResponseEntity<InputStreamResource> createSalePdf() {
//        System.out.println("Creating started");
//
//        ByteArrayInputStream pdf = pdfService.createPdf();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Disposition", "attachment; filename=sale.pdf");
//
//        return ResponseEntity
//                .ok()
//                .headers(httpHeaders)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdf));
//    }
//
//    @GetMapping("/purchasepdf")
//    public ResponseEntity<InputStreamResource> createPurchasePdf() {
//        System.out.println("Creating started");
//
//        ByteArrayInputStream pdf = pdfService.createPdf();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-Disposition", "attachment; filename=purchase.pdf");
//
//        return ResponseEntity
//                .ok()
//                .headers(httpHeaders)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdf));
//    }
//
//}