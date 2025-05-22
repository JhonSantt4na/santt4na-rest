package com.jhonn.santt4na_rest.file.importer.contract;

import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
	
	List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}
