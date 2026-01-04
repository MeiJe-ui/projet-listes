package fr.istic.pra.list;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import fr.istic.pra.benchmark.L3CollectionInteractions;
import fr.istic.pra.l3list.MySet;
import fr.istic.pra.lang.L3Iterator;
import fr.istic.pra.l3list.SmallSet; // a décommenter en partie 3.3
import fr.istic.pra.l3list.SubSet; // a décommenter en partie 3.2
import fr.istic.pra.l3list.util.L3List; // a décommenter en partie 3.2
// import fr.istic.pra.list.util.L3List; // a commenter en partie 3.2

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestMySet {
	public static final String ENS0 = "src/main/resources/f0.ens";
	public static final String ENS1 = "src/main/resources/f1.ens";
	public static final String ENS3 = "src/main/resources/f3.ens";
	public static final String TEST_U01 = "src/main/resources/test-u01.ens";
	public static final String TEST_D01 = "src/main/resources/test-d01.ens";
	public static final String TEST_S01 = "src/main/resources/test-s01.ens";
	public static final String TEST_D03 = "src/main/resources/test-d03.ens";
	public static final String TEST_I03 = "src/main/resources/test-i03.ens";
	public static final String TEST_U03 = "src/main/resources/test-u03.ens";

	private static L3CollectionInteractions<Integer> interactions = new L3CollectionInteractions<>(Integer::parseInt, -1, MySet.MAX_RANG*SmallSet.SET_SIZE);

	/**
	 * @param l1 premier ensemble
	 * @param l2 deuxième ensemble
	 * @return true si les ensembles l1 et l2 sont égaux, false sinon
	 */
	public static boolean compareMySets(MySet l1, MySet l2) {
		L3Iterator<SubSet> it1 = l1.l3Iterator();
		L3Iterator<SubSet> it2 = l2.l3Iterator();
		boolean bool = true;
		while (!it1.isOnFlag() && bool) {
			SubSet s1 = it1.getValue();
			SubSet s2 = it2.getValue();
			if (!compareSubSets(s1, s2)) {
				bool = false;
			}
			it1.goForward();
			it2.goForward();
		}
		return bool && it1.isOnFlag() && it2.isOnFlag();
	}

	public static boolean compareSubSets(SubSet s1, SubSet s2) {
		return s1.getRank() == s2.getRank() && compareSmallSets(s1.getSet(), s2.getSet());
	}

	public static boolean compareSmallSets(SmallSet s1, SmallSet s2) {
		return !(s1.size() == 0 || s2.size() == 0) && s1.toString().equals(s2.toString());
	}

	/**
	 * @param mySet ensemble à tester
	 * @return true si mySet est bien un ensemble creux
	 */
	public static boolean testSparsity(MySet mySet) {
		L3Iterator<SubSet> it = mySet.l3Iterator();
		while (!it.isOnFlag() && it.getValue().getSet().size() != 0) {
			it.goForward();
		}
		return it.isOnFlag();
	}

	private static MySet readFileToMySet(String fileName) {
		MySet set = new MySet();
		try {
			Scanner sc = new Scanner(new FileInputStream(fileName));
			interactions.addReadValues(sc, set::add);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Test
	public void testSetCreation() {
		MySet mySet1 = readFileToMySet("src/main/resources/test-desordre.ens");
		MySet mySet2 = readFileToMySet(ENS0);
		assertTrue("set creation in disorder", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testContainment1() {
		MySet mySet = readFileToMySet(ENS0);
		boolean bool1 = mySet.contains(128);
		boolean bool2 = mySet.contains(129);
		boolean bool3 = mySet.contains(32767);
		boolean bool4 = mySet.contains(22222);
		assertTrue("appartenance 1", bool1 && !bool2 && bool3 && !bool4);
	}

	@Test
	public void testContainment2() {
		MySet mySet = readFileToMySet(ENS0);
		boolean bool = mySet.contains(32511);
		assertTrue("appartenance 2", !bool);
	}

	@Test
	public void testSetAddition() throws FileNotFoundException {
		MySet mySet1 = readFileToMySet(ENS0);
		Scanner sc = new Scanner(new FileInputStream(ENS1));
		MySet mySet2 = readFileToMySet(TEST_U01);
		interactions.addReadValues(sc, mySet1::add);
		sc.close();
		assertTrue("set addition f0 f1", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval1() {
		MySet mySet1 = readFileToMySet(ENS0);
		mySet1.remove(64);
		mySet1.remove(32767);
		MySet mySet2 = readFileToMySet("src/main/resources/test-d05.ens");
		assertTrue("deletion sparsity 1", testSparsity(mySet1));
		assertTrue("deletion 1", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval2() {
		MySet mySet1 = new MySet();
		mySet1.add(0);
		mySet1.add(512);
		MySet mySet2 = new MySet();
		mySet2.add(0);
		mySet2.add(512);

		mySet1.remove(256);
		assertTrue("deletion sparsity 2", testSparsity(mySet1));
		assertTrue("deletion 2", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval3() {
		MySet mySet1 = new MySet();
		mySet1.add(64);
		mySet1.remove(64);
		assertTrue("deletion sparsity 3", testSparsity(mySet1));
		assertTrue("deletion 3", mySet1.isEmpty());
	}

	@Test
	public void testRemoval4() {
		MySet mySet1 = new MySet();
		mySet1.add(64);
		mySet1.add(3333);
		mySet1.remove(64);
		mySet1.remove(3333);
		assertTrue("deletion sparsity 4", testSparsity(mySet1));
		assertTrue("deletion 4", mySet1.isEmpty());
	}

	@Test
	public void testRemoval5() throws FileNotFoundException {
		MySet mySet1 = readFileToMySet(ENS0);
		Scanner sc = new Scanner(new FileInputStream(new File(ENS1)));
		interactions.removeReadValues(sc, mySet1::remove);
		sc.close();
		MySet mySet2 = readFileToMySet(TEST_D01);
		assertTrue("deletion sparsity 5", testSparsity(mySet1));
		assertTrue("deletion 5", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval6() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS0);
		mySet1.remove(4744);
		assertTrue("deletion sparsity 6", testSparsity(mySet1));
		assertTrue("deletion 6", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testSize1() {
		MySet mySet = readFileToMySet(ENS0);
		int size = mySet.size();
		assertEquals(14, size);
	}

	@Test
	public void testSize2() {
		MySet mySet = new MySet();
		((L3Iterator<SubSet>)mySet.l3Iterator()).getValue().getSet().add(22);
		int size = mySet.size();
		assertEquals(0, size);
	}

	@Test
	public void testInclusion2() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS3);
		assertTrue("inclusion f3 in f0", !mySet2.isIncludedIn(mySet1));
	}

	@Test
	public void testInclusion3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);
		mySet1.add(101);
		mySet1.add(300);

		mySet2.add(100);
		mySet2.add(300);

		assertTrue("inclusion 3", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);
		mySet1.add(101);
		mySet1.add(300);

		mySet2.add(300);

		assertTrue("inclusion 4", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);
		mySet1.add(200);
		mySet1.add(300);

		mySet2.add(100);
		mySet2.add(200);

		assertTrue("inclusion 5", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion6() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(10);

		mySet2.add(1034);
		mySet2.add(5555);

		assertTrue("inclusion 6", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion7() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(11);
		mySet1.add(1000);

		mySet2.add(1000);
		mySet2.add(11);

		assertTrue("inclusion 7", mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion8() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(11);
		mySet1.add(1000);

		mySet2.add(1000);
		mySet2.add(2222);

		assertTrue("inclusion 8", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion9() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(11);
		mySet1.add(1000);

		mySet2.add(11);
		mySet2.add(2000);

		assertTrue("inclusion 9", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testDifference1() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS3);
		MySet mySet3 = readFileToMySet(TEST_D03);
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f3", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference2() {
		MySet mySet1 = readFileToMySet(ENS3);
		MySet mySet2 = readFileToMySet(ENS0);
		MySet mySet3 = readFileToMySet("src/main/resources/test-d30.ens");
		mySet1.difference(mySet2);
		assertTrue("difference f3 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference3() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS1);
		MySet mySet3 = readFileToMySet(TEST_D01);
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f1", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.add(100);
		mySet1.add(300);

		mySet2.add(100);

		mySet3.add(300);

		mySet1.difference(mySet2);
		assertTrue("difference 100+300 and 100", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);

		mySet2.add(301);
		mySet2.add(100);

		mySet1.difference(mySet2);
		assertTrue("difference 100 and 100+301", mySet1.isEmpty());
	}

	@Test
	public void testDifference6() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS0);
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f0 : version 1", mySet1.isEmpty());
	}

	@Test
	public void testDifference7() {
		MySet mySet1 = readFileToMySet(ENS0);
		mySet1.difference(mySet1);
		assertTrue("difference f0 and f0 : version 2", mySet1.isEmpty());
	}

	@Test
	public void testIntersection1() {
		MySet mySet1 = readFileToMySet(ENS0);
		MySet mySet2 = readFileToMySet(ENS3);
		MySet mySet3 = readFileToMySet(TEST_I03);
		mySet1.intersection(mySet2);
		assertTrue("intersection f0 and f3", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection2() {
		MySet mySet1 = readFileToMySet(ENS3);
		MySet mySet2 = readFileToMySet(ENS0);
		MySet mySet3 = readFileToMySet(TEST_I03);
		mySet1.intersection(mySet2);
		assertTrue("intersection f3 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.add(100);
		mySet1.add(300);

		mySet2.add(100);
		mySet2.add(301);

		mySet3.add(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+300 and 100+301", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);
		mySet1.add(300);

		mySet2.add(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+300 and 100", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testIntersection5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.add(100);

		mySet2.add(100);
		mySet2.add(301);

		mySet3.add(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100 and 100+301", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection6() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.add(100);
		mySet1.add(999);

		mySet2.add(200);
		mySet2.add(301);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+999 and 200+301", mySet1.isEmpty());
	}

	@Test
	public void testIntersection7() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.add(100);
		mySet1.add(999);

		mySet2.add(100);
		mySet2.add(301);

		mySet3.add(100);
		mySet3.add(301);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+999 and 100+301", compareMySets(mySet2, mySet3));
	}

	

	@Test
	public void testAddTail() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		int bigValue = 32000;

		mySet1.add(100);
		mySet1.add(10000);
		mySet1.add(bigValue);

		mySet2.add(100);
		mySet2.add(10000);

		SmallSet smallSet = new SmallSet();
		smallSet.add(bigValue % 256);
		SubSet subset = new SubSet(bigValue / 256, smallSet);
		mySet2.addTail(subset);
		assertTrue("addTail", compareMySets(mySet1, mySet2));
	}

}