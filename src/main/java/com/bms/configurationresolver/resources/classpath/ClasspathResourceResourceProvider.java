package com.bms.configurationresolver.resources.classpath;

import java.util.Set;

import com.bms.configurationresolver.resources.ResourceProvider;
import com.bms.configurationresolver.resources.classpath.ClassPath.ResourceInfo;
import com.google.common.collect.Sets;

public class ClasspathResourceResourceProvider implements ResourceProvider<Set<ClasspathResource>> {

	private static final ClassPath CLASSPATH;
	private boolean secure;
	
	static{
		try{
			CLASSPATH = ClassPath.from(Thread.currentThread().getContextClassLoader());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<ClasspathResource> getResources() {
		Set<ClasspathResource> resources = Sets.newHashSet();
		for(ResourceInfo resourceInfo : CLASSPATH.getResources()){
			resources.add(new ClasspathResource(resourceInfo, this));
		}
		return resources;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
}
