package io.webBack.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.webBack.ppmtool.Exception.ProjectIdException;
import io.webBack.ppmtool.domain.Backlog;
import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.repositories.BacklogRepositories;
import io.webBack.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
//	private final String identifier="getProjectIdentifier().toUpperCase()";

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private BacklogRepositories backLogRepo;
	
	public Project saveOrUpdateProject(Project project)
	{
		try {
//			System.out.println("Saving DATA in TABLE");
//			System.out.print(project.getProjectName());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				backlog.setPTSequence(0);
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			if(project.getId()!=null) {
				project.setBacklog(backLogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
//			System.out.println(project.getProjectIdentifier());
			return projectRepo.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"'already Exists");
		}
	}
	
	public Project findByProjectIdentifier(String projectId) {
		return projectRepo.findByProjectIdentifier(projectId);
	}
	
	public Iterable<Project> findAll(){
		return projectRepo.findAll();
	}

	public void delete(String projectId) {
		Project project = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) 
		{
			throw new ProjectIdException("Cannot Delete Project Id with  '"+ projectId.toUpperCase()+"', as this doesnt exist");
		} 
		projectRepo.delete(project);
		
	}
}
