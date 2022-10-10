package com.periodical.trots.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.periodical.trots.entities.PeriodicalHasReceiptEntity;
import com.periodical.trots.entities.ReceiptEntity;
import com.periodical.trots.services.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateReportOrder{

    public static final String ORDER_REPORT_PDF = System.getenv("ORDER_REPORT_PDF");

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessListener.class);

    @Autowired
    private ReceiptService receiptService;

    @Scheduled(cron = "00 59 21 * * *", zone = "UTC")
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        Date date;
        String file = ORDER_REPORT_PDF;
        {
            try {
                PdfWriter writer = new PdfWriter(file);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                float [] pointColumnWidths = {150F, 150F, 150F, 150F};
                Table table = new Table(pointColumnWidths);

                date = Date.valueOf(dtf.format(now));
                Paragraph paragraph = new Paragraph("Daily Report about Orders\n"+date);
                document.add(paragraph);

                List<ReceiptEntity> list = receiptService.getReceiptsForDailyOrder(date);

                BigDecimal priceStart;

                List<BigDecimal> prices = new ArrayList<>();
                List<String> periodicals = new ArrayList<>();
                StringBuffer periodical;

                List<PeriodicalHasReceiptEntity> listOfPerHasEnt;

                for (int i = 0; i< list.size(); i++) {
                    BigDecimal priceFinal = BigDecimal.ZERO;
                    listOfPerHasEnt = new ArrayList<>(list.get(i).getReceiptEntities());
                    periodical = new StringBuffer();
                    for (PeriodicalHasReceiptEntity r : listOfPerHasEnt){
                        if(list.indexOf(r)==(listOfPerHasEnt.size()-1)){
                            periodical.append(r.getPeriodical().getSellId());
                        }else {
                            periodical.append(r.getPeriodical().getSellId()).append(", ");
                        }
                        priceStart = r.getPricePerMonth();
                        priceFinal = priceFinal.add(priceStart);
                    }
                    periodicals.add(periodical.toString());
                    prices.add(priceFinal);
                }

                table.addCell(new Cell().add("User ID"));
                table.addCell(new Cell().add("Telephone"));
                table.addCell(new Cell().add("Periodicals ID"));
                table.addCell(new Cell().add("Total price"));


                for (ReceiptEntity r: list){
                    table.addCell(new Cell().add(String.valueOf(r.getUser().getId())));
                    table.addCell(new Cell().add(r.getTelephoneNumber()));
                    table.addCell(new Cell().add(periodicals.get(list.indexOf(r))));
                    table.addCell(new Cell().add(String.valueOf(prices.get(list.indexOf(r)))));
                }

                document.add(table);

                System.out.println("pdfCreate");
                document.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            Mailer.sendMailToAdminReportOrders("headerperiodicalsite@gmail.com", "Daily report about orders", "Daily report");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
