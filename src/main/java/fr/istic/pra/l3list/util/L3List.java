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
	private class L3ListIterator implements L3Iterator<T> {
		private Element current;

		/**
		 * Place l'élément courant sur les premier élément a droite du flag,
		 * donc le prmeier élément a avoir une valeur
		 */
		public L3ListIterator() {
			this.current = flag.right;
		}

		/**
		 * passe au pochain élément de la liste
		 */
		public void goForward() {
			this.current = current.right;
		}

		/**
		 * passe a l'élément précédent de la liste
		 */
		public void goBackward() {
			this.current = current.left;
		}

		/**
		 * place le current sur le premier élement après la sentinelle
		 */
		@Override
		public void restart() {
			this.current = flag.right;
		}

		/**
		 * 
		 * @return true si l'élément courant est le flag, false sinon
		 */
		public boolean isOnFlag() {
			return (current == flag);
		}

		/**
		 * enlève un élement de la liste et déplace le curseur vers l'élément suivant
		 */
		@Override
		public void remove() {
			this.current.left.right = current.right;
			this.current.right.left = current.left;
			this.current= current.right;

		}

		/**
		 * 
		 * @return le valeur de l'élément courant
		 */
		@Override
		public T getValue() {
			return this.current.value;
		}

		/**
		 * Avance le curseur sur l'élément suivant
		 * 
		 * @return la valeur de l'élément passé
		 */
		@Override
		public T nextValue() {
			this.current = current.right;
			return current.left.value;
		}

		/**
		 * Ajoute un élément à gauche de l'élément courant
		 * 
		 * @param var1 élement à ajouter
		 */
		public void addLeft(T v) {
			Element addElement = new Element();

			addElement.value = v;

			addElement.left = current.left;
			addElement.right = current;

			current.left.right = addElement;
			current.left = addElement;
		}

		/**
		 * Ajoute un élément à droite de l'élément courant
		 * 
		 * @param var1 élement à ajouter
		 */
		public void addRight(T v) {
			Element addElement = new Element();
			addElement.value = v;

			addElement.left = current;
			addElement.right = current.right;

			current.right.left = addElement;
			current.right = addElement;
		}

		/**
		 * initialise la valeur de l'élement courant
		 * 
		 * @param var1 valeur de l'élément à initialiser
		 */
		public void setValue(T v) {
			current.value = v;
		}
	}

	/**
	 * Créer une nouvelle L3LinkedList avec uniquement un flag qui a comme left et right lui même
	 */
	public L3List() {
		this.flag = new Element();
		this.flag.right = this.flag;
		this.flag.left = this.flag;

	}

	/**
	 * créer un nouvel iterateur sur this
	 * 
	 * @return
	 */
	public L3ListIterator l3Iterator() {
		return new L3ListIterator();
	}

	/**
	 * Vérifie si la liste contient uniquement un flag
	 * 
	 * @return true si il y a seulement le flag
	 */
	public boolean isEmpty() {
		return (this.flag.right == flag && this.flag.left == flag);
	}

	/**
	 * Déreférence tous les éléments de la liste sauf le flag
	 * Réinitialise tous les itérateurs
	 */
	public void clear() {
		this.flag.right = flag;
		this.flag.left = flag;

		this.l3Iterator().restart();
	}

	@Override
	public boolean contains(T value) {
		L3ListIterator it = this.l3Iterator();
		it.restart();
		while(!it.isOnFlag()) {
			if(it.getValue() == value){ return true; }
			it.goForward();
		}
		return false;
	}

	@Override
	public void remove(T value) {
		L3ListIterator it = this.l3Iterator();
		it.restart();
		while(!it.isOnFlag()) {
			if(it.getValue() == value) {it.remove(); return;}
			it.goForward();
		}
	}

	@Override
	public int size() {
		L3ListIterator it = this.l3Iterator();
		it.restart();
		int compteur = 0;
		while(!it.isOnFlag()) {
			compteur++;
			it.goForward();
		}
		return compteur;
	}

	/**
	 * Ajoute un élement après le flag
	 * 
	 * @param v élément à ajouter
	 */
	public void addHead(T v) {
		Element addElement = new Element();
		addElement.value = v;
		
		addElement.right = this.flag.right;
		addElement.left = this.flag;
		
		this.flag.right.left = addElement;
		this.flag.right = addElement;
	}

	/**
	 * Ajoute un élément avant le flag
	 * 
	 * @param v élément à ajouter
	 */
	public void addTail(T v) {
		Element addElement = new Element();
		addElement.value = v;
		
		addElement.right = this.flag;
		addElement.left = this.flag.left;
		
		this.flag.left.right = addElement;
		this.flag.left = addElement;
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
		this.flag.value = v;
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