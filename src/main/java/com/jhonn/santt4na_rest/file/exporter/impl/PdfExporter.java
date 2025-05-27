package com.jhonn.santt4na_rest.file.exporter.impl;

import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import com.jhonn.santt4na_rest.file.exporter.contract.FileExporter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfExporter implements FileExporter {
	
	@Override
	public Resource exportFile(List<PersonDTO> people) throws Exception {
		return null;
	}
	
}
