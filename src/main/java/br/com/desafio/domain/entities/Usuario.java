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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	
	@Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres ")
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;
	
	@NotBlank
	@Email( message = "O nome deve ter entre 5 e 50 caracteres ")
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Calculo> calculos = new ArrayList<>();

}
