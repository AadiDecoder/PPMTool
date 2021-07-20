package io.webBack.ppmtool.web;

import java.security.Principal;
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
import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.repositories.UserRepository;
import io.webBack.ppmtool.services.MapValidationErrorService;
import io.webBack.ppmtool.services.ProjectService;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService proService;

	@Autowired
	private MapValidationErrorService mapValService;

	@PostMapping("/create")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
			Principal principal) {

		System.out.println("inside project create" + principal.getName());
		HashMap errorMap = mapValService.MapValidationService(result);
		System.out.println("inside project create" + principal.getName());

		if (result.hasErrors()) {
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}

		Project pro = proService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);

	}
	//update rest call
		@PostMapping("/update/{pt_id}")
		public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result1,
				@PathVariable String pt_id,Principal prin) 
		{

			ResponseEntity<?> errorMap1 = null;
			Map<String, String> erMap1 = new HashMap<>();
			erMap1 = mapValService.MapValidationService(result1);
			if (!erMap1.isEmpty())
				return new ResponseEntity<Map<String, String>>(erMap1, HttpStatus.BAD_REQUEST);
			
//			System.out.println(project.getSummary());

			Project updatedProject = proService.updateByProjectIdentifier(project,pt_id,prin.getName());

			return new ResponseEntity<Project>(updatedProject, HttpStatus.CREATED);

		}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

		Project project = proService.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project Identifier '" + projectId.toUpperCase() + "' doesnt exist");
		}
		return new ResponseEntity<Project>(project, HttpStatus.FOUND);

	}

	@GetMapping("/allById")
	public Iterable<Project> getAllProjectsById(Principal prin) {
//		System.out.println("Adarsh"+prin.getName());
		return proService.findAllById(prin.getName());
	}

	@GetMapping("/all")
	public Iterable<Project> getAllProjects(Principal prin) {
//		System.out.println("Adarsh"+prin.getName());
		return proService.findAll();

	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId,Principal prin) {

		proService.delete(projectId,prin.getName());
		return new ResponseEntity<String>("Project with ID : " + projectId + " was deleted ", HttpStatus.OK);

	}

}
