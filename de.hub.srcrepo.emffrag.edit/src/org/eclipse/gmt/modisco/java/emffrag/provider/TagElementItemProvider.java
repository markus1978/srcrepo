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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.gmt.modisco.java.TagElement;

import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaFactory;
import org.eclipse.gmt.modisco.java.emffrag.metadata.JavaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.gmt.modisco.java.TagElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TagElementItemProvider
	extends ASTNodeItemProvider
	implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TagElementItemProvider(AdapterFactory adapterFactory) {
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

			addTagNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Tag Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTagNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TagElement_tagName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TagElement_tagName_feature", "_UI_TagElement_type"),
				 JavaPackage.eINSTANCE.getTagElement_TagName(),
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
			childrenFeatures.add(JavaPackage.eINSTANCE.getTagElement_Fragments());
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
	 * This returns TagElement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TagElement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TagElement)object).getTagName();
		return label == null || label.length() == 0 ?
			getString("_UI_TagElement_type") :
			getString("_UI_TagElement_type") + " " + label;
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

		switch (notification.getFeatureID(TagElement.class)) {
			case JavaPackage.TAG_ELEMENT__TAG_NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case JavaPackage.TAG_ELEMENT__FRAGMENTS:
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
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAnnotation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArchive()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAssertStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAnnotationMemberValuePair()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAnnotationTypeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAnnotationTypeMemberDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAnonymousClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArrayAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArrayCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArrayInitializer()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArrayLengthAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createArrayType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createAssignment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createBooleanLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createBlockComment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createBreakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createCastExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createCatchClause()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createCharacterLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createClassFile()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createClassInstanceCreation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createConstructorDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createConditionalExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createCompilationUnit()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createContinueStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createDoStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createEmptyStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createEnhancedForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createEnumConstantDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createExpressionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createFieldDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createForStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createIfStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createImportDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createInfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createInitializer()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createInstanceofExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createJavadoc()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createLineComment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createMemberRef()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createMethodDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createMethodRef()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createMethodRefParameter()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createModifier()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createNumberLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createNullLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPackageAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createParameterizedType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createParenthesizedExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPostfixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrefixExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeBoolean()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeByte()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeChar()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeDouble()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeShort()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeFloat()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeInt()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeLong()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createPrimitiveTypeVoid()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSingleVariableAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSingleVariableDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createStringLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSuperConstructorInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSuperFieldAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSuperMethodInvocation()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSwitchCase()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSwitchStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createSynchronizedStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTagElement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTextElement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createThisExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createThrowStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTryStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTypeAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTypeDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTypeLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createTypeParameter()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedItem()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedItemAccess()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedAnnotationDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedAnnotationTypeMemberDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedClassDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedEnumDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedInterfaceDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedLabeledStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedMethodDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedSingleVariableDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedTypeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createVariableDeclarationFragment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createUnresolvedVariableDeclarationFragment()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createVariableDeclarationExpression()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createVariableDeclarationStatement()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
				 JavaFactory.eINSTANCE.createWildCardType()));

		newChildDescriptors.add
			(createChildParameter
				(JavaPackage.eINSTANCE.getTagElement_Fragments(),
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
			childFeature == JavaPackage.eINSTANCE.getTagElement_Fragments();

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
