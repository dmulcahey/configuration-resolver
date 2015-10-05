package com.github.dmulcahey.configurationresolver.configuration;

import java.util.Properties;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;


public interface FileBasedConfiguration<T extends AbstractResource> extends Configuration<T> {

	/**
	 * Returns the information contained in this Configuration as a Properties object.
	 * @return Properties 
	 */
	Properties getAsProperties();
	
	/**
	 * Returns the information contained in this Configuration as a Properties object that can be displayed without exposing secure information.
	 * @return Properties 
	 */
	Properties getAsPrintableProperties();
	
}
