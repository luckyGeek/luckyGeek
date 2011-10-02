package de.verpeil;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	ImageToPDFConverterTest.class,
	ConfigurationTest.class,
	MemoryTest.class,
	FileDownloaderTest.class,
	MainTest.class
})
public class AllTests {

}
