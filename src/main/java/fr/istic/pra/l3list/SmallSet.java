package fr.istic.pra.l3list;

import java.util.Arrays;

import fr.istic.pra.lang.L3Collection;


public class SmallSet implements L3Collection<Integer> {

	/**
	 * taille de l'ensemble
	 */
	public static final int SET_SIZE = 256;

	/**
	 * Ensemble est représenté comme un tableau de booléens.
	 */
	private boolean[] tab = new boolean[SET_SIZE];

	public SmallSet() {
		// TODO: écrire le constructeur de SmallSet
	}

	public SmallSet(boolean[] array) {
		// TODO: écrire le constructeur de SmallSet avec un tableau
	}

	public SmallSet(SmallSet set) {
		// TODO: écrire le constructeur de copie d'un SmallSet
	}

	/**
	 * @return nombre de valeurs appartenant à l’ensemble
	 */
	@Override
	public int size() {
		// TODO: écrire la méthode size()
		return -1;
	}

	/**
	 * @param x valeur à tester
	 * @return true, si l’entier x appartient à l’ensemble, false sinon
	 */
	@Override
	public boolean contains(Integer x) {
		// TODO: écrire la méthode contains(x)
		return false;
	}

	/**
	 * @return true, si l’ensemble est vide, false sinon
	 */
	@Override
	public boolean isEmpty() {
		// TODO: écrire la méthode isEmpty()
		return false;
	}

	/**
	 * Ajoute x à l’ensemble (sans effet si x déjà présent)
	 *
	 * @param x valeur à ajouter
	 * @pre 0 <= x <= 255
	 */
	@Override
	public void add(Integer x) {
		// TODO: écrire la méthode add(x)
	}

	/**
	 * Retire x de l’ensemble (sans effet si x n’est pas présent)
	 *
	 * @param x valeur à supprimer
	 * @pre 0 <= x <= 255
	 */
	@Override
	public void remove(Integer x) {
		// TODO: écrire la méthode remove(x)
	}

	/**
	 * Ajoute à l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin début de l’intervalle
	 * @param end   fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void addInterval(int deb, int fin) {
		// TODO: écrire la méthode addInterval(deb, fin)
	}

	/**
	 * Retire de l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin début de l’intervalle
	 * @param end   fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void removeInterval(int deb, int fin) {
		// TODO: écrire la méthode removeInterval
	}

	/**
	 * this devient l'union de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void union(L3Collection<Integer> set2) {
		// TODO: écrire la méthode union(set)
	}

	/**
	 * this devient l'intersection de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void intersection(L3Collection<Integer> set2) {
		// TODO: écrire la méthode intersection(set)
	}

	/**
	 * this devient la différence de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void difference(L3Collection<Integer> set2) {
		// TODO: écrire la méthode difference(set)
	}

	/**
	 * this devient la différence symétrique de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void symmetricDifference(L3Collection<Integer> set2) {
		// TODO: écrire la méthode symetricDifference(set)
	}

	/**
	 * this devient le complément de this.
	 */
	public void complement() {
		// TODO: écrire la méthode complement()
	}

	/**
	 * this devient l'ensemble vide.
	 */
	@Override
	public void clear() {
		// TODO: écrire la méthode clear()
	}

	/**
	 * @param set2 second ensemble
	 * @return true, si this est inclus dans set2, false sinon
	 */
	@Override
	public boolean isIncludedIn(L3Collection<Integer> set2) {
		// TODO: écrire la méthode isIncludedIn(set)
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		} else {
			SmallSet other = (SmallSet) obj;
			return Arrays.equals(this.tab, other.tab);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < SET_SIZE; ++i) {
			if (tab[i]) {
				result.append(i + " ");
			}
		}
		return result.toString();
	}
}
