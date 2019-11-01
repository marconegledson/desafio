package br.com.desafio.restcontroller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.desafio.domain.entities.AbstractPersistable;
import br.com.desafio.services.AbstractService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRestController<T extends AbstractPersistable<PK>, PK extends Serializable> {
	
	/**
	 * Servico generico
	 * @return
	 */
	protected abstract AbstractService<T, PK> getService();

	/**
	 * [GET] Retorna todos os registros de uma determinada entidade
	 * @return uma lista contendo os dados da entidade
	 */
	@GetMapping(value = "/")
	public List<T> getAll() {
		List<T> entities = new ArrayList<>();
		log.debug(">> getAll {}");
		entities = getService().findAll();
		log.debug("<< getAll [entities={}] ", entities);
		return entities;
	}
	
	/**
	 * [GET] Retorna um registro de uma determinada entidade
	 * @param id o id da entidade
	 * @return a entidade
	 */
	@GetMapping("/{id}")
	public T getById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		T entity = getService().findById(id);
		log.debug("<< getById {} {}", id, entity);
		return entity;
	}
	
	/**
	 * [POST] Cria uma entidade
	 * @param entity a entidade no formato json
	 */
	@PostMapping(value = "/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void create(@Valid @RequestBody T entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().save(entity);
		log.debug(" << create entity {} ", entity);
	}
	
	/**
	 * [PUT] Atualiza uma entidade
	 * @param id o id da entidade
	 * @param entity a entidade a ser atualizado em formato json
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void update(@PathVariable(value = "id", required = true) PK id, @Valid @RequestBody T entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().update(id, entity);
		log.debug(" << create entity {} ", entity);
	}
	
	/**
	 * [DELETE] Deleta uma entidade pelo id
	 * @param id o id a ser deletado
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(value = "id", required = true) PK id) {
		log.debug(">> getById {}", id);
		getService().delete(id);
	}

}
	
