package br.com.desafio.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter @Setter @NoArgsConstructor
public class Usuario extends AbstractPersistable<Long>{
	
	private static final String SEQUENCE = "usuario_sequence";

	@Id
	@Column(name = "id_usuario")
	@SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
	private Long id;
	
	@NotBlank(message = "O nome não pode estar vazio")
	@Column(name = "nome", length = 2048, nullable = false)
	private String nome;
	
	@NotBlank(message = "O email não pode estar vazio")
	@Column(name = "email", length = 2048, nullable = false)
	private String email;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Calculo> calculos = new ArrayList<>();

}
