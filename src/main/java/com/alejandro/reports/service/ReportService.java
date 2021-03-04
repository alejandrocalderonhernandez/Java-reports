package com.alejandro.reports.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alejandro.reports.dao.ActorDao;
import com.alejandro.reports.model.Actor;
import com.alejandro.reports.util.JDBCUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportService implements IReportService {
	
	private Connection connection;
	private ActorDao dao;
	
	private static final String PROPETIES_PATH = "src/main/resources/database.properties";
	private static final String SIMPLE_REPORT_PATH = "src/main/resources/reports/moviesByRating.jrxml";
	private static final String PARAMS_REPORT_PATH = "src/main/resources/reports/moviesFilterByRating.jrxml";
	private static final String PAYMENTS_REPORT_PATH = "src/main/resources/reports/paymentCustomer.jrxml";
	private static final String ACTORS_REPORT_PATH = "src/main/resources/reports/actors.jrxml";
	private static final String KEY_PARAM_RATING = "rating";
	private static final String KEY_PARAM_DATE = "date";
	
	public ReportService() {
		this.connection = JDBCUtil.getConnection(PROPETIES_PATH);
		this.dao = new ActorDao(this.connection);
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

	@Override
	public JasperPrint makeReportOwnConnection() {
		try {
			Set<Actor> actors = this.dao.findAll();
			FileInputStream stream = new FileInputStream(ACTORS_REPORT_PATH);
			JRBeanCollectionDataSource jrBeanCollectionDataSource  = new JRBeanCollectionDataSource(actors);
			JasperReport report = JasperCompileManager.compileReport(stream);
			return
				JasperFillManager.fillReport(report, new HashMap<>(),jrBeanCollectionDataSource);
		} catch (JRException e) {
			System.err.println("Error to create report: " + e.getMessage());
			throw new RuntimeException();
		} catch (FileNotFoundException e) {
			System.err.println("Error to get file: " + e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public JasperPrint makeReportJoin() {
		try {
			FileInputStream stream = new FileInputStream(PAYMENTS_REPORT_PATH);
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
