package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.entities.Movimentation;
import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GenerateReportService {
    private final ProductService pService;
    private final MovimentationService mService;


    Logger log = LoggerFactory.getLogger(GenerateReportService.class);

    public GenerateReportService(ProductService pService, MovimentationService mService) {
        this.pService = pService;
        this.mService = mService;
    }

    public void generateReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");


        try(PdfWriter writer =  new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);)
        {
            log.info("Iniciando a geração do PDF das movimentações");
            List<Product> list = pService.findAllProductsTable();
            if(list == null || list.isEmpty()){
                throw new Exception("Lista vazia");
            }

            log.info("Criando documento!");



            Paragraph titulo = new Paragraph("Relatório de Produtos")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(18)
                    .setBold();
            document.add(titulo);

            document.add(new Paragraph("\n"));


            log.info("Gerando a tabela!");
            Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();

            table.addHeaderCell("id");
            table.addHeaderCell("name");
            table.addHeaderCell("description");
            table.addHeaderCell("price");
            table.addHeaderCell("quantity");
            table.addHeaderCell("valor total");

            for(Product product : list){
                table.addCell(String.valueOf(product.getId()));
                table.addCell(product.getName());
                table.addCell(product.getDescription());
                table.addCell(String.format("R$ %.2f", product.getPrice()));
                table.addCell(String.valueOf(product.getQuantity()));
                table.addCell(String.format("R$ %.2f", product.getQuantity() * product.getPrice()));
            }

            document.add(table);
            response.getOutputStream().flush();
            log.info("Relatorio de produtos gerado com sucesso!");
        } catch (Exception e) {
            log.error("Falha ao gerar relatório de produtos: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o relatório de produtos");
        }

    }

    public void generateReportForMovimentation(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");


        try(PdfWriter writer =  new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);)
        {
            log.info("Iniciando a geração do PDF das movimentações");
            List<Movimentation> list = mService.getAll();
            if(list == null || list.isEmpty()){
                throw new Exception("Lista vazia");
            }

            log.info("Criando documento para as movimentações!");



            Paragraph titulo = new Paragraph("Relatório das movimentações")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(18)
                    .setBold();
            document.add(titulo);

            document.add(new Paragraph("\n"));


            log.info("Gerando a tabela!");
            Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

            table.addHeaderCell("id");
            table.addHeaderCell("name_produto");
            table.addHeaderCell("quantidade");
            table.addHeaderCell("horário");


            for(Movimentation m : list){
                table.addCell(String.valueOf(m.getId()));
                table.addCell(m.getNomeProduto());
                table.addCell(String.valueOf(m.getQuantityOfProduct()));
                table.addCell(String.valueOf(m.getHorarioOperacao()));

            }

            document.add(table);
            response.getOutputStream().flush();
            log.info("Relatorio de movimentações gerado com sucesso!");
        } catch (Exception e) {
            log.error("Falha ao gerar relatório de movimentações: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o relatório de movimentações");
        }

    }
}
