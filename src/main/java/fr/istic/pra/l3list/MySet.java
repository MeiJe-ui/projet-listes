package fr.istic.pra.l3list;

import fr.istic.pra.l3list.util.L3List; // a décommenter en partie 3.2
//import fr.istic.pra.list.util.L3List; // a commenté en partie 3.2
//import fr.istic.pra.list.SmallSet; // a commenté en partie 3.3
// import fr.istic.pra.list.SubSet; // a commenté en partie 3.2

import java.lang.Character.Subset;

import fr.istic.pra.lang.L3Collection;
import fr.istic.pra.lang.L3Iterator;

public class MySet extends L3List<SubSet>  {

	/**
	 * Borne superieure pour les rangs des sous-ensembles.
	 */
	public static final int MAX_RANG = Integer.MAX_VALUE/SmallSet.SET_SIZE;
	public L3Iterator<SubSet> it;

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
		int rank = value/SmallSet.SET_SIZE;
		int modulo = value%SmallSet.SET_SIZE;
		
		SmallSet smallSetMod = new SmallSet();
		smallSetMod.add(modulo); 
		SubSet subsetVerif = new SubSet(rank, smallSetMod);
		
		if(contains(subsetVerif)){
			return it.getValue().getSet().contains(modulo);
		}
		else{
			return false;
		}
	}

	/**
	 * Vérifie si un SubSet avec un rang specifique existe
	 * @return true si un subset avec ce rang existe peut 
	 * importe le contenu de son SmallSet
	 */
	@Override
	public boolean contains(SubSet set) {
		int rankSet = set.getRank();
		it.restart();
		it.goBackward();
		it.goBackward();
		if(it.getValue().getRank() < rankSet ){
			return false;
		}
		else{
			while(it.getValue().getRank() != rankSet) {
				if(it.isOnFlag()){
					return false;
				}
				it.goBackward();
			}
			return true;
		}
	}

	/**
	 * Ajouter element à this,
	 *
	 * @param value valuer à ajouter.
	 */
	public void add(int value) {
		int rank = value/SmallSet.SET_SIZE;
		int modulo = value%SmallSet.SET_SIZE;
		SmallSet smallSetAdd = new SmallSet();
		smallSetAdd.addInterval(modulo,modulo);

		SubSet subAdd = new SubSet(rank,smallSetAdd);
		add(subAdd);
	}

	/**
	 * Ajoute un SubSet au bon endroit dans le MySet
	 */
	@Override
	public void add(SubSet subset) {
		int rank = subset.getRank();
		if(rank > MAX_RANG) {
			System.out.println("erreur rang > MAX_RANG");
			return;
		}

		it.restart();

		while(it.getValue().getRank() < rank) {
			if(it.isOnFlag()){
				break;
			}
			it.goForward();
		}
		if(it.getValue().getRank() == rank) {
			it.getValue().getSet().union(subset.getSet());
		}
		else {
			it.addLeft(subset);
		}
	}

	/**
	 * Supprimer element de this.
	 * 
	 * @param element valeur à supprimer
	 */
	public void remove(int value) {
		int rank = value/SmallSet.SET_SIZE;
		int modulo = value%SmallSet.SET_SIZE;
		it.restart();
		while(it.getValue().getRank() != rank) {
			if(it.isOnFlag()){
				return;	
			}
			it.goForward();
		}
		it.getValue().getSet().remove(modulo);
		if(it.getValue().getSet().isEmpty()) {
			it.remove();
		}
	}

	@Override
	public void remove(SubSet subset) {
		int rankSet = subset.getRank();
		it.restart();
		while(it.getValue().getRank() != rankSet) {
			if(it.isOnFlag()){
				return;	
			}
			it.goForward();
		}
		it.remove();
		
	}

	/**
	 * @return nombre de valeur dans l'ensemble this
	 */
	@Override
	public int size() {
		int sizeMylist=0;
		it.restart();
		while(!it.isOnFlag()) {
			sizeMylist += it.getValue().getSet().size();
			it.goForward();
		}
		return sizeMylist;
	}

	/**
	 * @param set2 deuxième ensemble
	 * @return true si this est inclus dans set2, false sinon
	 */
	@Override
	public boolean isIncludedIn(L3Collection<SubSet> set2) {
		L3Iterator<SubSet> it2 = set2.l3Iterator();
		it.restart();
		it2.restart();

		while(! it.isOnFlag()) {
			
			if(it2.getValue().getRank() < it.getValue().getRank()){
				
				do{
					it2.goForward();
				} while(it2.getValue().getRank() < it.getValue().getRank());
			}
			
			if(it.getValue().getRank() == it2.getValue().getRank()) {
				if( ! it.getValue().getSet().isIncludedIn(it2.getValue().getSet()) ) {
					return false;
				}
				it.goForward();
			}

			if(it2.getValue().getRank() > it.getValue().getRank()) {
				return false;
			}

		}

		return true;
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
		L3Iterator<SubSet> it2 = otherSet.l3Iterator();
		it.restart();
		it2.restart();

		while(!(it.isOnFlag() || it2.isOnFlag())){
			
			int rankSet1 = it.getValue().getRank();
			int rankSet2 = it2.getValue().getRank();

			if(rankSet2 < rankSet1) {
				it2.goForward();
			}

			if(rankSet1 < rankSet2) {
				it.goForward();
			}

			if(rankSet1 == rankSet2) {
				it.getValue().getSet().difference(it2.getValue().getSet());
				
				if(it.getValue().getSet().isEmpty()) {
					this.remove(it.getValue());
					it.goBackward();
				}
				
				it.goForward();
			}

		}

	}

	/**
	 * This devient l'intersection de this et set2.
	 * 
	 * @param otherSet deuxième ensemble
	 */
	@Override
	public void intersection(L3Collection<SubSet> otherSet) {
		L3Iterator<SubSet> it2 = otherSet.l3Iterator();
		it.restart();
		it2.restart();

		while(!(it.isOnFlag())){
			
			int rankSet1 = it.getValue().getRank();
			int rankSet2 = it2.getValue().getRank();

			
			if(rankSet2 < rankSet1) {
				it2.goForward();
			}

			if(it2.isOnFlag()) {
				do {
					it.remove();
				}while(!(it.isOnFlag()));
				return;
			}
			
			if(rankSet1 < rankSet2) {
				it.remove();
			}

			if(rankSet1 == rankSet2) {
				it.getValue().getSet().intersection(it2.getValue().getSet());
				if(it.getValue().getSet().isEmpty()) {
					it.remove();
					it.goBackward();
				}
				it.goForward();
			}

		}
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
