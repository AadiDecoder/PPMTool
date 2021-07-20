package io.webBack.ppmtool.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.webBack.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    
	@Query("Select p from Project p where p.projectLeader=?1")
	Iterable<Project> findAllById(String projectLeader);
	
	
	Iterable<Project> findAll();
	
	@Query("Select p from Project p where p.projectIdentifier=?1 and p.projectLeader=?2")
	Project findByProjectIdentifierById(String projectId,String projectLeader);
	
	Project findByProjectIdentifier(String projectId);
}
