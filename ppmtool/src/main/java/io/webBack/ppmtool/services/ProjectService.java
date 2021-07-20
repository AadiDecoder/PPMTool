package io.webBack.ppmtool.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.webBack.ppmtool.Exception.ProjectIdException;
import io.webBack.ppmtool.Exception.ProjectNotFoundsException;
import io.webBack.ppmtool.domain.Backlog;
import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.domain.User;
import io.webBack.ppmtool.repositories.BacklogRepositories;
import io.webBack.ppmtool.repositories.ProjectRepository;
import io.webBack.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {
	
//	private final String identifier="getProjectIdentifier().toUpperCase()";

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private BacklogRepositories backLogRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Project saveOrUpdateProject(Project project ,String username)
	{
		 try{
			    System.out.println("inside projectService and about to fetch");
			    User user = userRepo.findByUsername(username);
			    user.getProjects();
	            project.setUser(user);
	            project.setProjectLeader(user.getUsername());
	            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

	            if(project.getId()==null){
	                Backlog backlog = new Backlog();
	                project.setBacklog(backlog);
	                backlog.setProject(project);
	                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
	            }

	            if(project.getId()!=null){
	                project.setBacklog(backLogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
	            }

	            return projectRepo.save(project);
	        }catch (Exception e){
	            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
	        }

	}
	
	public Project findByProjectIdentifier(String projectId) {
		return projectRepo.findByProjectIdentifier(projectId);
	}
	
	public Iterable<Project> findAllById(String name){
		return projectRepo.findAllById(name);
	}
	
	public Iterable<Project> findAll(){
		return projectRepo.findAll();
	}

	public void delete(String projectId,String username) {
		Project project = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) 
		{
			throw new ProjectIdException("Cannot Delete Project Id with  '"+ projectId.toUpperCase()+"', as this doesnt exist");
		} 
		if(!project.getProjectLeader().equals(username))
			throw new ProjectNotFoundsException("Project Not found in your Account");
		projectRepo.delete(project);
		
	}

	public Project updateByProjectIdentifier(@Valid Project updateProject, String pt_id, String name) {
		
		Project pro = projectRepo.findByProjectIdentifier(pt_id);
		if(pro==null) {
			throw new ProjectNotFoundsException("Project doesnt exists with PID "+pt_id);
		}
		if(!pro.getProjectLeader().equals(name))
			throw new ProjectNotFoundsException("Project Not found in your Account");
		pro=updateProject;
		
		return projectRepo.save(pro);
	}
}
