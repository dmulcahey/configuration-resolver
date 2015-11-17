package com.github.dmulcahey.configurationresolver.configuration;

import java.util.Set;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;

public interface ConfigurationDescriptor<T extends AbstractResource> {
	
	String getName();
	
	Set<T> getResources();

}
