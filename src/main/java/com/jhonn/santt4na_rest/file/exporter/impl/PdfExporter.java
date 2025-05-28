package com.jhonn.santt4na_rest.file.exporter.impl;


import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.file.exporter.contract.FileExporter;
import com.jhonn.santt4na_rest.services.QRCodeService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporter implements FileExporter {
	
	@Autowired
	private QRCodeService service;
	
	
	@Override
	public Resource exportFile(List<PersonDTO> people) throws Exception {
		InputStream inputStream = getClass().getResourceAsStream("/templates/person.jrxml");
		if (inputStream == null) {
			throw new RuntimeException("Template file not found: \"/templates/person.jrxml\"");
		}
		
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(people);
		Map<String, Object> parameters = new HashMap<>();
		//parameters.put("title", "People Report");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters ,dataSource);
		
		try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			return new ByteArrayResource(outputStream.toByteArray());
		}
	}
	
	@Override
	public Resource exportPerson(PersonDTO person) throws Exception {
		InputStream mainTemplateStream = getClass().getResourceAsStream("/templates/person.jrxml");
		if (mainTemplateStream == null) {
			throw new RuntimeException("Template file not found: \"/templates/person.jrxml\"");
		}
		
		InputStream sobReportStream = getClass().getResourceAsStream("/templates/books.jrxml");
		if (sobReportStream == null) {
			throw new RuntimeException("Template file not found: \"/templates/person.jrxml\"");
		}
		
		JasperReport mainReport = JasperCompileManager.compileReport(mainTemplateStream);
		JasperReport subReport = JasperCompileManager.compileReport(sobReportStream);
		
		InputStream qrCodeStream = service.generateQrCode(person.getProfileUrl(), 200,200);
		
		JRBeanCollectionDataSource subDataSource = new JRBeanCollectionDataSource(person.getBooks());
		
		String path = getClass().getResource("/templates/books.jrxml").getPath();
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("SUB_REPORT_DATA_SOURCE", subDataSource);
		parameters.put("BOOK_SUB_REPORT", subReport);
		parameters.put("SUB_REPORT_DIR", path);
		parameters.put("QR_CODEIMAGE", qrCodeStream);
		
		JRBeanCollectionDataSource mainDataSource = new JRBeanCollectionDataSource(Collections.singletonList(person));
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, mainDataSource);
		
		try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			return new ByteArrayResource(outputStream.toByteArray());
		}
	}
	
}
