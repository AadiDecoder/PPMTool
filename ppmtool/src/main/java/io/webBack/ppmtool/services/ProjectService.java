package io.webBack.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.webBack.ppmtool.Exception.ProjectIdException;
import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;
	
	public Project saveOrUpdateProject(Project project)
	{
		try {
//			System.out.println("Saving DATA in TABLE");
//			System.out.print(project.getProjectName());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
