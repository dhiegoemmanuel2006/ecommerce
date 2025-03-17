package com.dhiegoCommerce.EcommerceProject.services;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class GenerateReportService {
    private String url = "jdbc:mysql://localhost:3306/jdbc:mysql://localhost:3306/faculdadescrum";
    private String user = "root";
    private String password = "root";

    private String pdfPath = "C:" + File.separator + "resultados" + File.separator + "relatorio.pdf";

    Logger log =

    public void generateReport() throws SQLException {
        Connection db = DriverManager.getConnection(url, user, password);
    }

}
