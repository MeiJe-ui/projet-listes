package fr.istic.pra.l3list;

import fr.istic.pra.list.SmallSet; // a décommenté en partie 3.2 et a commenté en partie 3.3

public class SubSet implements Comparable<SubSet> {

	private final int rank;
	private final SmallSet set;

	public SubSet() {
		rank = 0;
		set = new SmallSet();
	}

	public SubSet(int rank, SmallSet smallSet) {
		this.rank = rank;
		this.set = smallSet;
	}

	public SubSet(SubSet subset) {
		this.rank = subset.rank;
		this.set = new SmallSet(subset.set);
	}

	public int getRank() {
		return this.rank;
	}

	public SmallSet getSet() {
		return this.set;
	}

	@Override
	public String toString() {
		return "Subset [rank=" + rank + ", set=" + set + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubSet)) {
			return false;
		}
		SubSet other = (SubSet) obj;
		return rank == other.rank && set.equals(other.set);
	}

	@Override
	public int compareTo(SubSet otherSubSet) {
		return Integer.compare(this.rank, otherSubSet.rank);
	}
}