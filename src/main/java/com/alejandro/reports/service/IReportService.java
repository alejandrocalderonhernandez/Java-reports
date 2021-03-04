package com.alejandro.reports.service;

import net.sf.jasperreports.engine.JasperPrint;

public interface IReportService {

	JasperPrint makeReport();
	JasperPrint makeReportFilter(Object filter);
	JasperPrint makeReportOwnConnection();
	JasperPrint makeReportJoin();
}
