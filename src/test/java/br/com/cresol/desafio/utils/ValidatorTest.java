package br.com.cresol.desafio.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void testValidaEmail() {
		assertTrue(Validator.validaEmail("gustavo@email.com"));
		assertTrue(Validator.validaEmail("gustavo@email.com.br"));
		assertTrue(Validator.validaEmail("gustavo@email.c"));
		assertTrue(Validator.validaEmail("gustavo_@email.c"));
		assertFalse(Validator.validaEmail("@email.com"));
		assertFalse(Validator.validaEmail("gustavo@email..com"));
		assertFalse(Validator.validaEmail("gustavo@email"));
		assertFalse(Validator.validaEmail("gustavo#email.com"));
	}

	@Test
	public void testValidaCPF() {
		assertTrue(Validator.validaCPF("46095280290"));
		assertFalse(Validator.validaCPF("460.95280290"));
		assertFalse(Validator.validaCPF("4609528029"));
		assertFalse(Validator.validaCPF("460952802900"));
		assertFalse(Validator.validaCPF("12345678977"));
	}

}
