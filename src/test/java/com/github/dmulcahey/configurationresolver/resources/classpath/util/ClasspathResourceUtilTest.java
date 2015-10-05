package com.github.dmulcahey.configurationresolver.resources.classpath.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.Test;

import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath;
import com.github.dmulcahey.configurationresolver.resources.classpath.util.ClasspathResourceUtil;

public class ClasspathResourceUtilTest {

	@Test
	public void testGetSubdirectoryNamesFromParentDirectory() throws IOException{
		Set<String> subdirectories = ClasspathResourceUtil.getSubdirectoryNamesFromParentDirectory(ClassPath.from(Thread.currentThread().getContextClassLoader()), "ComponentResources");
		Logger.getAnonymousLogger().info(subdirectories.toString());
		assertEquals(1, subdirectories.size());
		assertTrue(subdirectories.contains("Configuration"));
	}
	
}
