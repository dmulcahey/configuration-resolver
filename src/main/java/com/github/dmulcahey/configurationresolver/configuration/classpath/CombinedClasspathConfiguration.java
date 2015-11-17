package com.github.dmulcahey.configurationresolver.configuration.classpath;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration2.ConfigurationConverter;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.interpol.ExprLookup;
import org.apache.commons.configuration2.interpol.Lookup;
import org.apache.commons.configuration2.tree.NodeCombiner;
import org.apache.commons.configuration2.tree.OverrideCombiner;

import com.github.dmulcahey.configurationresolver.configuration.Configuration;
import com.github.dmulcahey.configurationresolver.configuration.ConfigurationDescriptor;
import com.github.dmulcahey.configurationresolver.configuration.FileBasedConfiguration;
import com.github.dmulcahey.configurationresolver.configuration.classpath.util.CommonsConfigurationUtil;
import com.github.dmulcahey.configurationresolver.configuration.lookup.ConfigurationLookup;
import com.github.dmulcahey.configurationresolver.configuration.lookup.ConfigurationLookupResolver;
import com.github.dmulcahey.configurationresolver.configuration.lookup.expression.ExpressionLookupVariableProvider;
import com.github.dmulcahey.configurationresolver.configuration.lookup.expression.ExpressionLookupVariableProviderResolver;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClasspathResource;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class CombinedClasspathConfiguration extends org.apache.commons.configuration2.CombinedConfiguration implements FileBasedConfiguration<ClasspathResource> {
	
	public static final String EXPRESSION_PREFIX = "expr";
	private ConfigurationDescriptor<ClasspathResource> combinedConfigurationDescriptor;
	private Set<String> importedConfigurations = Sets.newHashSet();
	
	public CombinedClasspathConfiguration() {
		this(new OverrideCombiner());
	}

	public CombinedClasspathConfiguration(NodeCombiner nodeCombiner) {
		super(nodeCombiner);
		initializeConfigurationLookups();
	}

	@Override
	public ConfigurationDescriptor<ClasspathResource> getConfigurationDescriptor() {
		return combinedConfigurationDescriptor;
	}

	public void setCombinedConfigurationDescriptor(ConfigurationDescriptor<ClasspathResource> combinedConfigurationDescriptor) {
		this.combinedConfigurationDescriptor = combinedConfigurationDescriptor;
	}

	@Override
	public Properties getAsProperties() {
		return ConfigurationConverter.getProperties(this);
	}

	@Override
	public Properties getAsPrintableProperties() {
		CombinedClasspathConfiguration clone = (CombinedClasspathConfiguration) this.clone();
		clone.getInterpolator().setEnableSubstitutionInVariables(true);
		for(ClasspathResource resource : clone.getConfigurationDescriptor().getResources()){
			if(resource.isSecure()){
				try {
					org.apache.commons.configuration2.Configuration configuration = CommonsConfigurationUtil.buildConfiguration(resource);
					Properties properties = ConfigurationConverter.getProperties(configuration);
					for(Object key : properties.keySet()){
						clone.setProperty((String)key, "[SECURED:VALUE_NOT_SHOWN]");
					}
				} catch (ConfigurationException e) {
					throw new RuntimeException("Unable to clone CombinedClasspathConfiguration: " + getConfigurationDescriptor().getName(), e);
				}
			}
		}
		return ConfigurationConverter.getProperties(clone);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(combinedConfigurationDescriptor.toString());
		if(!importedConfigurations.isEmpty()){
		sb.append("\n\nImported Configurations:\n");
			for(String importedConfiguration : importedConfigurations){
				sb.append(((Configuration<ClasspathResource>)this.getConfiguration(importedConfiguration)).getConfigurationDescriptor().toString());
			}
		}
		sb.append("\n\nProperties:\n");
		sb.append(getAsPrintableProperties().toString());
		sb.append("\n\n");
		return sb.toString();
	}
	
	void importConfiguration(CombinedClasspathConfiguration configuration){
		importedConfigurations.add(configuration.getConfigurationDescriptor().getName());
		this.addConfiguration(configuration, configuration.getConfigurationDescriptor().getName());
	}
	
	private void initializeConfigurationLookups(){
		this.getInterpolator().setEnableSubstitutionInVariables(true);
		Set<ConfigurationLookup> configurationLookups = new ConfigurationLookupResolver().resolve(null);
		Map<String, Lookup> myLookups = Maps.newHashMapWithExpectedSize(configurationLookups.size());
		for(ConfigurationLookup configurationLookup : configurationLookups){
			myLookups.put(configurationLookup.getPrefix(), configurationLookup);
		}
		myLookups.put(EXPRESSION_PREFIX, initializeExpressions());
		this.setPrefixLookups(myLookups);
	}
	
	private ExprLookup initializeExpressions(){
		Set<ExpressionLookupVariableProvider> expressionLookupVariableProviders = new ExpressionLookupVariableProviderResolver().resolve(null);
		ExprLookup.Variables variables = new ExprLookup.Variables();
		for(ExpressionLookupVariableProvider expressionLookupVariableProvider : expressionLookupVariableProviders){
			variables.add(new ExprLookup.Variable(expressionLookupVariableProvider.getPrefix(), expressionLookupVariableProvider.getValueProvider()));
		}
		ExprLookup expressionLookup = new ExprLookup(variables);
		expressionLookup.setInterpolator(this.getInterpolator());
		return expressionLookup;
	}
	
}
