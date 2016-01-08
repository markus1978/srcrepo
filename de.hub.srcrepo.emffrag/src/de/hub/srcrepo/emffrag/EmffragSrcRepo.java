package de.hub.srcrepo.emffrag;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import com.google.common.collect.Lists;

import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class EmffragSrcRepo {
	public static final List<EPackage> packages = Lists.newArrayList(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE);
}
