package com.github.dmulcahey.configurationresolver.resources.classpath.util;

import java.io.IOException;
import java.util.Set;

import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClasspathResource;
import com.github.dmulcahey.configurationresolver.resources.classpath.FilteredClasspathResourceResourceProvider;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath.ResourceInfo;
import com.github.dmulcahey.configurationresolver.resources.classpath.filter.PathFilter;
import com.github.dmulcahey.configurationresolver.resources.util.ResourceInfoUtil;
import com.google.common.collect.Sets;

public class ClasspathResourceUtil {
	
	private ClasspathResourceUtil(){}

	public static Set<String> getSubdirectoryNamesFromParentDirectory(ClassPath classPath, final String parentDirectoryName) throws IOException{
		FilteredClasspathResourceResourceProvider classpathResourceResourceProvider = FilteredClasspathResourceResourceProvider.builder()
			.order(100)
			.withResourceFilter(new PathFilter(parentDirectoryName))
			.withClassPath(classPath)
			.build();
		Set<ClasspathResource> resources = classpathResourceResourceProvider.getResources();
		Set<ResourceInfo> resourceInfos = Sets.newHashSetWithExpectedSize(resources.size());
		for(ClasspathResource resource : resources){
			resourceInfos.add(resource.getResourceInfo());
		}
		return ResourceInfoUtil.getSubdirectoryNamesFromResourceInfo(resourceInfos, parentDirectoryName);
	}
}
