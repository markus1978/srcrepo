/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmt.modisco.java.emffrag.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.gmt.modisco.java.TypeDeclarationStatement;

import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.gmt.modisco.java.TypeDeclarationStatement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TypeDeclarationStatementItemProvider
	extends StatementItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeDeclarationStatementItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration());
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TypeDeclarationStatement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TypeDeclarationStatement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_TypeDeclarationStatement_type");
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TypeDeclarationStatement.class)) {
			case JavaPackage.TYPE_DECLARATION_STATEMENT__DECLARATION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createAnnotationTypeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createUnresolvedAnnotationDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createUnresolvedClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createUnresolvedEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createUnresolvedInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTypeDeclarationStatement_Declaration(),
				 JavaFactory.eINSTANCE.createUnresolvedTypeDeclaration()));
	}

}
