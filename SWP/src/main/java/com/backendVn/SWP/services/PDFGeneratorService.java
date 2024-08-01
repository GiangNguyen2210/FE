
package com.backendVn.SWP.services;

import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.WarrantyCard;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.RequestRepository;
import com.backendVn.SWP.repositories.WarrantyCardRepository;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class PDFGeneratorService {

    private final RequestRepository requestRepository;
    private final RequestOrderRepository requestOrderRepository;
    private final WarrantyCardRepository warrantyCardRepository;

    public PDFGeneratorService(RequestRepository requestRepository, RequestOrderRepository requestOrderRepository,
                               WarrantyCardRepository warrantyCardRepository) {
        this.requestRepository = requestRepository;
        this.requestOrderRepository = requestOrderRepository;
        this.warrantyCardRepository = warrantyCardRepository;
    }
//    List<String> data,
    public void generateWarrantyCertificate(String templatePath, String outputPath, Integer requestId) throws Exception {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        RequestOrder requestOrder = requestOrderRepository.findByRequestID(request)
                .orElseThrow(()-> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));
        WarrantyCard warrantyCard = warrantyCardRepository.findByRequestOrder(requestOrder)
                .orElseThrow(()-> new AppException(ErrorCode.WARRANTY_CARD_NOT_FOUND));

        // Load the PDF template
        ClassPathResource resource = new ClassPathResource(templatePath);
        InputStream templateStream = resource.getInputStream();

        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(templateStream), new PdfWriter(outputPath));
            Document document = new Document(pdfDoc);

            InputStream fontStream = new ClassPathResource("font/Roboto-Black.ttf").getInputStream();
            byte[] fontBytes = fontStream.readAllBytes();
            PdfFont customFont = PdfFontFactory.createFont(fontBytes, "Identity-H");

            addTextAtPosition(document, request.getCustomerID().getCusName(), 430, 433,customFont);
            addTextAtPosition(document, requestOrder.getDesignID().getDesignName(), 415, 410, customFont);
            addTextAtPosition(document, request.getCategory(), 410, 387,customFont);
            addTextAtPosition(document, request.getMaterialID().getMaterialName(), 565, 387,customFont);
            if (request.getMainStone()!=null) {
                addTextAtPosition(document, request.getMainStone().getMaterialName(), 400, 365,customFont);
            }
            if(request.getSubStone()!=null){
                addTextAtPosition(document, request.getSubStone().getMaterialName(), 390, 343,customFont);
            }
            String formattedStartDate = formatInstantToString(String.valueOf(warrantyCard.getCreatedAt()));
            String formattedEndDate = formatInstantToString(String.valueOf(warrantyCard.getEndAt()));
            addTextAtPosition(document, formattedStartDate, 452, 320,customFont);
            addTextAtPosition(document, formattedEndDate, 683, 320,customFont);

            pdfDoc.close();
            System.out.println("PDF filled successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String formatInstantToString(String instantString) {
        Instant instant = Instant.parse(instantString);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.format(formatter);
    }

    private static void addTextAtPosition(Document document, String text, float x, float y,PdfFont font) {
        Paragraph paragraph = new Paragraph(text)
                .setFont(font)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT);

        document.showTextAligned(paragraph, x, y, TextAlignment.LEFT);
    }
}


