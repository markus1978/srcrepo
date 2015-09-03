/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sebastien Minguet (Mia-Software) - initial API and implementation
 *    Frederic Madiot (Mia-Software) - initial API and implementation
 *    Fabien Giquel (Mia-Software) - initial API and implementation
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Erwan Breton (Sodifrance) - initial API and implementation
 *    Romain Dervaux (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software) - Bug 335003 - [Discoverer] : Existing Discoverers Refactoring based on new framework
 *******************************************************************************/
package org.eclipse.modisco.java.discoverer.internal.io.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Comment;
import org.eclipse.gmt.modisco.java.Javadoc;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.modisco.java.discoverer.internal.io.java.binding.PendingElement;

/**
 * The aim of this class is to link comments to the related nodes. JDT does not
 * attach comments (line and block comments) to model nodes.
 */
public final class CommentsManager {
	private final boolean debug = false;
	private static CommentsManager instance = new CommentsManager();

	private CommentsManager() {
		// Nothing
	}

	public static void resolveCommentPositions(final JDTVisitor visitor) {
		org.eclipse.jdt.core.dom.CompilationUnit cuJdtNode = visitor.getCuNode();
		org.eclipse.gmt.modisco.java.CompilationUnit moDiscoCuNode = (org.eclipse.gmt.modisco.java.CompilationUnit) visitor
				.getBijectiveMap().getValue(cuJdtNode);
		List<Comment> commentsList = new ArrayList<Comment>(visitor.getCommentsBinding()
				.getValues());

		CommentsManager manager = CommentsManager.instance;
		List<Comment> unLocatedComments = manager.jdtLocationSearch(visitor, cuJdtNode,
				moDiscoCuNode, commentsList);
		if (CommentsManager.instance.debug) {
			System.out.println("===> Comments management <==== " + moDiscoCuNode.getName()); //$NON-NLS-1$
			System.out.println("total number of comments = " + commentsList.size()); //$NON-NLS-1$
			System.out.println("number of unlocated comments = " + unLocatedComments.size()); //$NON-NLS-1$
			System.out
					.println("number of comments directly owned by the compilation unit = " + moDiscoCuNode.getComments().size()); //$NON-NLS-1$
		}
		/*
		 * to be able to manage correctly the comments directly owned by the
		 * compilation unit, we will try the alternative algorithm (instead of
		 * jdt algorithm) to retrieve the correct location of these comments.
		 * EMF will manage the relocation.
		 */
		unLocatedComments.addAll(moDiscoCuNode.getComments());
		/*
		 * To manage the comments inside a class declaration:
		 * 
		 * As all comments inside are generated at the end of the class
		 * declaration, because we don't store where it comes from (line
		 * position). So we should try to attach these comments to a better
		 * element, using the alternative algorithm which handle whitespaces
		 * differently. EMF will manage the relocation.
		 */
		for (AbstractTypeDeclaration type : moDiscoCuNode.getTypes()) {
			Iterator<Comment> iterator = type.getComments().iterator();
			while (iterator.hasNext()) {
				Comment comment = iterator.next();
				if (comment.isEnclosedByParent()) {
					unLocatedComments.add(comment);
				}
			}
		}
		// processing remaining comments
		for (Comment comment : unLocatedComments) {
			if (CommentsManager.instance.debug) {
				System.out.println("alternate location for comment: " + comment.getContent()); //$NON-NLS-1$
			}
			boolean locationFound = CommentsManager.alternateLocationSearch(comment, visitor,
					moDiscoCuNode);

			/*
			 * We assure that all comments are processed, in linking remaining
			 * unlocated comments to root type.
			 */
			if (!locationFound) {
				if (CommentsManager.instance.debug) {
					System.out.println("comment location not found: " + comment.getContent()); //$NON-NLS-1$
				}
				if (visitor.getRootTypeOrEnum() != null) {
					visitor.getRootTypeOrEnum().getComments().add(comment);
				} else { // misc case of java file whithout type declaration
					EcoreUtil.delete(comment);
				}
			}
		}
	}

