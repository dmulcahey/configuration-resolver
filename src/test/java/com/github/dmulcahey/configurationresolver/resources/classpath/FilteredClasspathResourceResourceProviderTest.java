package com.github.dmulcahey.configurationresolver.resources.classpath;

import java.util.logging.Logger;

import lombok.SneakyThrows;

import org.junit.Test;

import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath;
import com.github.dmulcahey.configurationresolver.resources.classpath.FilteredClasspathResourceResourceProvider;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.ExtensionFilter;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.NotFilter;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.PathFilter;

public class FilteredClasspathResourceResourceProviderTest {

	
	@Test
	@SneakyThrows
	public void testFilteredClasspathResourceResourceProvider(){
		ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
		
		FilteredClasspathResourceResourceProvider classpathResourceResourceProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(100)
			.withResourceFilter(new PathFilter("ComponentResources"))
			.withClassPath(classPath)
			.build();
		
		Logger.getAnonymousLogger().info(classpathResourceResourceProvider.toString());
		
		Logger.getAnonymousLogger().info(classpathResourceResourceProvider.getResources().toString());
		
		classpathResourceResourceProvider = FilteredClasspathResourceResourceProvider.builder()
		.order(100)
		.withResourceFilter(new PathFilter("ComponentResources"))
		.withResourceFilter(new NotFilter(new PathFilter("EnvironmentOverrides")))
		.withClassPath(classPath)
		.build();
		
		Logger.getAnonymousLogger().info(classpathResourceResourceProvider.getResources().toString());
		
		classpathResourceResourceProvider = FilteredClasspathResourceResourceProvider.builder()
		.order(100)
		.withResourceFilter(new PathFilter("ComponentResources"))
		.withResourceFilter(new NotFilter(ExtensionFilter.PROPERTIES_FILTER))
		.withClassPath(classPath)
		.build();
		
		Logger.getAnonymousLogger().info(classpathResourceResourceProvider.getResources().toString());
	}
	
}
