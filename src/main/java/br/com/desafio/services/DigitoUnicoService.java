package br.com.desafio.services;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DigitoUnicoService {
	
	private void validarDigitoUnico(BigInteger entrada, Long concatenador) {
		if(Objects.isNull(entrada)) {
			throw new IllegalArgumentException("Entrada invalida nao deve ser nulo ");
		}
		if(Objects.isNull(concatenador)) {
			throw new IllegalArgumentException("Concatenador invalido nao deve ser nulo ");
		}
		
		if(entrada.compareTo(BigInteger.valueOf(0L)) < 0 ) {
			throw new IllegalArgumentException("Entrada invalida deve ser 1 <= numero informado ");
		}
		if(concatenador.longValue() < 0L ) {
			throw new IllegalArgumentException("Concatenador invalido deve ser 1 <= concatenador informado ");
		}
		
//		validacao desnecessaria o biginteger teoricamente nao possui tamanho e o tamanho e o limite
//		da memoria. como 10 ^ 1000000 e um numero infinito nao pode ser validado
//		if(entrada.compareTo(BigDecimal.valueOf(Math.pow(10, 1000000)).toBigInteger()) > 0 ) {
//			throw new IllegalArgumentException("Entrada invalida deve ser 1 < 10 ^ 1000000");
//		}
		
		if(concatenador.compareTo(Double.valueOf(Math.pow(10, 5)).longValue()) > 0 ) {
			throw new IllegalArgumentException("Concatenador invalid0 deve ser 1 <  10 ^ 5 ");
		}
		
	}
	
	
	private Integer calcularDigitoUnico(BigInteger entrada) {
		log.debug(">> calcularDigitoUnico [entrada={}]", entrada);
		
		int resultado = 0;
		
		char[] characters = entrada.toString().toCharArray();
		List<Integer> numeros = IntStream.range(0, characters.length)
											.mapToObj(i -> Integer.valueOf(String.valueOf(characters[i])))
											.collect(Collectors.toList());
		Collections.reverse(numeros);
		
		resultado = numeros
						.stream()
						.mapToInt(Integer::intValue)
						.sum();
		
		log.debug("<< calcularDigitoUnico [resultado={}]", resultado);
		return resultado;
	}

	
	public Integer calcularDigitoUnico(BigInteger entrada, Long concatenador) {
		log.debug(">> calcularDigitoUnico [entrada={}, concatenador={}]", entrada, concatenador);
		
		validarDigitoUnico(entrada, concatenador);
		StringBuilder numero = new StringBuilder();
		LongStream.range(0, concatenador).forEach(loop -> numero.append(entrada.toString()));
		long resultado = Long.valueOf(numero.toString());
		
		do {
			resultado = calcularDigitoUnico(BigInteger.valueOf(resultado));
		} while (resultado > 10);
		
		log.debug("<< calcularDigitoUnico [resultado={}]", resultado);
		return (int) resultado;
	}

}
