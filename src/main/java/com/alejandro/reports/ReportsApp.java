package com.alejandro.reports;

import java.io.FileNotFoundException;

import com.alejandro.reports.service.IReportService;
import com.alejandro.reports.service.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;


public class ReportsApp {

	private static final String SAVE_PATH = "/home/alejandro/Descargas/my-report.pdf";
	
	public static void main(String[] args) throws FileNotFoundException, JRException {
		IReportService service = new ReportService();
		JasperExportManager.exportReportToPdfFile(service.makeReport(), SAVE_PATH);
	}
}
