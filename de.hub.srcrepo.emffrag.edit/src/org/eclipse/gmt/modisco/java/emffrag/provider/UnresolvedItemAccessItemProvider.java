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

import org.eclipse.gmt.modisco.java.UnresolvedItemAccess;

import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.gmt.modisco.java.UnresolvedItemAccess} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UnresolvedItemAccessItemProvider
	extends ExpressionItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnresolvedItemAccessItemProvider(AdapterFactory adapterFactory) {
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

			addElementPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Element feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addElementPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_UnresolvedItemAccess_element_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_UnresolvedItemAccess_element_feature", "_UI_UnresolvedItemAccess_type"),
				 JavaPackage.eINSTANCE.getUnresolvedItemAccess_Element(),
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
			childrenFeatures.add(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier());
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
	 * This returns UnresolvedItemAccess.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/UnresolvedItemAccess"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_UnresolvedItemAccess_type");
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

		switch (notification.getFeatureID(UnresolvedItemAccess.class)) {
			case JavaPackage.UNRESOLVED_ITEM_ACCESS__QUALIFIER:
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
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArchive()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAssertStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAnnotationMemberValuePair()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAnnotationTypeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAnnotationTypeMemberDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAnonymousClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArrayAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArrayCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArrayInitializer()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArrayLengthAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createArrayType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createAssignment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createBooleanLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createBlockComment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createBreakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createCastExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createCatchClause()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createCharacterLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createClassFile()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createClassInstanceCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createConstructorDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createConditionalExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createCompilationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createContinueStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createDoStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createEmptyStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createEnhancedForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createEnumConstantDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createExpressionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createFieldDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createIfStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createImportDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createInfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createInitializer()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createInstanceofExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createJavadoc()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createLineComment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createMemberRef()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createMethodDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createMethodRef()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createMethodRefParameter()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createModifier()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createNumberLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createNullLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPackageAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createParameterizedType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createParenthesizedExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPostfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrefixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeBoolean()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeByte()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeChar()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeDouble()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeShort()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeFloat()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeInt()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeLong()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeVoid()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSingleVariableAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSingleVariableDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createStringLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSuperConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSuperFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSuperMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSwitchCase()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSwitchStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createSynchronizedStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTagElement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTextElement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createThisExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createThrowStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTryStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTypeAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTypeDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTypeLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createTypeParameter()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedItem()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedItemAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedAnnotationDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedAnnotationTypeMemberDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedMethodDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedSingleVariableDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedTypeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createVariableDeclarationFragment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createUnresolvedVariableDeclarationFragment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createVariableDeclarationExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createVariableDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createWildCardType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier(),
				 JavaFactory.eINSTANCE.createWhileStatement()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == JavaPackage.eINSTANCE.getASTNode_Comments() ||
			childFeature == JavaPackage.eINSTANCE.getUnresolvedItemAccess_Qualifier();

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
