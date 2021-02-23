package com.alejandro.reports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.alejandro.reports.util.JDBCUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportService implements IReportService {
	
	private Connection connection;
	
	private static final String PROPETIES_PATH = "src/main/resources/database.properties";
	private static final String SIMPLE_REPORT_PATH = "src/main/resources/reports/moviesByRating.jrxml.jrxml";
	private static final String PARAMS_REPORT_PATH = "src/main/resources/reports/moviesFilterByRating.jrxml";
	private static final String KEY_PARAM_RATING = "rating";
	private static final String KEY_PARAM_DATE = "date";
	
	public ReportService() {
		this.connection = JDBCUtil.getConnection(PROPETIES_PATH);
	}
	
	@Override
	public JasperPrint makeReport() {
		try {
			FileInputStream stream = new FileInputStream(SIMPLE_REPORT_PATH);
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

	@Override
	public JasperPrint makeReportFilter(Object filter) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put(KEY_PARAM_RATING, filter);
			params.put(KEY_PARAM_DATE, LocalDate.now().toString());
			FileInputStream stream = new FileInputStream(PARAMS_REPORT_PATH);
			JasperReport report = JasperCompileManager.compileReport(stream);
			return
					JasperFillManager.fillReport(report, params, this.connection);
		} catch (JRException e) {
			System.err.println("Error to create report: " + e.getMessage());
			throw new RuntimeException();
		} catch (FileNotFoundException e) {
			System.err.println("Error to get file: " + e.getMessage());
			throw new RuntimeException();
		}
	}

}
