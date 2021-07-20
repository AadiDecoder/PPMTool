package io.webBack.ppmtool.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.webBack.ppmtool.domain.Backlog;
import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.services.MapValidationErrorService;
import io.webBack.ppmtool.services.ProjectTaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id) {
		ResponseEntity<?> errorMap = null;
		Map<String, String> erMap = new HashMap<>();
		System.out.println("inside backlog " + result.getErrorCount());
		erMap = mapValidationErrorService.MapValidationService(result);
		System.out.println(errorMap + "ajgsgd");
		if (!erMap.isEmpty())
			return new ResponseEntity<Map<String, String>>(erMap, HttpStatus.BAD_REQUEST);

		ProjectTask bl = projectTaskService.addProjectTask(backlog_id, projectTask);

		return new ResponseEntity<ProjectTask>(bl, HttpStatus.CREATED);

	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
		return projectTaskService.findBacklogById(backlog_id);
	}

	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	//update rest call
	@PostMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask proTask, BindingResult result1,
			@PathVariable String backlog_id, @PathVariable String pt_id) 
	{

		ResponseEntity<?> errorMap1 = null;
		Map<String, String> erMap1 = new HashMap<>();
		erMap1 = mapValidationErrorService.MapValidationService(result1);
		if (!erMap1.isEmpty())
			return new ResponseEntity<Map<String, String>>(erMap1, HttpStatus.BAD_REQUEST);
		
		System.out.println(proTask.getSummary());

		ProjectTask updatedProTask = projectTaskService.updateByProjectSequence(proTask,backlog_id,pt_id);

		return new ResponseEntity<ProjectTask>(updatedProTask, HttpStatus.CREATED);

	}
}