	/**
	 * Linking comments to Nodes using jdt location informations
	 * 
	 * @param visitor
	 * @param cuJdtNode
	 * @param moDiscoCuNode
	 * @param commentsList
	 * @return
	 */
	private List<Comment> jdtLocationSearch(final JDTVisitor visitor,
			final org.eclipse.jdt.core.dom.CompilationUnit cuJdtNode,
			final org.eclipse.gmt.modisco.java.CompilationUnit moDiscoCuNode,
			final List<Comment> commentsList) {
		List<Comment> localCommentList = new ArrayList<Comment>(commentsList);
		SortedMap<Integer, org.eclipse.jdt.core.dom.ASTNode> nodesMap = new TreeMap<Integer, org.eclipse.jdt.core.dom.ASTNode>(
				new Comparator<Integer>() {
					/**
					 * We must give an order to nodes which own comments : jdt
					 * ast nodes gives only starting and ending indexes. e.g.,
					 * for a root class, start index will be 2 and end will be
					 * 11, for a method definition, start index will be 4 and
					 * end will be 6. Ordering will consider first the node
					 * whose starting index is the highest.
					 * 
					 * But what if a node has only a trailing comment ? first
					 * leading comment index = -1 !
					 * 
					 * Here is a summary of all cases available:
					 * <table border="1">
					 * <tr>
					 * <td>case</td>
					 * <td>A</td>
					 * <td>B</td>
					 * <td>C</td>
					 * <td>D</td>
					 * </tr>
					 * <tr>
					 * <td>firstLeadingCommentIndex</td>
					 * <td>-1</td>
					 * <td>n</td>
					 * <td>n</td>
					 * <td>-1</td>
					 * </tr>
					 * <tr>
					 * <td>lastTrailingCommentIndex</td>
					 * <td>-1</td>
					 * <td>-1</td>
					 * <td>n</td>
					 * <td>n</td>
					 * </tr>
					 * </tr>
					 * </table>
					 * 
					 * Note: for each case, we cannot gather the information of
					 * having, or not, comments inside the element (specific to
					 * elements which have a block ?)
					 * <ul>
					 * <li>case A: We have no comments before and after the
					 * element
					 * 
					 * <li>case B: We have comments before the element
					 * 
					 * <li>case C: We have comments before and after the element
					 * 
					 * <li>case D: We have comments after the element
					 * </ul>
					 * 
					 * Warning: a bug has been potentially found ! The start
					 * position of a node corresponds to the extended start
					 * position (including comments and whitespace)) but only
					 * for elements that have a javadoc. So to bypass this bug,
					 * we have to process comment positions in a different way.
					 * The order of comments locating should be: comments
					 * before, comments after, then comments inside.
					 * <ul>
					 * <li>for comments before, we will start from the first
					 * comment, then shift the start position to the end of
					 * first comment, verify that next comment starts at the new
					 * start position (end of first comment) otherwise we have
					 * to consider it is not a comment before.
					 * <li>For the comments after, we will start from the last
					 * comment which end position should be the same as the end
					 * position of the node, then previous comment should end at
					 * the start position of last comment otherwise it is not a
					 * comment after.
					 * <li>Comments inside are comments in the node that are not
					 * before or after.
					 * </ul>
					 * 
					 */
					public int compare(final Integer o1, final Integer o2) {
						return -o1.compareTo(o2);
					}
				});
		for (org.eclipse.jdt.core.dom.ASTNode node : visitor.getBijectiveMap().getKeys()) {
			/*
			 * we have to decide if the element has comment
			 * 
			 * 1. firstLeadingCommentIndex != -1
			 * 
			 * 2. lastTrailingCommentIndex != -1
			 * 
			 * 3. start position of a comment in the list is included in the
			 * range of start position and end position of the element. This
			 * algorithm may lead to add some elements that have no comments ...
			 */
			int position = node.getStartPosition();
			if (cuJdtNode.firstLeadingCommentIndex(node) != -1) {
				nodesMap.put(position, node);
			} else if (cuJdtNode.lastTrailingCommentIndex(node) != -1) {
				nodesMap.put(position, node);
			}
		}
		/*
		 * we have now a sorted collection of nodes, using this collection we
		 * will be able to iterate to link current node with its comments.
		 */
		for (Integer indexMap : nodesMap.keySet()) {
			org.eclipse.jdt.core.dom.ASTNode jdtNode = nodesMap.get(indexMap);
			ASTNode element = visitor.getBijectiveMap().getValue(jdtNode);
			if (element instanceof PendingElement) {
				if (((PendingElement) element).getClientNode() != null) {
					element = ((PendingElement) element).getClientNode();
				} else { // should never happen
					element = visitor.getBijectiveMap().getValue(jdtNode.getParent());
				}
			}
			if (element instanceof Package) {
				// replace it by the compilation unit
				element = moDiscoCuNode;
			} else {
				// we have to clear javadoc comments that have been already
				// linked to the element
				ListIterator<Comment> listIterator = element.getComments().listIterator();
				// element.getComments().clear();
				while (listIterator.hasNext()) {
					Comment comment = listIterator.next();
					if (comment instanceof Javadoc) {
						// Javadoc javadoc = (Javadoc) comment;
						// XXX put back in the un-located comments list?
						// localCommentList.add(javadoc);
						listIterator.remove();
					}
				}

			}
			if (CommentsManager.instance.debug) {
				System.out.println("element commented ? " + element.toString()); //$NON-NLS-1$
			}
			List<Comment> commentsToLink = computeListOfcommentsBefore(jdtNode, cuJdtNode, visitor);
			addComments(commentsToLink, element, false, true, localCommentList);

			commentsToLink = computeListOfcommentsAfter(jdtNode, cuJdtNode, visitor);
			addComments(commentsToLink, element, false, false, localCommentList);
		}

		// return unlocated comment
		return localCommentList;
	}

