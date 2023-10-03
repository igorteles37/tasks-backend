package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void deveRetornarTrueParaDataFuturas() {
		LocalDate data = LocalDate.now().plusDays(1);
		assertTrue(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarTrueParaDataIgualAAtual() {
		LocalDate data = LocalDate.now();
		assertTrue(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarFalseParaDataPassadas() {
		LocalDate data = LocalDate.now().minusDays(1);
		assertFalse(DateUtils.isEqualOrFutureDate(data));
	}

}
