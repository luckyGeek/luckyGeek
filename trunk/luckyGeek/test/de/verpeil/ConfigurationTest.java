package de.verpeil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testConfiguration() {
		assertEquals("http://geekandpoke.typepad.com/geekandpoke/atom.xml",	Configuration.getDownloadUrl());
		assertEquals("all.pdf", Configuration.getAllFile());
		assertEquals("current.jpg", Configuration.getLastImage());
		assertEquals("last.pdf", Configuration.getLastFile());
		assertEquals("atom.xml", Configuration.getTempXmlName());
		assertEquals(0.25f, Configuration.getScaleFactor(), 0.00001);
	}
}
