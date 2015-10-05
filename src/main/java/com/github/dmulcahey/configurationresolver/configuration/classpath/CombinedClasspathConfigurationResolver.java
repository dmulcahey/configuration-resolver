package com.github.dmulcahey.configurationresolver.configuration.classpath;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.MergeCombiner;
import org.apache.commons.configuration2.tree.UnionCombiner;

import com.github.dmulcahey.configurationresolver.configuration.ConfigurationDescriptor;
import com.github.dmulcahey.configurationresolver.configuration.ConfigurationDescriptorResolver;
import com.github.dmulcahey.configurationresolver.configuration.classpath.annotation.CombinedClasspathConfigurationResolverPostresolutionActivity;
import com.github.dmulcahey.configurationresolver.configuration.classpath.annotation.CombinedClasspathConfigurationResolverPostresolutionTest;
import com.github.dmulcahey.configurationresolver.configuration.classpath.annotation.CombinedClasspathConfigurationResolverPreresolutionActivity;
import com.github.dmulcahey.configurationresolver.configuration.classpath.annotation.CombinedClasspathConfigurationResolverPreresolutionTest;
import com.github.dmulcahey.configurationresolver.configuration.classpath.util.CommonsConfigurationUtil;
import com.github.dmulcahey.configurationresolver.resources.ResourceOrdering;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClasspathResource;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class CombinedClasspathConfigurationResolver extends AbstractClasspathConfigurationResolver<CombinedClasspathConfiguration> {

	public CombinedClasspathConfigurationResolver(ConfigurationDescriptorResolver<ClasspathResource> configurationDescriptorResolver) {
		super(configurationDescriptorResolver);
	}

	@Override
	protected CombinedClasspathConfiguration resolveClasspathPropertiesConfiguration(ConfigurationDescriptor<ClasspathResource> configurationDescriptor) throws ConfigurationException {
		List<ClasspathResource> classpathResources = Lists.newArrayList(configurationDescriptor.getResources());
		CombinedClasspathConfiguration combinedClasspathConfiguration = new CombinedClasspathConfiguration();
		combinedClasspathConfiguration.setCombinedConfigurationDescriptor(configurationDescriptor);
		Collections.sort(classpathResources, Collections.reverseOrder(new ResourceOrdering()));
		for(ClasspathResource classpathResource : classpathResources){
			combinedClasspathConfiguration.addConfiguration(CommonsConfigurationUtil.buildConfiguration(classpathResource));
		}
		if(combinedClasspathConfiguration.containsKey(CombinedClasspathConfiguration.USE_UNION_COMBINER)){
			combinedClasspathConfiguration.setNodeCombiner(new UnionCombiner());
		}else if(combinedClasspathConfiguration.containsKey(CombinedClasspathConfiguration.USE_MERGE_COMBINER)){
			combinedClasspathConfiguration.setNodeCombiner(new MergeCombiner());
		}
		return combinedClasspathConfiguration;
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPreresolutionTestAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(CombinedClasspathConfigurationResolverPreresolutionTest.class);
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPostresolutionTestAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(CombinedClasspathConfigurationResolverPostresolutionTest.class);
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPreresolutionActivityAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(CombinedClasspathConfigurationResolverPreresolutionActivity.class);
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPostresolutionActivityAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(CombinedClasspathConfigurationResolverPostresolutionActivity.class);
	}

}
