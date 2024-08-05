//package com.Mayuri_EV_Vehicle.service;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.Mayuri_EV_Vehicle.entity.Sales;
//import com.Mayuri_EV_Vehicle.repository.SalesRepository;
//import com.lowagie.text.Chunk;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
//import com.lowagie.text.Font;
//import com.lowagie.text.FontFactory;
//import com.lowagie.text.Image;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//
//@Service
//public class PdfServiceImpl implements PdfService{
//    private BillService billService;
//    private SalesRepository salesRepository;
////
//    public PdfServiceImpl(BillService billService,SalesRepository salesRepository) {
//        this.billService = billService;
//        this.salesRepository = salesRepository;
//    }
//
//    private Logger logger= LoggerFactory.getLogger(PdfServiceImpl.class);
//    @Override
//    public ByteArrayInputStream createPdf(){
//       logger.info("create pdf starting");
//        String title="Welcome to World";
//        String content="Please don't come!. (i) Keep calm: It is very important to keep calm and analyse the situation. You will need to check whether the incident which has taken place is as serious as it looks. It can also be a one-off incide...\n" +
//                "\n" +
//                "Read more at: ";
//
////       logger.info("Creating PDF for bill: " + bill.getId());
////        String title = "Bill Details For Billing";
////        String content = "Bill Number: " + bill.getBillNumber() + "\n"
////                + "Amount: " + bill.getAmount() + "\n"
////                + "Date: " + bill.getBillDate() +"\n"
////                +"Id: " + bill.getId();
//
//        ByteArrayOutputStream out= new ByteArrayOutputStream();
//        Document document= new Document();
//        try{
//            PdfWriter.getInstance(document,out);
//            document.open();
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);
//            Paragraph titlePara= new Paragraph(title,titleFont);
//            titlePara.setAlignment(Element.ALIGN_CENTER);
//            document.add(titlePara);
//
//            Font paraFont = FontFactory.getFont(FontFactory.HELVETICA,18);
//            Paragraph contentPara= new Paragraph(content,paraFont);
//            contentPara.setAlignment(Element.ALIGN_BOTTOM);
//            document.add(contentPara);
//
//            // Add a table
//            PdfPTable table = new PdfPTable(7); // 3 columns
//            table.setWidthPercentage(100); // Width 100%
//            table.setSpacingBefore(10f); // Space before table
//
//            // Set column widths
//            float[] columnWidths = {2f, 2f, 2f, 2f,2f,2f,2f};
//            table.setWidths(columnWidths);
//
//            String[] headers = {"Sales Order Id", "Item Type", "Item Details", "Date Of Sales","Customer Name","Price per Quantity","Total Amount"};
//
////            List<Sales> all = salesRepository.findAll();
////    		int i=0;
////            for (Sales sale : all) {
////
////            	data[i][0] = sale.getId().toString();
////            	data[i][1] =sale.getEAutoVariantDetails().getEAutoVariants().getEAuto().getVechileType().toString();//
////            	data[i][2] =sale.getEAutoVariantDetails().getEAutoVariants().getVariantName();//
////            	data[i][3] =sale.getSaleDate().toString();//
////            	data[i][4] =sale.getCustomerName();
////            	data[i][5] =sale.getPerQuantityPrice().toString();
////            	data[i][6] =sale.getSales_totalPrice().toString();
////
////    			i++;
////    		}
//
////            PdfPCell cell1 = new PdfPCell(new Paragraph("Column 1"));
////            cell1.setPadding(10);
////            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
////            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
////
////            PdfPCell cell2 = new PdfPCell(new Paragraph("Column 2"));
////            cell2.setPadding(10);
////            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
////            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
////
////            PdfPCell cell3 = new PdfPCell(new Paragraph("Column 3"));
////            cell3.setPadding(10);
////            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
////            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
////
////            // Add cells to the table
////            table.addCell(cell1);
////            table.addCell(cell2);
////            table.addCell(cell3);
//
//            for (String header : headers) {
//                PdfPCell cell = new PdfPCell(new Paragraph(header));
////                cell.setBorderColor(BaseColor.BLACK);
//                cell.setPadding(10);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                table.addCell(cell);
//            }
//            List<Sales> all = salesRepository.findAll();
//            for (Sales sale : all) {
//                 {
//                    PdfPCell cell = new PdfPCell(new Paragraph());
////                    cell.setBorderColor(BaseColor.BLACK);
//                    cell.setPadding(10);
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        			table.addCell(sale.getId().toString());
////        			table.addCell(sale.getEAutoVariantDetails().getEAutoVariants().getEAuto().getVechileType().toString());
////        			table.addCell(sale.getEAutoVariantDetails().getEAutoVariants().getVariantName());
//        			table.addCell(sale.getSaleDate().toString());
//        			table.addCell(sale.getCustomerName());
//        			table.addCell(sale.getPerQuantityPrice().toString());
//        			table.addCell(sale.getSales_totalPrice().toString());
//
//
//
//        	//		sale.getId().toString();
////                	data[i][1] =sale.getEAutoVariantDetails().getEAutoVariants().getEAuto().getVechileType().toString();//
////                	data[i][2] =sale.getEAutoVariantDetails().getEAutoVariants().getVariantName();//
////                	data[i][3] =sale.getSaleDate().toString();//
////                	data[i][4] =sale.getCustomerName();
////                	data[i][5] =sale.getPerQuantityPrice().toString();
////                	data[i][6] =sale.getSales_totalPrice().toString();
//                }
//            }
//
//
//            // Add table to the document
//            document.add(table);
//
//            document.close();
//
//
//        }catch(Exception e){
//           e.printStackTrace();
//        }
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//}
