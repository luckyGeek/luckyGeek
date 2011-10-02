package de.verpeil;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MemoryTest {
	@Test
	public void testParseDate() {
		final Memory memory =  new Memory();
		assertNotNull(memory);
		final String url = memory.getUrl();
		assertNotNull(url);
	}
}
