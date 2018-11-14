package com.verizon.tsp.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.verizon.tsp.controllers.PlanDetailsController;
import com.verizon.tsp.models.CSA;
import com.verizon.tsp.models.Report;

import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
        int c=0;
      // PlanDetailsController pdc=new PlanDetailsController();
        
        List<Report> reports = (List<Report>) model.get("reports");
        document.add(new Paragraph("Generated Users " + LocalDate.now()));

        List<String> planNames=PlanDetailsController.getPlanNames();
        System.out.println(planNames.size()+ "Size hai ye");
        
        PdfPTable table = new PdfPTable(reports.stream().findAny().get().getColumnCount());
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);
        
      //  System.out.println(planNames.get(0));
        
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Plan Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("January", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Febraury", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("March", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("April", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("May", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("June", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("July", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("August", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("September", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("October", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("November", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("December", font));
        table.addCell(cell);

  
        for(Report report : reports){
        	table.addCell(planNames.get(c));
        	table.addCell(Double.toString(report.getJanuary()));
            table.addCell(Double.toString(report.getFebruary()));
            table.addCell(Double.toString(report.getMarch()));
            table.addCell(Double.toString(report.getApril()));
            table.addCell(Double.toString(report.getMay()));
            table.addCell(Double.toString(report.getJune()));
            table.addCell(Double.toString(report.getJuly()));
            table.addCell(Double.toString(report.getAugust()));
            table.addCell(Double.toString(report.getSeptember()));
            table.addCell(Double.toString(report.getOctober()));
            table.addCell(Double.toString(report.getNovember()));            
            table.addCell(Double.toString(report.getDecember()));
            c=c+1;
        }

        document.add(table);
    }
}
