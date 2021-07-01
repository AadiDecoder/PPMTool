package io.webBack.ppmtool.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.webBack.ppmtool.Exception.ProjectIdException;
import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.services.MapValidationErrorService;
import io.webBack.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService proService;
	
	@Autowired
	private MapValidationErrorService mapValService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project , BindingResult result)
	{
		HashMap errorMap= mapValService.MapValidationService(result);
         if(result.hasErrors()) 
         {
        	 return new ResponseEntity<Map<String,String>>( errorMap ,HttpStatus.BAD_REQUEST);
         }
         
		Project pro=proService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project , HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId){
		
		Project project = proService.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) 
		{
			throw new ProjectIdException("Project Identifier '"+ projectId.toUpperCase()+"' doesnt exist");
		}
		return new ResponseEntity<Project>(project , HttpStatus.FOUND);
		
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(){
		return proService.findAll();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId) {
		
		proService.delete(projectId);
		return new ResponseEntity<String>("Project with ID : "+ projectId +" was deleted ", HttpStatus.OK);
				
	}
	
	
}
