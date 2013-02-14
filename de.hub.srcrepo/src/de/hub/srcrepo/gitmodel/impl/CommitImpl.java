/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hub.srcrepo.gitmodel.impl;

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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.GitModelPackage;
import de.hub.srcrepo.gitmodel.ParentRelation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getCommiter <em>Commiter</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getTime <em>Time</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getParentRelations <em>Parent Relations</em>}</li>
 *   <li>{@link de.hub.srcrepo.gitmodel.impl.CommitImpl#getChildRelations <em>Child Relations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommitImpl extends EObjectImpl implements Commit {
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
	protected CommitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GitModelPackage.Literals.COMMIT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.COMMIT__AUTHOR, oldAuthor, author));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.COMMIT__COMMITER, oldCommiter, commiter));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.COMMIT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.COMMIT__TIME, oldTime, time));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GitModelPackage.COMMIT__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParentRelation> getParentRelations() {
		if (parentRelations == null) {
			parentRelations = new EObjectContainmentWithInverseEList<ParentRelation>(ParentRelation.class, this, GitModelPackage.COMMIT__PARENT_RELATIONS, GitModelPackage.PARENT_RELATION__CHILD);
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
			childRelations = new EObjectWithInverseResolvingEList<ParentRelation>(ParentRelation.class, this, GitModelPackage.COMMIT__CHILD_RELATIONS, GitModelPackage.PARENT_RELATION__PARENT);
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
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParentRelations()).basicAdd(otherEnd, msgs);
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				return ((InternalEList<?>)getParentRelations()).basicRemove(otherEnd, msgs);
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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
			case GitModelPackage.COMMIT__AUTHOR:
				return getAuthor();
			case GitModelPackage.COMMIT__COMMITER:
				return getCommiter();
			case GitModelPackage.COMMIT__NAME:
				return getName();
			case GitModelPackage.COMMIT__TIME:
				return getTime();
			case GitModelPackage.COMMIT__MESSAGE:
				return getMessage();
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				return getParentRelations();
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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
			case GitModelPackage.COMMIT__AUTHOR:
				setAuthor((String)newValue);
				return;
			case GitModelPackage.COMMIT__COMMITER:
				setCommiter((String)newValue);
				return;
			case GitModelPackage.COMMIT__NAME:
				setName((String)newValue);
				return;
			case GitModelPackage.COMMIT__TIME:
				setTime((Date)newValue);
				return;
			case GitModelPackage.COMMIT__MESSAGE:
				setMessage((String)newValue);
				return;
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				getParentRelations().clear();
				getParentRelations().addAll((Collection<? extends ParentRelation>)newValue);
				return;
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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
			case GitModelPackage.COMMIT__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case GitModelPackage.COMMIT__COMMITER:
				setCommiter(COMMITER_EDEFAULT);
				return;
			case GitModelPackage.COMMIT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GitModelPackage.COMMIT__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case GitModelPackage.COMMIT__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				getParentRelations().clear();
				return;
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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
			case GitModelPackage.COMMIT__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case GitModelPackage.COMMIT__COMMITER:
				return COMMITER_EDEFAULT == null ? commiter != null : !COMMITER_EDEFAULT.equals(commiter);
			case GitModelPackage.COMMIT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GitModelPackage.COMMIT__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
			case GitModelPackage.COMMIT__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case GitModelPackage.COMMIT__PARENT_RELATIONS:
				return parentRelations != null && !parentRelations.isEmpty();
			case GitModelPackage.COMMIT__CHILD_RELATIONS:
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

} //CommitImpl
