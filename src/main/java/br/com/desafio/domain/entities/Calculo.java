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
	
	@NotNull
	@Column(name = "resultado", nullable = false)
	private Integer resultado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Override
	public boolean equals(Object obj) {
		return this.entrada.equals(((Calculo)obj).getEntrada()) && 
				this.concatenador.equals(((Calculo)obj).getConcatenador());
	}
	
	@Override
	public int hashCode() {
		return entrada.hashCode() + concatenador.hashCode();
	}

}
