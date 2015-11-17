package com.github.dmulcahey.configurationresolver.configuration.lookup;

import org.apache.commons.configuration2.interpol.Lookup;

public interface ConfigurationLookup extends Lookup {

	String getPrefix();
	
}
