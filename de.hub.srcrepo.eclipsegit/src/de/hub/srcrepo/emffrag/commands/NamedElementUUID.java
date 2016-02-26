package de.hub.srcrepo.emffrag.commands;

public class NamedElementUUID {
	private final String id;
	private final String rev;
	private final String project;
	public NamedElementUUID( String rev, String project, String id) {
		super();
		this.id = id;
		this.rev = rev;
		this.project = project;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((rev == null) ? 0 : rev.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedElementUUID other = (NamedElementUUID) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (rev == null) {
			if (other.rev != null)
				return false;
		} else if (!rev.equals(other.rev))
			return false;
		return true;
	}
	public String getId() {
		return id;
	}
	public String getRev() {
		return rev;
	}
	public String getProject() {
		return project;
	}
	
}
