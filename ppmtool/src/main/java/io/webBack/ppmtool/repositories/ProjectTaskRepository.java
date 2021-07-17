package io.webBack.ppmtool.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.webBack.ppmtool.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	ProjectTask findByProjectSequence(String sequence);
}
