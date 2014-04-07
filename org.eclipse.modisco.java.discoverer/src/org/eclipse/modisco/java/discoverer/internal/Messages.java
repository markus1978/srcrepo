/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fabien GIQUEL (Mia-Software) - initial API and implementation
 *    Romain Dervaux
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.modisco.java.discoverer.internal.messages"; //$NON-NLS-1$
	public static String DiscoverJavaModelFromJavaProject_title;
	public static String TranslateJavaModelToKdm_ConvertJavaToKDM;
	public static String JavaJdtBridge_0;
	public static String JavaJdtBridge_1;
	public static String JavaJdtBridge_2;
	public static String JavaJdtBridge_3;
	public static String JavaJdtBridge_4;
	public static String JavaJdtBridge_5;
	public static String JavaJdtBridge_6;
	public static String JavaJdtBridge_7;
	public static String JavaReader_bindingsTask;
	public static String JavaReader_discoveringTask;
	public static String JavaReader_redefinitionsTask;
	public static String JDTDelegateBindingFactory_10;
	public static String LibraryReader_BindingTask;
	public static String LibraryReader_DiscoveringTask;
	public static String LibraryReader_RedefinitionsTask;
	static {
		// initialize resource bundle
		NLS.initializeMessages(Messages.BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// Nothing
	}
}
