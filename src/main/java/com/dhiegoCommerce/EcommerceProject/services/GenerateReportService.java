package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.entities.Product;
import com.dhiegoCommerce.EcommerceProject.repositories.ProductRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.List;

@Service
public class GenerateReportService {
    private final ProductService pService;

    private String pdfPath = "C:" + File.separator + "resultados" + File.separator + "relatorio.pdf";

    Logger log = LoggerFactory.getLogger(GenerateReportService.class);

    public GenerateReportService(ProductService pService) {
        this.pService = pService;
    }

    public void generateReport() throws FileNotFoundException {
        try{
            log.info("Iniciando a geração do pDF");
            List<Product> list = pService.findAllProductsTable();
            if(list == null || list.isEmpty()){
                throw new Exception("Lista vazia");
            }

            log.info("Criando documento!");
            PdfWriter writer =  new PdfWriter(new FileOutputStream(pdfPath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);


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
            document.close();
            log.info("Relatorio gerado com sucesso!");
        } catch (Exception e) {
            log.error("Falha ao gerar relatório: " + e.getMessage());
        }

    }

}
