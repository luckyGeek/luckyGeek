package de.verpeil;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	ConfigurationTest.class,
	FileDownloaderTest.class,
	MainTest.class
})
public class AllTests {

}
