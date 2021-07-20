package io.webBack.ppmtool.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import io.webBack.ppmtool.Exception.ProjectIdException;
import io.webBack.ppmtool.Exception.ProjectNotFoundsException;
import io.webBack.ppmtool.Exception.ProjectTaskNotFoundException;
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
		
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

		Integer BacklogSequence = backlog.getPTSequence();

		
		BacklogSequence++;

		backlog.setPTSequence(BacklogSequence);

		System.out.println("setting backlog to projectTask");

		projectTask.setBacklog(backlog);

	
		projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		if (projectTask.getPriority()==0 || projectTask.getPriority() == null) { 
													
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
	
    public ProjectTask findPTByProjectSequence(String backlog_id,String seq) {
    	
    	Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
    	if(backlog==null) {
    		throw new ProjectIdException("Project Identifier '"+ backlog_id.toUpperCase()+"' doesnt exist");
    	}
    	
    	ProjectTask pt=projectTaskRepository.findByProjectSequence(seq);
    	if(pt==null) {
    		throw new ProjectTaskNotFoundException("Project Task with pTSeq: '"+seq+"'does not exist");
    	}
    	
    	
    	 if(!pt.getProjectIdentifier().equals(backlog_id)){
             throw new ProjectNotFoundsException("Project Task ' "+ seq +" ' does not exist in project: ' "+backlog_id);
         }
    	
    	
    	return pt;
    	
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask , String backlog_id , String pt_id) {
//    	ProjectTask proTask = projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());
    	
    	Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
    	if(backlog==null) {
    		throw new ProjectIdException("Project Identifier '"+ backlog_id.toUpperCase()+"' doesnt exist");
    	}
    	ProjectTask proTask=projectTaskRepository.findByProjectSequence(pt_id);
    	if(proTask==null) {
    		throw new ProjectTaskNotFoundException("Project Task with pTSeq: '"+pt_id+" 'does not exist");
    	}
    	
    	
    	 if(!proTask.getProjectIdentifier().equals(backlog_id)){
    		  throw new ProjectNotFoundsException("Project Task ' "+ pt_id +" ' does not exist in project: ' "+backlog_id);
    	 }
    	proTask=updatedTask;
    	
    	return projectTaskRepository.save(proTask);
    }
    
}






























