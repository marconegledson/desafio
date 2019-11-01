package br.com.desafio.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafio.domain.entities.AbstractPersistable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractService<T extends AbstractPersistable<PK>, PK extends Serializable> {
	
	/**
	 * O repositorio para a consulta no banco
	 * @return o repositorio da entidade
	 */
	protected abstract JpaRepository<T, PK> getRepository();
	
	/**
	 * Verifica se a entidade possui registros na sua base de dados
	 * @return se possui TRUE ou nao FALSE
	 */
	public boolean isEmpty() {
		log.debug(">> isEmpty");
		boolean isEmpty = getRepository().count() == 0;
		log.debug("<< isEmpty [isEmpty={}] ", isEmpty);
		return isEmpty;
	}
	
	/**
	 * Conta quantos registros possui a entidade
	 * @return a quantidade de registros
	 */
	public Long count() {
		log.debug(">> count");
		long count = getRepository().count();
		log.debug("<< count [count={}] ", count);
		return count;
	}

	/**
	 * Busca uma entidade pelo seu id
	 * @param id o id a ser pesquisado
	 * @return a entidade pesquisada
	 */
	public T findById(PK id) {
		log.debug(">> findById [id={}] ", id);
		Optional<T> entity = getRepository().findById(id);
		log.debug("<< findById [id={}, entity={}] ", id, entity);
		return entity.orElseThrow(() -> new EntityNotFoundException());
	}
	
	/**
	 * Busca uma entidade pelo seu id
	 * @param id o id a ser pesquisado
	 * @return a entidade pesquisada (Optional)
	 */
	public Optional<T> getById(PK id) {
		log.debug(">> findById [id={}] ", id);
		Optional<T> entity = getRepository().findById(id);
		log.debug("<< findById [id={}, entity={}] ", id, entity);
		return entity;
	}
	
	/**
	 * Lista todos os registros da entidade
	 * @return a lista contendo os registros da entidade
	 */
	public List<T> findAll(){
		List<T> entities = getRepository().findAll();
		log.debug("<< findAll [entities={}] ", entities);
		return entities;
	}
	
	/**
	 * Salva uma entidade na base de dados
	 * @param entity a entidade a ser salva
	 * @return a entidade salva
	 */
	public T save(T entity) {
		log.debug(">> save [entity={}] ", entity);
		T t = getRepository().save(entity);
		log.debug("<< save [entity={}] ", t);
		return entity;
	}
	
	/**
	 * Atualiza uma determinada entidade
	 * @param id o id da entidade a ser atualizada
	 * @param entity a entidade com os dados a ser atualizado
	 * @return a entidade atualizada
	 */
	public T update(PK id, T entity) {
		log.debug(">> update [entity={}] ", entity);
		T entityInSession = this.findById(id);
		BeanUtils.copyProperties(entity, entityInSession, new String[]{"id"});
		T t = getRepository().save(entityInSession);
		log.debug("<< update [entity={}] ", t);
		return entity;
	}
	
	/**
	 * Remove um entidade pelo id
	 * @param id o id da entidade a ser excluida
	 */
	public void delete(PK id) {
		log.debug(">> delete [id={}] ", id);
		T entity = this.findById(id);
		this.delete(entity);
	}
	
	/**
	 * Remove uma entidade pela entidade passada
	 * @param entity a entidade a ser removida
	 */
	public void delete(T entity) {
		log.debug(">> delete [entity={}] ", entity);
		getRepository().delete(entity);
	}


	
	

}
