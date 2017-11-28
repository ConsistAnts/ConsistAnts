package de.hu_berlin.cchecker.core.learning.alergia.tests;

import org.junit.Test;

import de.hu_berlin.cchecker.core.learning.alergia.ProbabilisticTrie;

public class BasicTest extends AbstractBasicTest {

  @Test
  public void testEmptyTree() {
    ProbabilisticTrie trie = givenIHaveAProbabilisticTrie();
    thenTheNumberOfNodesOfTheTrie_willBe(trie, 0);
  }

  @Test
  public void testInsertEmptyWord() {
    Integer[] sequence = {};
    ProbabilisticTrie trie = givenIHaveAProbabilisticTrie();
    whenIInsertASequence_intoTheTrie(sequence, trie);
    thenTheNumberOfNodesOfTheTrie_willBe(trie, 1);
    thenTheWord_isInTheTrie(sequence, trie);
  }

  @Test
  public void testInsertNonEmptyWord() {
    ProbabilisticTrie trie = givenIHaveAProbabilisticTrie();
    Integer[] sequence = {1};
    whenIInsertASequence_intoTheTrie(sequence, trie);
    thenTheNumberOfNodesOfTheTrie_willBe(trie, 2);
    thenTheWord_isInTheTrie(sequence, trie);
    thenTheWord_isNotInTheTrie(new Integer[]{}, trie);
  }

}
