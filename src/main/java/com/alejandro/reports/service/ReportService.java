package com.alejandro.reports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;

import com.alejandro.reports.util.JDBCUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportService implements IReportService {
	
	private Connection connection;
	
	private static final String PROPETIES_PATH = "src/main/resources/database.properties";
	private static final String JASPER_FILE_PATH = "src/main/resources/movies-simple-report.jrxml";
	
	public ReportService() {
		this.connection = JDBCUtil.getConnection(PROPETIES_PATH);
	}
	
	@Override
	public JasperPrint makeReport() {
		try {
			FileInputStream stream = new FileInputStream(JASPER_FILE_PATH);
			JasperReport jasperReport = JasperCompileManager.compileReport(stream);
			return
				JasperFillManager.fillReport(jasperReport, new HashMap<>(), this.connection);
		} catch (JRException e) {
			System.err.println("Error to create report: " + e.getMessage());
			throw new RuntimeException();
		} catch (FileNotFoundException e) {
			System.err.println("Error to get file: " + e.getMessage());
			throw new RuntimeException();
		}
	}

}
