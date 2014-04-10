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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.gmt.modisco.java.LabeledStatement;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.gmt.modisco.java.LabeledStatement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class LabeledStatementItemProvider
	extends NamedElementItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LabeledStatementItemProvider(AdapterFactory adapterFactory) {
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

			addUsagesInBreakStatementsPropertyDescriptor(object);
			addUsagesInContinueStatementsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Usages In Break Statements feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUsagesInBreakStatementsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabeledStatement_usagesInBreakStatements_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabeledStatement_usagesInBreakStatements_feature", "_UI_LabeledStatement_type"),
				 JavaPackage.eINSTANCE.getLabeledStatement_UsagesInBreakStatements(),
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Usages In Continue Statements feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUsagesInContinueStatementsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabeledStatement_usagesInContinueStatements_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabeledStatement_usagesInContinueStatements_feature", "_UI_LabeledStatement_type"),
				 JavaPackage.eINSTANCE.getLabeledStatement_UsagesInContinueStatements(),
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
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
			childrenFeatures.add(JavaPackage.eINSTANCE.getLabeledStatement_Body());
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
	 * This returns LabeledStatement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/LabeledStatement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((LabeledStatement)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_LabeledStatement_type") :
			getString("_UI_LabeledStatement_type") + " " + label;
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

		switch (notification.getFeatureID(LabeledStatement.class)) {
			case JavaPackage.LABELED_STATEMENT__BODY:
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
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createAssertStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createBreakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createCatchClause()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createContinueStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createDoStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createEmptyStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createEnhancedForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createExpressionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createIfStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createSuperConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createSwitchCase()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createSwitchStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createSynchronizedStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createThrowStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createTryStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createTypeDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createUnresolvedLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createVariableDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getLabeledStatement_Body(),
				 JavaFactory.eINSTANCE.createWhileStatement()));
	}

}
