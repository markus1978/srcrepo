/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sebastien Minguet (Mia-Software) - initial API and implementation
 *    Frederic Madiot (Mia-Software) - initial API and implementation
 *    Fabien Giquel (Mia-Software) - initial API and implementation
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Erwan Breton (Sodifrance) - initial API and implementation
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/

package de.hub.srcrepo.modisco;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.internal.util.JavaUtil;

/**
 * The aim of this class is to bind method with the method overriden of a super
 * class if any
 */
public final class SrcRepoMethodRedefinitionManager {

	private SrcRepoMethodRedefinitionManager() {
		// Nothing
	}

	/**
	 * consolidates the model with redefinitions links between methods
	 * 
	 * @param model
	 * @param facto
	 */
	public static void resolveMethodRedefinitions(final EObject model, final JavaFactory facto) {
		Object item;
		for (Iterator<?> i = model.eAllContents(); i.hasNext();) {
			item = i.next();

			if (item instanceof MethodDeclaration) {
				MethodDeclaration aMethod = (MethodDeclaration) item;
				if (!aMethod.isProxy()) {
					String signature = getRawSignature(aMethod);

					TypeAccess superClassRef = null;
					AbstractTypeDeclaration declaringType = aMethod.getAbstractTypeDeclaration();
					if (declaringType != null && declaringType instanceof ClassDeclaration) {
						superClassRef = ((ClassDeclaration) declaringType).getSuperClass();
					}
					if (declaringType == null
							&& aMethod.getAnonymousClassDeclarationOwner() != null) {
						ClassInstanceCreation cic = aMethod.getAnonymousClassDeclarationOwner()
								.getClassInstanceCreation();
						if (cic != null) {
							superClassRef = cic.getType();
						}
					}

					boolean bFound = false;
					// Look for similar method signature in super classes
					while (!bFound && superClassRef != null
							&& superClassRef.getType() instanceof ClassDeclaration) {
						ClassDeclaration superClass = (ClassDeclaration) superClassRef.getType();
						for (BodyDeclaration bodyDecl : superClass.getBodyDeclarations()) {
							if (bodyDecl instanceof MethodDeclaration
									&& signature
											.equals(getRawSignature((MethodDeclaration) bodyDecl))) {
								bFound = true;
								aMethod.setRedefinedMethodDeclaration((MethodDeclaration) bodyDecl);
							}
						}
						superClassRef = superClass.getSuperClass();
					}
				}
			}
		}
	}

	private static String getRawSignature(final MethodDeclaration aMethod) {
		String signature;
		signature = aMethod.getName();
		for (SingleVariableDeclaration aParam : aMethod.getParameters()) {
			signature += "|"; //$NON-NLS-1$
			signature += getRawSignature(aParam.getType());
			if (aParam.isVarargs()) {
				signature += "..."; //$NON-NLS-1$
			}
		}
		return signature;
	}

	private static String getRawSignature(final TypeAccess typeRef) {
		String signature;
		if (typeRef.getType() instanceof AbstractTypeDeclaration) {
			signature = JavaUtil.getQualifiedName(typeRef.getType(), true);
		} else {
			signature = typeRef.getType().getName();
		}
		return signature;
	}
}
