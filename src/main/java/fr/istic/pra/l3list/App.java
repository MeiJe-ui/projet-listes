package fr.istic.pra.l3list;

import java.awt.Container;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import fr.istic.pra.benchmark.ActionList;
import fr.istic.pra.benchmark.BenchmarkAnyCollections;
import fr.istic.pra.benchmark.BenchmarkAnyL3Collection;
import fr.istic.pra.benchmark.BenchmarkData;
import fr.istic.pra.benchmark.L3CollectionInteractions;
import fr.istic.pra.lang.L3Iterator;
import fr.istic.pra.list.util.L3List; // a commenter en partie 3.2
// import fr.istic.pra.l3list.util.L3List; // a decommenter en partie 3.2
import fr.istic.pra.list.SubSet;// a commenter en partie 3.2
public class App extends JFrame{
	private final static int MAX_SET = 5;
	private ArrayList<MySet> l3SetList = new ArrayList<>(MAX_SET);
	private L3CollectionInteractions<Integer> l3SetInteractions;

	private Box boxEns = Box.createVerticalBox();
	private Box boxChart = Box.createVerticalBox();

	private Scanner standardInput = new Scanner(System.in);
	private final String FONT = "Verdana";

	/**
	 * Création de la frame
	 * 
	 * @param name nom de l'application
	 * @param l3SetSupplier type de L3Set manipulé
	 */
	private App(String name) {
		super(name);
		l3SetInteractions = new L3CollectionInteractions<>(Integer::parseInt, -1, MySet.MAX_RANG*SmallSet.SET_SIZE);
		for (int i = 0; i < MAX_SET; ++i) {
			l3SetList.add(new MySet());
		}
	}

