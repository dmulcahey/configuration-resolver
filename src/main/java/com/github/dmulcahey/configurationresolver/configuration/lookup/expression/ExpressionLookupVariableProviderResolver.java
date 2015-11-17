package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

import lombok.SneakyThrows;

import com.github.dmulcahey.resolver.AbstractResolver;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;

public class ExpressionLookupVariableProviderResolver extends AbstractResolver<Object, Set<ExpressionLookupVariableProvider>>{

	@Override
	@SneakyThrows
	protected Set<ExpressionLookupVariableProvider> doResolution(Object input) {
		Set<Class<? extends ExpressionLookupVariableProvider>> expressionLookupVariableProviderClasses = getSubTypesOf(ExpressionLookupVariableProvider.class);
		if(expressionLookupVariableProviderClasses == null || expressionLookupVariableProviderClasses.size() == 0){
			return Collections.emptySet();
		}
		Set<ExpressionLookupVariableProvider> expressionLookupVariableProviders = Sets.newHashSetWithExpectedSize(expressionLookupVariableProviderClasses.size());
		for(Class<? extends ExpressionLookupVariableProvider> expressionLookupVariableProviderClass : expressionLookupVariableProviderClasses){
			expressionLookupVariableProviders.add(expressionLookupVariableProviderClass.newInstance());
		}
		return expressionLookupVariableProviders;
	}
	
	@Override
	public Optional<Class<? extends Annotation>> getPostresolutionTestAnnotationClass(){
		return Optional.<Class<? extends Annotation>>of(ExpressionLookupVariableProviderResolverPostresolutionTest.class);
	}

}
