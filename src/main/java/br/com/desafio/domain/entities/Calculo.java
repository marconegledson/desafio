package br.com.desafio.domain.entities;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_calculo")
@Getter @Setter @NoArgsConstructor
public class Calculo extends AbstractPersistable<Long>{
	
	private static final String SEQUENCE = "calculo_sequence";

	@Id
	@Column(name = "id_resultado")
	@SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
	private Long id;
	
	@NotNull
	@Column(name = "entrada", nullable = false)
	private BigInteger entrada;
	

	@NotNull
	@Column(name = "concatenador", nullable = false)
	private Long concatenador;
	
	@Column(name = "resultado", nullable = false)
	private Integer resultado;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concatenador == null) ? 0 : concatenador.hashCode());
		result = prime * result + ((entrada == null) ? 0 : entrada.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calculo other = (Calculo) obj;
		if (concatenador == null) {
			if (other.concatenador != null)
				return false;
		} else if (!concatenador.equals(other.concatenador))
			return false;
		if (entrada == null) {
			if (other.entrada != null)
				return false;
		} else if (!entrada.equals(other.entrada))
			return false;
		return true;
	}
	
	

}
