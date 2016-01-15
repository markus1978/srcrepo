package de.hub.srcrepo.emffrag;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

import com.google.common.collect.Lists;

import de.hub.srcrepo.SrcRepoActivator;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

public class EmffragSrcRepo {
	
	public static final List<EPackage> packages = Lists.newArrayList(JavaPackage.eINSTANCE, RepositoryModelPackage.eINSTANCE);
		
	public static JavaPackage configureJavaPackage(boolean withUsages) {	
		JavaPackage emffragJavaPackage = JavaPackage.eINSTANCE;
		org.eclipse.gmt.modisco.java.emf.JavaPackage originalJavaPackage = org.eclipse.gmt.modisco.java.emf.JavaPackage.eINSTANCE;
		
		configureJavaPackage(emffragJavaPackage, withUsages);
		configureJavaPackage(originalJavaPackage, withUsages);
		
		return emffragJavaPackage;
	}

	private static void configureJavaPackage(org.eclipse.gmt.modisco.java.emf.JavaPackage javaPackage, boolean withUsages) {
//		if (!withUsages) {
//			TreeIterator<EObject> eAllContents = javaPackage.eAllContents();
//			while (eAllContents.hasNext()) {
//				EObject next = eAllContents.next();
//				if (next instanceof EReference) {
//					EReference reference = (EReference)next;
//					if (reference.getName().startsWith("usage")) {
//						EReference eOpposite = reference.getEOpposite();
//						if (eOpposite != null) {
//							SrcRepoActivator.INSTANCE.info(reference.getEContainingClass().getName() + "." + reference.getName() 
//									+ "->" + reference.getEOpposite().getEContainingClass().getName() + "." + reference.getEOpposite().getName());
//							eOpposite.setEOpposite(null);
//							reference.setEOpposite(null);								
//						}						
//					}
//				}
//			}
//		}
	}
}
