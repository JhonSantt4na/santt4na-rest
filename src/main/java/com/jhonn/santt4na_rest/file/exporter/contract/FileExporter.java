package com.jhonn.santt4na_rest.file.exporter.contract;

import com.jhonn.santt4na_rest.dataDTO.v1.PersonDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {
	
	Resource exportFile(List<PersonDTO> people) throws Exception;
	Resource exportPerson(PersonDTO person) throws Exception;
}
