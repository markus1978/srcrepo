/**
 */
package de.hub.srcrepo.repositorymodel.emffrag.impl;

import java.lang.ref.WeakReference;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.hub.emffrag.fragmentation.FObjectImpl;
import de.hub.jstattrack.ValueStatistic;
import de.hub.jstattrack.services.BatchedPlot;
import de.hub.jstattrack.services.Summary;
import de.hub.srcrepo.repositorymodel.AbstractFileRef;
import de.hub.srcrepo.repositorymodel.Diff;
import de.hub.srcrepo.repositorymodel.emffrag.metadata.RepositoryModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getNewPath <em>New Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getOldPath <em>Old Path</em>}</li>
 *   <li>{@link de.hub.srcrepo.repositorymodel.emffrag.impl.DiffImpl#getFile <em>File</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiffImpl extends FObjectImpl implements Diff {
	
	private final static ValueStatistic diffCountStat = new ValueStatistic().with(Summary.class).with(BatchedPlot.class).register(Diff.class, "Diff count on heap");
	private final static Cache<Diff, Long> testCache = CacheBuilder.newBuilder().weakKeys().build();
	private static long counter = 0;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected DiffImpl() {
		super();
		testCache.put(this, counter++);
		if (counter % 10000 == 0) {
			// run gc, after that measure memory
			for (int i = 0; i < 2; i++) {
				Object obj = new Object();
				WeakReference<?> ref = new WeakReference<Object>(obj);
				obj = null;
				while (ref.get() != null) {
					System.gc();
				}
				System.runFinalization();
			}
			testCache.cleanUp();
			diffCountStat.track(testCache.size());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepositoryModelPackage.Literals.DIFF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNewPath() {
		return (String)eGet(RepositoryModelPackage.Literals.DIFF__NEW_PATH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewPath(String newNewPath) {
		eSet(RepositoryModelPackage.Literals.DIFF__NEW_PATH, newNewPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeType getType() {
		return (ChangeType)eGet(RepositoryModelPackage.Literals.DIFF__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ChangeType newType) {
		eSet(RepositoryModelPackage.Literals.DIFF__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOldPath() {
		return (String)eGet(RepositoryModelPackage.Literals.DIFF__OLD_PATH, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldPath(String newOldPath) {
		eSet(RepositoryModelPackage.Literals.DIFF__OLD_PATH, newOldPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractFileRef getFile() {
		return (AbstractFileRef)eGet(RepositoryModelPackage.Literals.DIFF__FILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(AbstractFileRef newFile) {
		eSet(RepositoryModelPackage.Literals.DIFF__FILE, newFile);
	}

} //DiffImpl
