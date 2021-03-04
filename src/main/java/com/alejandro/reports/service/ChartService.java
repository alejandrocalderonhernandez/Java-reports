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

public class ChartService implements IChartService {
	
	private Connection connection;
	
	private static final String PROPETIES_PATH = "src/main/resources/database.properties";
	private static final String PIE_REPORT_PATH = "src/main/resources/reports/pieChart.jrxml";
	
	public ChartService() {
		this.connection = JDBCUtil.getConnection(PROPETIES_PATH);
	}

	@Override
	public JasperPrint pieChart() {
		try {
			FileInputStream stream = new FileInputStream(PIE_REPORT_PATH);
			JasperReport report = JasperCompileManager.compileReport(stream);
			return
				JasperFillManager.fillReport(report, new HashMap<>(), this.connection);
		} catch (JRException e) {
			System.err.println("Error to create report: " + e.getMessage());
			throw new RuntimeException();
		} catch (FileNotFoundException e) {
			System.err.println("Error to get file: " + e.getMessage());
			throw new RuntimeException();
		}
	}

}
