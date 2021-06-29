package io.webBack.ppmtool.services;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.webBack.ppmtool.domain.Backlog;
import io.webBack.ppmtool.domain.ProjectTask;
import io.webBack.ppmtool.repositories.BacklogRepositories;
import io.webBack.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepositories backLogRepo;
	
	@Autowired
	private ProjectTaskRepository proTaskRepo;
	
	
	public ProjectTask addProjectTask(String projectIdentifier , ProjectTask projectTask) {
		
		//Exceptions: Project not found

        //PTs to be added to a specific project, project != null, BL exists
		System.out.println("proIde"+projectIdentifier);
        Backlog backlog = backLogRepo.findByProjectIdentifier(projectIdentifier);
        System.out.println(backlog.getPTSequence()+"bsjhbd");
        //set the bl to pt
        
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer BacklogSequence = backlog.getPTSequence();
        System.out.println(BacklogSequence);
        // Update the BL SEQUENCE
        BacklogSequence=BacklogSequence+1;

        backlog.setPTSequence(BacklogSequence);
        projectTask.setBacklog(backlog);
//        backlog.setProjectTasks(projectTask);;
        
        

        //Add Sequence to Project Task
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //INITIAL priority when priority null

        //INITIAL status when status is null
        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }

        if(projectTask.getPriority()==null){ //In the future we need projectTask.getPriority()== 0 to handle the form
            projectTask.setPriority(3);
        }

        return proTaskRepo.save(projectTask);
	}
}
