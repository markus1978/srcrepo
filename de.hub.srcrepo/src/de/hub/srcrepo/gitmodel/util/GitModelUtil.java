package de.hub.srcrepo.gitmodel.util;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import de.hub.srcrepo.IGitModelVisitor;
import de.hub.srcrepo.gitmodel.Commit;
import de.hub.srcrepo.gitmodel.Diff;
import de.hub.srcrepo.gitmodel.ParentRelation;

public class GitModelUtil {

	public enum Direction { TO_PARENT, FROM_PARENT };
	
	public static void visitCommitHierarchy(Commit startingCommit, Direction direction, IGitModelVisitor visitor) {
		Queue<Commit> commitsToVisist = new LinkedList<Commit>();
		commitsToVisist.add(startingCommit);
		while (!commitsToVisist.isEmpty()) {
			Commit nextCommit = commitsToVisist.poll();
			
			if (visitor.onStartCommit(nextCommit)) {
				for (ParentRelation parentRelation: nextCommit.getParentRelations()) {
					for (Diff diff : parentRelation.getDiffs()) {
						if (diff.getType() == ChangeType.ADD) {
							visitor.onAddedFile(diff);
						} else if (diff.getType() == ChangeType.COPY) {
							visitor.onCopiedFile(diff);
						} else if (diff.getType() == ChangeType.DELETE) {
							visitor.onDeletedFile(diff);
						} else if (diff.getType() == ChangeType.MODIFY) {
							visitor.onModifiedFile(diff);
						} else if (diff.getType() == ChangeType.RENAME) {
							visitor.onRenamedFile(diff);
						}
					}
				}
				visitor.onCompleteCommit(nextCommit);
				
				if (direction == Direction.TO_PARENT) {
					for (ParentRelation parentRelation: nextCommit.getParentRelations()) {
						Commit parent = parentRelation.getParent();
						if (parent != null) {
							commitsToVisist.add(parent);
						}
					}
				} else { // if (direction == Direction.FROM_PARENT) {
					for (ParentRelation parentRelation: nextCommit.getChildRelations()) {
						Commit child = parentRelation.getChild();
						if (child != null) {
							commitsToVisist.add(child);
						}
					}
				}
			}
		}
	}
}
