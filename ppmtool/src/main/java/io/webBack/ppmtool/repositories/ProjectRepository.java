package io.webBack.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.webBack.ppmtool.domain.Project;
import io.webBack.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Iterable<Project> findAll();
	
	Project findByProjectIdentifier(String projectId);
}
