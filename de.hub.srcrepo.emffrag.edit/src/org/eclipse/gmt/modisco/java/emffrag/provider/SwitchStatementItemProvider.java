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
import org.eclipse.gmt.modisco.java.SwitchStatement;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.gmt.modisco.java.SwitchStatement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SwitchStatementItemProvider
	extends StatementItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SwitchStatementItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(JavaPackage.eINSTANCE.getSwitchStatement_Expression());
			childrenFeatures.add(JavaPackage.eINSTANCE.getSwitchStatement_Statements());
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
	 * This returns SwitchStatement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/SwitchStatement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_SwitchStatement_type");
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

		switch (notification.getFeatureID(SwitchStatement.class)) {
			case JavaPackage.SWITCH_STATEMENT__EXPRESSION:
			case JavaPackage.SWITCH_STATEMENT__STATEMENTS:
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
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createArrayAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createArrayCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createArrayInitializer()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createArrayLengthAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createAssignment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createBooleanLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createCastExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createCharacterLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createClassInstanceCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createConditionalExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createInfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createInstanceofExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createNumberLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createNullLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createParenthesizedExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createPostfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createPrefixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createSingleVariableAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createStringLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createSuperFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createSuperMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createThisExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createTypeAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createTypeLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createUnresolvedItemAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Expression(),
				 JavaFactory.eINSTANCE.createVariableDeclarationExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createAssertStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createBreakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createCatchClause()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createContinueStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createDoStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createEmptyStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createEnhancedForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createExpressionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createIfStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createSuperConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createSwitchCase()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createSwitchStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createSynchronizedStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createThrowStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createTryStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createTypeDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createUnresolvedLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createVariableDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getSwitchStatement_Statements(),
				 JavaFactory.eINSTANCE.createWhileStatement()));
	}

}