package com.bms.configurationresolver.resources.classpath.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Set;

import lombok.SneakyThrows;

import org.junit.Test;

import com.bms.configurationresolver.resources.classpath.ClassPath;
import com.bms.configurationresolver.resources.classpath.ClasspathResource;
import com.bms.configurationresolver.resources.classpath.FilteredClasspathResourceResourceProvider;
import com.bms.configurationresolver.resources.classpath.filter.DirectoryFilter;
import com.bms.configurationresolver.resources.classpath.filter.PathFilter;

public class DirectoryFilterTest {

	@Test
	public void testAcceptPath(){
		ClasspathResource resource = getResource();
		URL url = resource.getURL();
		String urlString = url.toExternalForm();
		String componentResourcesDirectory = urlString.substring(0, urlString.indexOf("ComponentResources") + "ComponentResources".length());
		assertTrue(new DirectoryFilter().accept(componentResourcesDirectory));
	}
	
	@Test
	public void testRejectPath(){
		assertFalse(new DirectoryFilter().accept(getResource().getURL().toExternalForm()));
	}
	
	@SneakyThrows
	private ClasspathResource getResource(){
		ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
		FilteredClasspathResourceResourceProvider resourcesProvider = FilteredClasspathResourceResourceProvider.builder()
				.withResourceFilter(new PathFilter("ComponentResources/Configuration/junk.properties"))
				.withClassPath(classPath)
				.build();
		Set<ClasspathResource> resources = resourcesProvider.getResources();
		return resources.iterator().next();
	}
	
}
