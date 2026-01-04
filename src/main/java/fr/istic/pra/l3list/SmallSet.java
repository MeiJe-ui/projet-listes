package fr.istic.pra.l3list;

import java.util.Arrays;

import fr.istic.pra.lang.L3Collection;
import fr.istic.pra.lang.L3Iterator;


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
		for(int i = 0 ; i < SET_SIZE ; i++) {
			tab[i] = false;
		}
	}

	public SmallSet(boolean[] array) {
		int i;

		if(array.length > SET_SIZE){
			for(i = 0 ; i < SET_SIZE ; i++){
				tab[i] = array[i];
			}
			return;
		}

		if(array.length < SET_SIZE) {
			for( i = array.length; i < SET_SIZE ; i++) {
				tab[i] = false;
			}
			return;
		}

		for( i = 0 ; i < array.length ; i++) {
			tab[i] = array[i];
		}

	}

	public SmallSet(SmallSet set) {
		for(int i = 0 ; i < SET_SIZE ; i++) {
			tab[i] = set.tab[i];
		}
	}

	/**
	 * @return nombre de valeurs appartenant à l’ensemble
	 */
	@Override
	public int size() {
		int compteur = 0 ;
		for (boolean b : tab) {
			if(b) {compteur++;}
		}
		return compteur;
	}

	/**
	 * @param x valeur à tester
	 * @return true, si l’entier x appartient à l’ensemble, false sinon
	 */
	@Override
	public boolean contains(Integer x) {
		
		if(x > SET_SIZE || x <0) {return false;}
		
		else{
			return tab[x];
		}

	}

	/**
	 * @return true, si l’ensemble est vide, false sinon
	 */
	@Override
	public boolean isEmpty() {
		
		for (boolean b : tab) {
			if(b) {return false;}
		}

		return true;
	}

	/**
	 * Ajoute x à l’ensemble (sans effet si x déjà présent)
	 *
	 * @param x valeur à ajouter
	 * @pre 0 <= x <= 255
	 */
	@Override
	public void add(Integer x) {
		if( x<0 || x > 255) {
			return;
		}
		tab[x] = true;
	}

	/**
	 * Retire x de l’ensemble (sans effet si x n’est pas présent)
	 *
	 * @param x valeur à supprimer
	 * @pre 0 <= x <= 255
	 */
	@Override
	public void remove(Integer x) {
		if( x<0 || x > 255) {
			return;
		}
		tab[x] = false;
	}

	/**
	 * Ajoute à l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin début de l’intervalle
	 * @param end   fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void addInterval(int deb, int fin) {
		if(deb < 0 || fin > 255 || deb > fin) {
			return;
		}
		for(int i = deb; i<=fin ; i++) {
			tab[i] = true;
		}
	}

	/**
	 * Retire de l’ensemble les valeurs deb, deb+1, deb+2, ..., fin.
	 *
	 * @param begin début de l’intervalle
	 * @param end   fin de l’intervalle
	 * @pre 0 <= begin <= end <= 255
	 */
	public void removeInterval(int deb, int fin) {
		if(deb < 0 || fin > 255 || deb > fin) {
			return;
		}
		for(int i = deb; i<=fin ; i++) {
			tab[i] = false;
		}
	}

	/**
	 * this devient l'union de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void union(L3Collection<Integer> set2) {

		for(int i = 0 ; i < SET_SIZE ; i++) {
			if(set2.contains(i)){
				this.tab[i] = true;
			}
		}
	}

	/**
	 * this devient l'intersection de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void intersection(L3Collection<Integer> set2) {
		
		for(int i = 0 ; i < SET_SIZE ; i++) {
			if(set2.contains(i) && this.tab[i]){
				this.tab[i] = true;
			}
			else{
				this.tab[i] = false;
			}
		}
	}

	/**
	 * this devient la différence de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void difference(L3Collection<Integer> set2) {
		for(int i = 0 ; i < SET_SIZE ; i++) {
			if(set2.contains(i)){
				this.tab[i] = false;
			}
		}
	}

	/**
	 * this devient la différence symétrique de this et set2
	 * 
	 * @param set2 deuxième ensemble
	 */
	@Override
	public void symmetricDifference(L3Collection<Integer> set2) {
		SmallSet tabSave = this;
		this.union(set2);
		tabSave.intersection(set2);
		this.difference(tabSave);	
	}

	/**
	 * this devient le complément de this.
	 */
	public void complement() {
		for(int i =0 ; i < SET_SIZE ; i++) {
			if(this.tab[i]) {
				this.tab[i] = false;
			}
			else {
				this.tab[i] = true;
			}
		}
	}

	/**
	 * this devient l'ensemble vide.
	 */
	@Override
	public void clear() {
		for(int i=0 ; i < SET_SIZE ; i++) {
			this.tab[i] = false;
		}
	}

	/**
	 * @param set2 second ensemble
	 * @return true, si this est inclus dans set2, false sinon
	 */
	@Override
	public boolean isIncludedIn(L3Collection<Integer> set2) {
		for(int i = 0 ; i < SET_SIZE ; i++) {
			if(tab[i] && !set2.contains(i)){
				return false;
			}
		}
		return true;
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