	/**
	 * @param jdtNode
	 * @param cuJdtNode
	 * @param visitor
	 * @return
	 */
	private List<Comment> computeListOfcommentsAfter(
			final org.eclipse.jdt.core.dom.ASTNode jdtNode, final CompilationUnit cuJdtNode,
			final JDTVisitor visitor) {
		List<Comment> result = new ArrayList<Comment>();
		int index = cuJdtNode.lastTrailingCommentIndex(jdtNode);
		if (index != -1) {
			/*
			 * we have to retrieve all comments which start position is more
			 * than basic end position of jdt node. Or the opposite way, all
			 * comments which ended before the extended end position of jdt
			 * node.
			 */
			int endPosition = cuJdtNode.getExtendedStartPosition(jdtNode)
					+ cuJdtNode.getExtendedLength(jdtNode);
			for (int i = index; i > -1; i--) {
				org.eclipse.jdt.core.dom.ASTNode jdtComment = (org.eclipse.jdt.core.dom.ASTNode) cuJdtNode
						.getCommentList().get(i);
				int commentEndPosition = (cuJdtNode.getExtendedStartPosition(jdtComment) + cuJdtNode
						.getExtendedLength(jdtComment));
				if (this.debug) {
					System.out.println("end position = " + endPosition); //$NON-NLS-1$
					System.out.println("comment end position = " + commentEndPosition); //$NON-NLS-1$
				}
				String whitespaces = null;
				if (endPosition > commentEndPosition) {
					whitespaces = visitor.getJavaContent()
							.substring(commentEndPosition, endPosition).trim();
				}
				if ((whitespaces == null) || (whitespaces.length() == 0)) {
					// shift the end position to manage further comments
					endPosition = cuJdtNode.getExtendedStartPosition(jdtComment);
					Comment comment = visitor.getCommentsBinding().get(jdtComment);
					if (comment != null) {
						result.add(0, comment);
					}
				} else {
					// stop iteration (we are inside or before the jdt node)
					i = -1;
				}
			}
		}
		if (this.debug) {
			System.out.println("number of comments after the node = " + result.size()); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * @param commentsBefore
	 * @param element
	 * @param enclosedByElement
	 * @param prefixOfElement
	 */
	private void addComments(final List<Comment> commentsBefore, final ASTNode element,
			final boolean enclosedByElement, final boolean prefixOfElement,
			final List<Comment> localCommentsList) {
		for (Comment comment : commentsBefore) {
			if (localCommentsList.contains(comment)) {
				if (this.debug) {
					System.out.println("added comment = " + comment.getContent()); //$NON-NLS-1$
				}
				comment.setEnclosedByParent(enclosedByElement);
				comment.setPrefixOfParent(prefixOfElement);

				element.getComments().add(comment);
				localCommentsList.remove(comment);
			}
		}
	}

	/**
	 * @param jdtNode
	 * @param cuJdtNode
	 * @return
	 */
	private List<Comment> computeListOfcommentsBefore(
			final org.eclipse.jdt.core.dom.ASTNode jdtNode, final CompilationUnit cuJdtNode,
			final JDTVisitor visitor) {
		List<Comment> result = new ArrayList<Comment>();
		int index = cuJdtNode.firstLeadingCommentIndex(jdtNode);
		if (index != -1) {
			/*
			 * we have to retrieve all comments which start position is less
			 * than start position of jdt node. shall we use the extended
			 * position and length ?
			 */
			int size = cuJdtNode.getCommentList().size();
			int startPosition = cuJdtNode.getExtendedStartPosition(jdtNode);
			for (int i = index; i < size; i++) {
				org.eclipse.jdt.core.dom.ASTNode jdtComment = (org.eclipse.jdt.core.dom.ASTNode) cuJdtNode
						.getCommentList().get(i);
				int commentPosition = cuJdtNode.getExtendedStartPosition(jdtComment);
				if (this.debug) {
					System.out.println("start position = " + startPosition); //$NON-NLS-1$
					System.out.println("comment position = " + commentPosition); //$NON-NLS-1$
				}
				String whitespaces = null;
				if (commentPosition > startPosition) {
					try {
						whitespaces = visitor.getJavaContent().substring(startPosition, commentPosition).trim();
					} catch (Exception e) {
						// markus, hub, sam, srcrepo
						// this often throughs a sting out of bounds exception.
						// will simply break the loop, and skip all further comments, when this happens.
						break;
					}
				}
				if ((whitespaces == null) || (whitespaces.length() == 0)) {
					/*
					 * shift the start position to manage further comments, to
					 * manage whitespace (space and tabulation).
					 */
					startPosition = commentPosition + cuJdtNode.getExtendedLength(jdtComment);
					Comment comment = visitor.getCommentsBinding().get(jdtComment);
					if (comment != null) {
						result.add(comment);
					}
				} else {
					// stop iteration (we are inside or after the jdt node)
					i = size;
				}
			}
		}
		if (this.debug) {
			System.out.println("number of comments before the node = " + result.size()); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * @param comment
	 * @param originalFileContent
	 * @return content of comment
	 */
	public static String extractCommentContent(final org.eclipse.jdt.core.dom.Comment comment,
			final String originalFileContent) {
		String result = originalFileContent.substring(comment.getStartPosition(),
				comment.getStartPosition() + comment.getLength());
		return result;
	}

	/**
	 * Some misc comments are not linked to any node in jdt AST : - comments in
	 * a block, with at least one blank line before next node and one blank line
	 * after last node - comments in a block, at the end and with at least one
	 * blank line after last node. Use another algorithm to link them.
	 * 
	 * @param comment
	 * @param visitor
	 */
	private static boolean alternateLocationSearch(final Comment comment, final JDTVisitor visitor,
			final org.eclipse.gmt.modisco.java.CompilationUnit moDiscoCuNode) {
		ASTNode bestParent = null;
		org.eclipse.jdt.core.dom.ASTNode jdtComment = visitor.getCommentsBinding().getKey(comment);

		// We search the nearest element in default parent subtree
		int bestFollowingNodeStart = Integer.MAX_VALUE;
		int bestFollowingNodeEnd = Integer.MIN_VALUE;
		int bestEnclosingNodeStart = Integer.MIN_VALUE;
		int bestEnclosingNodeEnd = Integer.MAX_VALUE;
		for (org.eclipse.jdt.core.dom.ASTNode jdtNode : visitor.getBijectiveMap().getKeys()) {
			ASTNode modiscoNode = visitor.getBijectiveMap().get(jdtNode);
			if (mayOwnComment(jdtNode) && !(modiscoNode instanceof PendingElement)) {
				int sp = jdtNode.getStartPosition();
				int ep = jdtNode.getStartPosition() + jdtNode.getLength();
				// Does this node follow the comment more closely ?
				if ((sp >= jdtComment.getStartPosition() + jdtComment.getLength())
						&& (sp < bestFollowingNodeStart || (sp == bestFollowingNodeStart && ep > bestFollowingNodeEnd))
						&& (sp < bestEnclosingNodeEnd)) {
					bestFollowingNodeStart = sp;
					bestFollowingNodeEnd = ep;
					bestParent = modiscoNode;
					comment.setEnclosedByParent(false);
					comment.setPrefixOfParent(true);
				}
				// Does this node enclose the comment more closely ?
				if ((sp <= jdtComment.getStartPosition()) && (ep > jdtComment.getStartPosition())
						&& (ep < bestFollowingNodeStart) && (ep <= bestEnclosingNodeEnd)
						&& (sp >= bestEnclosingNodeStart)) {
					bestEnclosingNodeEnd = ep;
					bestEnclosingNodeStart = sp;
					bestParent = modiscoNode;
					comment.setEnclosedByParent(true);
					comment.setPrefixOfParent(false);
				}
			}
		}

		if (bestParent != null) {
			// insert comment in parent comment list at the right position
			if (bestParent instanceof Package) {
				bestParent = moDiscoCuNode;
				comment.setEnclosedByParent(false);
				comment.setPrefixOfParent(true);
			}
			attachComment(comment, jdtComment, bestParent, visitor);
			return true;
		}
		return false;

	}

	/*
	 * Links a comment to a receiver, in making sure of the comments ordering
	 */
	private static void attachComment(final Comment comment,
			final org.eclipse.jdt.core.dom.ASTNode jdtComment, final ASTNode receiver,
			final JDTVisitor visitor) {
		if (receiver.getComments().contains(comment)) {
			return;
		}
		int insertIndex = 0;
		for (; insertIndex < receiver.getComments().size(); insertIndex++) {
			org.eclipse.jdt.core.dom.ASTNode jdtOtherComment = visitor.getCommentsBinding().getKey(
					receiver.getComments().get(insertIndex));
			if (jdtOtherComment != null
					&& jdtOtherComment.getStartPosition() > jdtComment.getStartPosition()) {
				break;
			}
		}
		receiver.getComments().add(insertIndex, comment);
	}

	/*
	 * A comment element (Comment, Tag, ...) can not own others comments (there
	 * is always an appropriate parent java statement)
	 */
	private static boolean mayOwnComment(final org.eclipse.jdt.core.dom.ASTNode element) {
		return !(element instanceof org.eclipse.jdt.core.dom.Comment)
				&& !(element instanceof org.eclipse.jdt.core.dom.TagElement)
				&& !(element instanceof org.eclipse.jdt.core.dom.TextElement)
				&& !(element instanceof org.eclipse.jdt.core.dom.MemberRef)
				&& !(element instanceof org.eclipse.jdt.core.dom.MethodRef);
	}

}