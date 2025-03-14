package com.dhiegoCommerce.EcommerceProject.services;

import com.dhiegoCommerce.EcommerceProject.entities.Product;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {

    public final String relatorioPath = "classpath:jasper/relatorios/";
    public final String arquivoJRXMLPath = "relatorio.jrxml";
    public final Logger logger = LoggerFactory.getLogger(JasperReportService.class);

    public static final String destinoPDF = "c:" + File.separator + "resultados" + File.separator;

    public void gerarRelatorio(List<Product> produtos) throws FileNotFoundException {
        logger.info("Iniciando geração de relatório...");

        Map<String, Object> map = new HashMap<>();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(produtos);

        String pathAbsolut = getAbsolutPath();
        logger.info("Caminho do arquivo .jrxml: {}", pathAbsolut);

        try{
            String localDirectory = getDiretorioSave("Relatórios-gerados" );
            logger.info("Caminho de destino do PDF: {}", localDirectory);

            JasperReport report = JasperCompileManager.compileReport(pathAbsolut);
            JasperPrint print = JasperFillManager.fillReport(report, map, dataSource);
            JasperExportManager.exportReportToPdfFile(print, localDirectory);

            logger.info("Relatório gerado com sucesso em: {}", localDirectory);
        }  catch (JRException e) {
            logger.error("Erro ao gerar relatório");
        throw new RuntimeException(e);
    }
    }

    private String getDiretorioSave(String name) {
        this.createDiretorio(destinoPDF);
        return destinoPDF + name.concat(".pdf");
    }

    private void createDiretorio(String name) {
        File dir = new File(name);
        if (!dir.exists()) {
            boolean criado = dir.mkdir();
            if (!criado) {
                throw new RuntimeException("Não foi possível criar o diretório: " + name);
            }
        }
    }

    public String getAbsolutPath () throws FileNotFoundException {
        return ResourceUtils.getFile(relatorioPath + arquivoJRXMLPath).getAbsolutePath();
    }

}
