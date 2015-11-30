package com.github.dmulcahey.configurationresolver.resources.classpath;

import java.net.URL;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;

import com.github.dmulcahey.configurationresolver.resources.AbstractResource;
import com.github.dmulcahey.configurationresolver.resources.ResourceProvider;
import com.github.dmulcahey.configurationresolver.resources.classpath.ClassPath.ResourceInfo;

public class ClasspathResource extends AbstractResource {
	
	private ResourceInfo resourceInfo;
	
	public ClasspathResource(){
	}
	
	public ClasspathResource(ResourceInfo resourceInfo, ResourceProvider<?> resourceProvider) {
		super(resourceProvider);
		this.resourceInfo = resourceInfo;
	}

	public ResourceInfo getResourceInfo() {
		return resourceInfo;
	}
	
	public final URL getURL() {
		return resourceInfo.url();
	}

	public final String getResourceName() {
		return resourceInfo.getResourceName();
	}

	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	
	@Override
	public String getName(){
		return resourceInfo.getResourceName().substring(resourceInfo.getResourceName().lastIndexOf("/") + 1);
	}
	
	public boolean isDirectory() throws FileSystemException{
		return VFS.getManager().resolveFile(resourceInfo.url().toExternalForm()).getType().equals(FileType.FOLDER);
	}
	
	public boolean isFile() throws FileSystemException{
		return VFS.getManager().resolveFile(resourceInfo.url().toExternalForm()).getType().equals(FileType.FILE);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceInfo == null) ? 0 : resourceInfo.url().toExternalForm().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (!(obj instanceof ClasspathResource)){
			return false;
		}
		ClasspathResource other = (ClasspathResource) obj;
		if (resourceInfo == null) {
			if (other.resourceInfo != null){
				return false;
			}
		} else if (!resourceInfo.url().toExternalForm().equals(other.resourceInfo.url().toExternalForm())){
			return false;
		}
		return true;
	}
	
}
