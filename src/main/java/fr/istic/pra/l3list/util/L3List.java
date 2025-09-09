package fr.istic.pra.l3list.util;

import fr.istic.pra.lang.L3Collection;
import fr.istic.pra.lang.L3Iterator;

/**
 * liste en double chainage par references
 */
public class L3List<T> implements L3Collection<T>{

	/**
	 * Premier élément de la liste
	 */
	private Element flag;

	/**
	 * Un élément d'une liste chainé connait sa valeur son voisin de gauche et son
	 * voisin de droite
	 */
	private class Element {
		public T value;
		public Element left, right;
	}

	/**
	 * Iterateur specifique au L3LinkedList
	 */
	public class L3ListIterator implements L3Iterator<T> {
		private Element current;

		/**
		 * Place l'élément courant sur les premier élément a droite du flag,
		 * donc le prmeier élément a avoir une valeur
		 */
		public L3ListIterator() {
			// TODO: écrire le constructeur de L3ListIterator
		}

		/**
		 * passe au pochain élément de la liste
		 */
		public void goForward() {
			// TODO: écrire la méthode goForward()
		}

		/**
		 * passe a l'élément précédent de la liste
		 */
		public void goBackward() {
			// TODO: écrire la méthode goBackward()
		}

		/**
		 * place le current sur le premier élement après la sentinelle
		 */
		@Override
		public void restart() {
			// TODO: écrire la méthode restart()
		}

		/**
		 * 
		 * @return true si l'élément courant est le flag, false sinon
		 */
		public boolean isOnFlag() {
			// TODO: écrire la méthode isOnFlag
			return false;
		}

		/**
		 * enlève un élement de la liste et déplace le curseur vers l'élément suivant
		 */
		@Override
		public void remove() {
			// TODO: écrire la méthode remove()
		}

		/**
		 * 
		 * @return le valeur de l'élément courant
		 */
		@Override
		public T getValue() {
			// TODO: écrire la méthode getValue()
			return null;
		}

		/**
		 * Avance le curseur sur l'élément suivant
		 * 
		 * @return la valeur de l'élément passé
		 */
		@Override
		public T nextValue() {
			// TODO: écrire la méthode nextValue()
			return null;
		}

		/**
		 * Ajoute un élément à gauche de l'élément courant
		 * 
		 * @param var1 élement à ajouter
		 */
		public void addLeft(T v) {
			// TODO: écrire la méthode addLeft(value)
		}

		/**
		 * Ajoute un élément à droite de l'élément courant
		 * 
		 * @param var1 élement à ajouter
		 */
		public void addRight(T v) {
			// TODO: écrire la méthode addRight(value)
		}

		/**
		 * initialise la valeur de l'élement courant
		 * 
		 * @param var1 valeur de l'élément à initialiser
		 */
		public void setValue(T v) {
			// TODO: écrire la méthode setValue(value)
		}
	}

	/**
	 * Créer une nouvelle L3LinkedList avec uniquement un flag qui a comme left et right lui même
	 */
	public L3List() {
		// TODO: écrire le constrcteur de L3List
	}

	/**
	 * créer un nouvel iterateur sur this
	 * 
	 * @return
	 */
	public L3ListIterator l3Iterator() {
		// TODO: écrire la méthode l3Iterator
		return null;
	}

	/**
	 * Vérifie si la liste contient uniquement un flag
	 * 
	 * @return true si il y a seulement le flag
	 */
	public boolean isEmpty() {
		// TODO: écrire la méthode isEmpty()
		return false;
	}

	/**
	 * Déreférence tous les éléments de la liste sauf le flag
	 * Réinitialise tous les itérateurs
	 */
	public void clear() {
		// TODO: écrire la méthode clear()
	}

	@Override
	public boolean contains(T value) {
		// TODO: écrire la méthode contains(value)
		return false;
	}

	@Override
	public void remove(T value) {
			// TODO: écrire la méthode remove(value)
		}

	@Override
	public int size() {
		// TODO: écrire la méthode size()
		return -1;
	}

	/**
	 * Ajoute un élement après le flag
	 * 
	 * @param v élément à ajouter
	 */
	public void addHead(T v) {
		// TODO: écrire la méthode addHead(value)
	}

	/**
	 * Ajoute un élément avant le flag
	 * 
	 * @param v élément à ajouter
	 */
	public void addTail(T v) {
		// TODO: écrire la méthode addTail(value)
	}

	/**
	 * Par contrat avec L3Collection, L3List doit 
	 * avoir un add par défaut
	 */
	@Override
	public void add(T value) {
		this.addTail(value);
	}

	/**
	 * Initialise la valeur du flag
	 * @param v
	 */
	public void setFlag(T v) {
		// TODO: écrire la méthode setFlag(value)
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		L3ListIterator p = l3Iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}