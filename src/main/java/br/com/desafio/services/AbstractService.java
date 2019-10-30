package br.com.desafio.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.domain.entities.AbstractPersistable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractService<T extends AbstractPersistable<PK>, PK extends Serializable> {
	
	protected abstract JpaRepository<T, PK> getRepository();
	
	public boolean isEmpty() {
		log.debug(">> isEmpty");
		boolean isEmpty = getRepository().count() == 0;
		log.debug("<< isEmpty [isEmpty={}] ", isEmpty);
		return isEmpty;
	}
	
	public Long count() {
		log.debug(">> count");
		long count = getRepository().count();
		log.debug("<< count [count={}] ", count);
		return count;
	}

	public T findById(PK id) {
		log.debug(">> findById [id={}] ", id);
		Optional<T> entity = getRepository().findById(id);
		log.debug("<< findById [id={}, entity={}] ", id, entity);
		return entity.orElseThrow(() -> new EntityNotFoundException());
	}
	
	public List<T> findAll(){
		List<T> entities = getRepository().findAll();
		log.debug("<< findAll [entities={}] ", entities);
		return entities;
	}
	
	public T save(T entity) {
		log.debug(">> save [entity={}] ", entity);
		T t = getRepository().save(entity);
		log.debug("<< save [entity={}] ", t);
		return entity;
	}
	
	public void delete(PK id) {
		log.debug(">> delete [id={}] ", id);
		getRepository().deleteById(id);
	}
	
	public void delete(T entity) {
		log.debug(">> delete [entity={}] ", entity);
		getRepository().delete(entity);
	}
	

}
