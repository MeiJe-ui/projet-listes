package fr.istic.pra.l3list;

// import fr.istic.pra.l3list.util.L3List; // a décommenter en partie 3.2
import fr.istic.pra.list.util.L3List; // a commenté en partie 3.2
import fr.istic.pra.list.SmallSet; // a commenté en partie 3.3
import fr.istic.pra.list.SubSet; // a commenté en partie 3.2
import fr.istic.pra.lang.L3Collection;
import fr.istic.pra.lang.L3Iterator;

public class MySet extends L3List<SubSet>  {

	/**
	 * Borne superieure pour les rangs des sous-ensembles.
	 */
	public static final int MAX_RANG = Integer.MAX_VALUE/SmallSet.SET_SIZE;
	public L3ListIterator it;

	/**
	 * Créer un MySet comme étant une liste chainé avec un Subset qui sert de
	 * flag.
	 */
	public MySet() {
		super();
		super.setFlag(new SubSet(MAX_RANG, new SmallSet()));
		this.it = super.l3Iterator();
	}

	// -------------------------------------------------------------------------- //
	// --------------- Appartenance, Ajout, Suppression, Cardinal --------------- //
	// -------------------------------------------------------------------------- //

	/**
	 * @param value valeur à tester
	 * @return true si valeur appartient à l'ensemble, false sinon
	 */
	public boolean contains(int value) {
		// TODO: écrire la methode contains(value)
		return false;
	}

	/**
	 * Vérifie si un SubSet avec un rang specifique existe
	 * @return true si un subset avec ce rang existe peut 
	 * importe le contenu de son SmallSet
	 */
	@Override
	public boolean contains(SubSet set) {
		// TODO: écrire la méthode contains(subset)
		return false;
	}

	/**
	 * Ajouter element à this,
	 *
	 * @param value valuer à ajouter.
	 */
	public void add(int value) {
		// TODO: écrire la méthode add(value)
	}

	/**
	 * Ajoute un SubSet au bon endroit dans le MySet
	 */
	@Override
	public void add(SubSet subset) {
		// TODO: écrire la méthode add(subset)
	}

	/**
	 * Supprimer element de this.
	 * 
	 * @param element valeur à supprimer
	 */
	public void remove(int value) {
		// TODO: écrire la méthode remove(value)
	}

	@Override
	public void remove(SubSet subset) {
		// TODO: écrire la méthode remove(subset)
	}

	/**
	 * @return nombre de valeur dans l'ensemble this
	 */
	@Override
	public int size() {
		// TODO: écrire la méthode size()
		return -1;
	}

	/**
	 * @param set2 deuxième ensemble
	 * @return true si this est inclus dans set2, false sinon
	 */
	@Override
	public boolean isIncludedIn(L3Collection<SubSet> set2) {
		// TODO: écrire la méthode isIncludedIn(set)
		return false;
	}

	// -------------------------------------------------------------------------- //
	// ------------------------ Difference, Intersection ------------------------ //
	// -------------------------------------------------------------------------- //

	/**
	 * This devient la différence de this et set2.
	 * 
	 * @param otherSet deuxième ensemble
	 */
	@Override
	public void difference(L3Collection<SubSet> otherSet) {
		// TODO: écrire la méthode difference(set)
	}

	/**
	 * This devient l'intersection de this et set2.
	 * 
	 * @param otherSet deuxième ensemble
	 */
	@Override
	public void intersection(L3Collection<SubSet> otherSet) {
		// TODO: écrire la méthode intersection(set)
	}

	// -------------------------------------------------------------------------- //
	// --------------------------- Affichage ----------------------------------- //
	// -------------------------------------------------------------------------- //

	/**
	 * @return l'ensemble this sous forme de chaîne de caractères.
	 */
	@Override
	public String toString() {
		if (this.isEmpty())
			return "";
		StringBuilder result = new StringBuilder();
		int count = 0;
		it.restart();
		while (!it.isOnFlag()) {
			result.append(formatSubSet(it.getValue(), count));
			it.goForward();
		}
		return result.toString();
	}

	private String formatSubSet(SubSet subSet, int count) {
		StringBuilder result = new StringBuilder();
		int startValue = subSet.getRank() * 256;
		for (int i = 0; i < 256; ++i) {
			if (subSet.getSet().contains(i)) {
				StringBuilder number = new StringBuilder(String.valueOf(startValue + i));
				int numberLength = number.length();
				for (int j = 6; j > numberLength; --j) {
					number.append(" ");
				}
				result.append(number);
				count++;
				if (count == 10) {
					result.append("\n");
					count = 0;
				}
			}
		}
		return result.toString();
	}
}
