/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.repositorymodel.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.srcrepo.repositorymodel.ParentRelation;
import de.hub.srcrepo.repositorymodel.RepositoryModelPackage;
import de.hub.srcrepo.repositorymodel.Rev;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rev</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getParentRelations <em>Parent Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.impl.RevImpl#getChildRelations <em>Child Relations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RevImpl extends EObjectImpl implements Rev {
	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getCommiter() <em>Commiter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommiter()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMITER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommiter() <em>Commiter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommiter()
	 * @generated
	 * @ordered
	 */
	protected String commiter = COMMITER_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected Date time = TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParentRelations() <em>Parent Relations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentRelations()
	 * @generated
	 * @ordered
	 */
	protected EList<ParentRelation> parentRelations;

	/**
	 * The cached value of the '{@link #getChildRelations() <em>Child Relations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildRelations()
	 * @generated
	 * @ordered
	 */
	protected EList<ParentRelation> childRelations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RevImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.REV;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REV__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommiter() {
		return commiter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommiter(String newCommiter) {
		String oldCommiter = commiter;
		commiter = newCommiter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REV__COMMITER, oldCommiter, commiter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REV__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTime(Date newTime) {
		Date oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REV__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepositoryModelPackage.REV__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParentRelation> getParentRelations() {
		if (parentRelations == null) {
			parentRelations = new EObjectContainmentWithInverseEList<ParentRelation>(ParentRelation.class, this, RepositoryModelPackage.REV__PARENT_RELATIONS, RepositoryModelPackage.PARENT_RELATION__CHILD);
		}
		return parentRelations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParentRelation> getChildRelations() {
		if (childRelations == null) {
			childRelations = new EObjectWithInverseResolvingEList<ParentRelation>(ParentRelation.class, this, RepositoryModelPackage.REV__CHILD_RELATIONS, RepositoryModelPackage.PARENT_RELATION__PARENT);
		}
		return childRelations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParentRelations()).basicAdd(otherEnd, msgs);
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildRelations()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				return ((InternalEList<?>)getParentRelations()).basicRemove(otherEnd, msgs);
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				return ((InternalEList<?>)getChildRelations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RepositoryModelPackage.REV__AUTHOR:
				return getAuthor();
			case RepositoryModelPackage.REV__COMMITER:
				return getCommiter();
			case RepositoryModelPackage.REV__NAME:
				return getName();
			case RepositoryModelPackage.REV__TIME:
				return getTime();
			case RepositoryModelPackage.REV__MESSAGE:
				return getMessage();
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				return getParentRelations();
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				return getChildRelations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RepositoryModelPackage.REV__AUTHOR:
				setAuthor((String)newValue);
				return;
			case RepositoryModelPackage.REV__COMMITER:
				setCommiter((String)newValue);
				return;
			case RepositoryModelPackage.REV__NAME:
				setName((String)newValue);
				return;
			case RepositoryModelPackage.REV__TIME:
				setTime((Date)newValue);
				return;
			case RepositoryModelPackage.REV__MESSAGE:
				setMessage((String)newValue);
				return;
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				getParentRelations().clear();
				getParentRelations().addAll((Collection<? extends ParentRelation>)newValue);
				return;
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				getChildRelations().clear();
				getChildRelations().addAll((Collection<? extends ParentRelation>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RepositoryModelPackage.REV__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case RepositoryModelPackage.REV__COMMITER:
				setCommiter(COMMITER_EDEFAULT);
				return;
			case RepositoryModelPackage.REV__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RepositoryModelPackage.REV__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case RepositoryModelPackage.REV__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				getParentRelations().clear();
				return;
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				getChildRelations().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RepositoryModelPackage.REV__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case RepositoryModelPackage.REV__COMMITER:
				return COMMITER_EDEFAULT == null ? commiter != null : !COMMITER_EDEFAULT.equals(commiter);
			case RepositoryModelPackage.REV__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RepositoryModelPackage.REV__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
			case RepositoryModelPackage.REV__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case RepositoryModelPackage.REV__PARENT_RELATIONS:
				return parentRelations != null && !parentRelations.isEmpty();
			case RepositoryModelPackage.REV__CHILD_RELATIONS:
				return childRelations != null && !childRelations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (author: ");
		result.append(author);
		result.append(", commiter: ");
		result.append(commiter);
		result.append(", name: ");
		result.append(name);
		result.append(", time: ");
		result.append(time);
		result.append(", message: ");
		result.append(message);
		result.append(')');
		return result.toString();
	}

} //RevImpl