	/**
	 * Create the GUI and show it. For thread safety,
	 * this method is invoked from the
	 * event dispatch thread.
	 */
	public void createAndShowGUI() {
		// Create and set up the window.
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// Set up the content pane.
		this.addComponentsToPane(this.getContentPane());
		// Display the window.
		this.pack();
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				standardInput.close();
			}
		});
	}

	/**
	 * Ajout des boutons, de l'affichage des ensembles (vides) et de l'affichage du
	 * graph (vide)
	 * 
	 * @param pane
	 */
	private void addComponentsToPane(final Container pane) {
		// left: buttons of action
		// right strings and plots
		Box layout = Box.createHorizontalBox();
		pane.add(layout);

		// Create a button for each action possible on a L3Set
		Box boxButtons = Box.createVerticalBox();
		
		JButton initBenchMark = new JButton("Initialiser le benchmark");
		initBenchMark.setFont(new Font(FONT, Font.PLAIN, 14));
		initBenchMark.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.this.setVisible(false);
				App.this.createBenchmark();
				Component[] comps = boxButtons.getComponents();
				for (Component comp: comps) {
					comp.setEnabled(true);
				}
				App.this.setVisible(true);
			}
		});
		boxButtons.add(initBenchMark);

		boxButtons.add(new InteractAndPlotButtons(ActionList.ADD, "Ajouter", "ajouter des valeurs a l'ensemble numero n1"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.REMOVE, "Retirer", "retirer des valeurs a l'ensemble numero n1"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.CONTAINS, "Appartient",
				"determiner si x appartient a l'ensemble numero n1"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.SIZE, "Taille", "afficher le cardinal de l'ensemble numero n1"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.UNION, "Union",
				"l'ensemble numero n1 <-- union de l'ensemble numero n1 et l'ensemble numero n2"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.INTERSECTION, "Intersection",
				"l'ensemble numero n1 <-- intersection de l'ensemble numero n1 et l'ensemble numero n2"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.DIFFERENCE, "Difference",
				"l'ensemble numero n1 <-- difference l'ensemble numero n1 et l'ensemble numero n2)"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.SYMDIFF, "Difference symetrique",
				"l'ensemble numero n1 <-- difference symetrique de l'ensemble numero n1 et l'ensemble numero n2"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.EQUALS, "Egalité",
				"determiner si l'ensemble numero n1 et l'ensemble numero n2 sont egaux"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.INCLUDE, "Inclus",
				"determiner si l'ensemble numero n1 est inclus dans l'ensemble numero n2"));
		boxButtons.add(new InteractAndPlotButtons(ActionList.RESET, "Reinitialiser",
				"reinitialiser l'ensemble numero n1 a partir d'un fichier"));
		boxButtons.add(
				new InteractAndPlotButtons(ActionList.SAVE, "Sauvegarder", "sauvegarder l'ensemble numero n1 dans un fichier"));
		layout.add(boxButtons);

		Box boxVisu = Box.createVerticalBox();

		// Display all the MySet created and their values
		updateL3SetDisplay();
		boxVisu.add(boxEns);

		// Display perfomances chart when possible
		updateChart(new XChartPanel<>(new XYChartBuilder().width(800).height(500).build()));
		boxVisu.add(boxChart);

		layout.add(boxVisu);
	}

	/**
	 * Met à jour l'affichage des MySet
	 */
	private void updateL3SetDisplay() {
		boxEns.removeAll();
		JLabel titleEns = new JLabel("Visualisation des ensembles de tests");
		titleEns.setFont(new Font(FONT, Font.PLAIN, 18));
		boxEns.add(titleEns);
		for (int i = 0; i < MAX_SET; ++i) {
			JLabel ensLabel = new JLabel("Ensemble " + i + " : " + l3SetList.get(i).toString());
			ensLabel.setFont(new Font(FONT, Font.PLAIN, 14));
			JLabel ensDetailsLabel = new JLabel("Détails : " + this.printMySetDetails(l3SetList.get(i)));
			ensDetailsLabel.setFont(new Font(FONT, Font.PLAIN, 14));
			boxEns.add(ensLabel);
			boxEns.add(ensDetailsLabel);
		}
	}

	private String printMySetDetails(MySet currentSet) {
		StringBuilder result = new StringBuilder();
		L3Iterator<SubSet> it = currentSet.l3Iterator();
		result.append("| ");
		while (!it.isOnFlag()) {
			result.append( it.getValue().getRank() + "->");
			result.append(it.getValue().getSet().toString() + " | ");
			it.goForward();
		}
		return result.toString();
	}

	/**
	 * Met à jour le graph lors d'un calcul de performance
	 * 
	 * @param title
	 * @param chart
	 */
	private void updateChart(XChartPanel<XYChart> chart) {
		boxChart.removeAll();
		boxChart.add(chart);
	}

	private BenchmarkAnyCollections<ArrayList<Integer>, Integer> arrayListBenchmark;
	private BenchmarkAnyCollections<LinkedList<Integer>, Integer> linkedListBenchmark;
	private BenchmarkAnyCollections<HashSet<Integer>, Integer> hashSetBenchmark;
	private BenchmarkAnyCollections<LinkedHashSet<Integer>, Integer> linkedHashSetBenchmark;
	private BenchmarkAnyL3Collection<MySet, SubSet> mysetBenchmark;

	private void createBenchmark() {
		// Création de benchmark sur toutes les collections intéressantes
		long start = System.currentTimeMillis();
		Random random = new Random();
		arrayListBenchmark = new BenchmarkAnyCollections<>(ArrayList::new, ()-> random.nextInt());
		linkedListBenchmark = new BenchmarkAnyCollections<>(LinkedList::new, ()-> random.nextInt());
		hashSetBenchmark = new BenchmarkAnyCollections<>(HashSet::new, ()-> random.nextInt());
		linkedHashSetBenchmark = new BenchmarkAnyCollections<>(LinkedHashSet::new, ()-> random.nextInt());
		mysetBenchmark = new BenchmarkAnyL3Collection<>(MySet::new, SubSet::new);
		long end = System.currentTimeMillis();
		System.out.println("Temps de création du benchmark : " + (end - start));
	}

	/**
	 * Créer un ensemble de boutons d'actions qui font appel aux fonctionnalitées de
	 * MySet
	 * et qui calcul les performances quand c'est possible
	 */
	private class InteractAndPlotButtons extends Box {
		private String command;
		private JButton interactionButton;
		private JButton plotButton;
		private ActionList type;

		@Override
		public void setEnabled(boolean b) {
			if (plotButton != null) plotButton.setEnabled(true);
		}

		public InteractAndPlotButtons(ActionList type, String name, String command) {
			// alignement horizontal
			super(BoxLayout.LINE_AXIS);

			this.command = command;
			this.type = type;

			// Création des boutons
			interactionButton = new JButton(name);
			interactionButton.setFont(new Font(FONT, Font.PLAIN, 14));
			// Association d'action au bouton
			this.associateInteractionToButton();
			this.add(interactionButton);
			if (type != ActionList.SAVE && type != ActionList.RESET) {
				plotButton = new JButton("Performances");
				plotButton.setFont(new Font(FONT, Font.PLAIN, 14));
				if (mysetBenchmark == null) plotButton.setEnabled(false);
				// Association d'action au bouton
				this.associatePlottingToButton();
				this.add(plotButton);
			}
		}

		private void associateInteractionToButton() {
			this.interactionButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					App.this.setVisible(false);
					System.out.println("* " + InteractAndPlotButtons.this.command);
					int n1;
					int n2;
					boolean b;
					int setNumber;
					switch (type) {
						case ADD:
							setNumber = readSetNumber(1);
							System.out.println(" valeurs à ajouter (-1 pour finir) : ");
							l3SetInteractions.addReadValues(standardInput, l3SetList.get(setNumber)::add);
							break;
						case CONTAINS:
							setNumber = readSetNumber(1);
							System.out.println(" valeurs a chercher dans le set : " + setNumber);
							b = l3SetInteractions.containsReadValue(l3SetList.get(setNumber)::contains);
							if (b) {
								System.out.println(" valeur presente");
							} else {
								System.out.println(" valeur non presente");
							}
							break;
						case SIZE:
							n1 = readSetNumber(1);
							int size = l3SetList.get(n1).size();
							System.out.println("l'ensemble numero " + n1 + " a " + size + " elements");
							break;
						case DIFFERENCE:
							l3SetList.get(readSetNumber(1)).difference(l3SetList.get(readSetNumber(2)));
							break;
						case SYMDIFF:
							l3SetList.get(readSetNumber(1)).symmetricDifference(l3SetList.get(readSetNumber(2)));
							break;
						case EQUALS:
							n1 = readSetNumber(1);
							n2 = readSetNumber(2);
							b = l3SetList.get(n1).equals(l3SetList.get(n2));
							if (b) {
								System.out.println("les ensembles numero " + n1 + " et numero " + n2 + " sont egaux");
							} else {
								System.out.println("les ensembles numero " + n1 + " et numero " + n2 + " ne sont pas egaux");
							}
							break;
						case INCLUDE:
							n1 = readSetNumber(1);
							n2 = readSetNumber(2);
							b = l3SetList.get(n1).isIncludedIn(l3SetList.get(n2));
							if (b) {
								System.out.println("l'ensemble numero " + n1 + " est inclus dans l'ensemble numero " + n2);
							} else {
								System.out.println("l'ensemble numero " + n1 + " n'est pas inclus dans l'ensemble numero " +
										n2);
							}
							break;
						case INTERSECTION:
							l3SetList.get(readSetNumber(1)).intersection(l3SetList.get(readSetNumber(2)));
							break;
						case RESET:
							l3SetInteractions.restore(readFileName(), l3SetList.get(readSetNumber(1))::add);
							break;
						case REMOVE:
							setNumber = readSetNumber(1);
							System.out.println(" valeurs à supprimer (-1 pour finir) : ");
							l3SetInteractions.removeReadValues(standardInput, l3SetList.get(setNumber)::remove);
							break;
						case SAVE:
							l3SetInteractions.save(readFileName(), l3SetList.get(readSetNumber(1)));
							break;
						case UNION:
							l3SetList.get(readSetNumber(1)).union(l3SetList.get(readSetNumber(2)));
							break;
					}
					App.this.updateL3SetDisplay();
					App.this.setVisible(true);
				}
			
			
				/**
				 * @param i
				 * @return numéro de l'ensemble choisi par l'utilisateur
				 */
				private int readSetNumber(int i) {
					int number;
					boolean b;
					do {
						b = true;
						number = 0;
						System.out.println(" numero d'ensemble n" + i + " (>=0 et <" + MAX_SET + ") : ");
						try {
							number = standardInput.nextInt();
						} catch (NumberFormatException e) {
							b = false;
						}
					} while (!b || (number < 0 || number >= MAX_SET));
					return number;
				}

				/**
				 * @return nom de fichier saisi psar l'utilisateur
				 */
				private String readFileName() {
					System.out.println(" nom du fichier : ");
					return standardInput.next();
				}
			});
		}

		private void associatePlottingToButton() {
			this.plotButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					App.this.setVisible(false);
					
					long start = System.currentTimeMillis();
					BenchmarkData arrayListData = arrayListBenchmark.calculTimeForWholeBenchmark(InteractAndPlotButtons.this.type);
					BenchmarkData linkedListData = linkedListBenchmark.calculTimeForWholeBenchmark(InteractAndPlotButtons.this.type);
					BenchmarkData hashSetData = hashSetBenchmark.calculTimeForWholeBenchmark(InteractAndPlotButtons.this.type);
					BenchmarkData linkedHashSetData = linkedHashSetBenchmark.calculTimeForWholeBenchmark(InteractAndPlotButtons.this.type);
					BenchmarkData mysetData = mysetBenchmark.calculTimeForWholeBenchmark(InteractAndPlotButtons.this.type);
					long end = System.currentTimeMillis();
					System.out.println("Temps de calcul du benchmark pour " + type + " : " + (end-start));
					
					// Création du plot des temps de chaques benchmark
					XYChart chart = new XYChartBuilder()
						.width(800)
						.height(600)
						.title("Performances de la fonction " + InteractAndPlotButtons.this.type)
						.xAxisTitle("Nombre d'éléments")
						.yAxisTitle("Temps en nano secondes").build();
					chart.addSeries("Java ArrayList", arrayListData.nbElements, arrayListData.temps);
					chart.addSeries("Java LinkedList", linkedListData.nbElements, linkedListData.temps);
					chart.addSeries("Java HashSet", hashSetData.nbElements, hashSetData.temps);
					chart.addSeries("Java LinkedHashSet", linkedHashSetData.nbElements, linkedHashSetData.nbElements);
					chart.addSeries("L3Set", mysetData.nbElements, mysetData.temps);
					updateChart(new XChartPanel<>(chart));
					
					App.this.setVisible(true);
				}
			});
		}
	}
    public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}   
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				App ui = new App("TP Listes et Ensembles");
				ui.createAndShowGUI();
			}
		});
	}
}
