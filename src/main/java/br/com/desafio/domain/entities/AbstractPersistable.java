package br.com.desafio.domain.entities;

import java.io.Serializable;

public abstract class AbstractPersistable<PK extends Serializable> {

	protected PK id;
	
	public abstract PK getId();
	
	public abstract void setId(PK id);
	
}
