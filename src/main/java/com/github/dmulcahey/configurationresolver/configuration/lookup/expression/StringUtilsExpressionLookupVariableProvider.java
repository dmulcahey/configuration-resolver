package com.github.dmulcahey.configurationresolver.configuration.lookup.expression;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsExpressionLookupVariableProvider implements ExpressionLookupVariableProvider {

	@Override
	public String getPrefix() {
		return "String";
	}

	@Override
	public Object getValueProvider() {
		return StringUtils.class;
	}

}
