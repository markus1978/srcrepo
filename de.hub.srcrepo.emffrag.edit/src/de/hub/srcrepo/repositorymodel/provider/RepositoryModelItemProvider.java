/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import de.hub.srcrepo.repositorymodel.RepositoryModel;
import de.hub.srcrepo.repositorymodel.Rev;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelFactory;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * This is the item provider adapter for a {@link de.hub.srcrepo.repositorymodel.RepositoryModel} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RepositoryModelItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryModelItemProvider(AdapterFactory adapterFactory) {
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

			addRootRevPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Root Rev feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRootRevPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_RepositoryModel_rootRev_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_RepositoryModel_rootRev_feature", "_UI_RepositoryModel_type"),
				 RepositoryModelPackage.Literals.REPOSITORY_MODEL__ROOT_REV,
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
			childrenFeatures.add(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REFS);
			childrenFeatures.add(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REVS);
			childrenFeatures.add(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS);
		}
		return childrenFeatures;
	}

	@Override
	protected Object getValue(EObject eObject, EStructuralFeature eStructuralFeature) {
		if (eStructuralFeature == RepositoryModelPackage.eINSTANCE.getRepositoryModel_AllRevs()) {
			// only supply the first couple of elements
			List<EObject> revs = new ArrayList<EObject>();
			Iterator<Rev> iterator = ((RepositoryModel)eObject).getAllRevs().iterator();
			for (int i = 0; i < 10; i++) {
				if (iterator.hasNext());
				revs.add(iterator.next());
			}
			return revs;
		} else {
			return super.getValue(eObject, eStructuralFeature);
		}
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
	 * This returns RepositoryModel.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/RepositoryModel"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_RepositoryModel_type");
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

		switch (notification.getFeatureID(RepositoryModel.class)) {
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REFS:
			case RepositoryModelPackage.REPOSITORY_MODEL__ALL_REVS:
			case RepositoryModelPackage.REPOSITORY_MODEL__TRAVERSALS:
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
				(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REFS,
				 RepositoryModelFactory.eINSTANCE.createRef()));

		newChildDescriptors.add
			(createChildParameter
				(RepositoryModelPackage.Literals.REPOSITORY_MODEL__ALL_REVS,
				 RepositoryModelFactory.eINSTANCE.createRev()));

		newChildDescriptors.add
			(createChildParameter
				(RepositoryModelPackage.Literals.REPOSITORY_MODEL__TRAVERSALS,
				 RepositoryModelFactory.eINSTANCE.createTraversalState()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return RepositoryModelEditPlugin.INSTANCE;
	}

}
