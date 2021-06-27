package io.webBack.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.webBack.ppmtool.domain.Backlog;

@Repository
public interface BacklogRepositories extends CrudRepository<Backlog, Long>{

	Backlog findByProjectIdentifier(String identifier);
}
