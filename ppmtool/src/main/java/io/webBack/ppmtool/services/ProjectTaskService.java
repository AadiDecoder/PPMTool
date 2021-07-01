package io.webBack.ppmtool.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import io.webBack.ppmtool.Exception.ProjectNotFoundsException;
import io.webBack.ppmtool.domain.Backlog;
import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.repositories.BacklogRepositories;
import io.webBack.ppmtool.repositories.ProjectRepository;
import io.webBack.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepositories backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) 
	{
       try {
		// Exceptions: Project not found
		// custom exception handler for hadnling null project identifier in database;
		/*
		 * { "Project Not Found" : "Project Not Found" }
		 */

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

		// set the bl to pt

		// we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101

		Integer BacklogSequence = backlog.getPTSequence();

		// Update the BL SEQUENCE
		BacklogSequence++;

		backlog.setPTSequence(BacklogSequence);

		System.out.println("setting backlog to projectTask");

		projectTask.setBacklog(backlog);

		// Add Sequence to Project Task
		projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		// INITIAL priority when priority null

		// INITIAL status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		if (projectTask.getPriority() == null) { // In the future we need projectTask.getPriority()== 0 to handle the
													// form
			projectTask.setPriority(3);
		}
		backlog.getProjectTasks().add(projectTask);

		backlogRepository.save(backlog);
		return projectTask;
	}
       catch (Exception e) {
		throw new ProjectNotFoundsException("Project not Found");
	}
	}

	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		
	
	Project project = projectRepository.findByProjectIdentifier(backlog_id);
	
		if(project==null) {
			throw new ProjectNotFoundsException("Project with ID: '"+backlog_id+"' does not exist");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);

	}

//	public List<ProjectTask> findBacklogById(String backlog_id) {
//		
//		return null;
//	}
}