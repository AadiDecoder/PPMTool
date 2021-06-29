package io.webBack.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.repositories.BacklogRepositories;
import io.webBack.ppmtool.services.MapValidationErrorService;
import io.webBack.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
public class BackLogController {
	
	@Autowired
	private ProjectTaskService proTaskService;
	

	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask proTask,
								BindingResult result , @PathVariable String backlog_id )
	{
		System.out.println(proTask.getSummary());
//		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//		System.out.println(errorMap);
//		if(errorMap != null) return errorMap;
		
		System.out.println(result.getFieldError());
		ProjectTask pt=proTaskService.addProjectTask(backlog_id, proTask);
		
		
		
		return new ResponseEntity<ProjectTask>(pt,HttpStatus.CREATED);
	}

}
