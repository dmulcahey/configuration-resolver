package com.github.dmulcahey.configurationresolver.resources.classpath.filter;

public interface ResourceFilter {

	boolean accept(String resourcePath);
	
}
