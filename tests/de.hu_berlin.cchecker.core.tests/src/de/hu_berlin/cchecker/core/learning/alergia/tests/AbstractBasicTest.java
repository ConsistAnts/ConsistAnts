package de.hu_berlin.cchecker.core.learning.alergia.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.hu_berlin.cchecker.core.learning.alergia.ProbabilisticTrie;

public abstract class AbstractBasicTest {

  protected ProbabilisticTrie givenIHaveAProbabilisticTrie() {
    return new ProbabilisticTrie();
  }

  protected void whenIInsertASequence_intoTheTrie(Integer[] sequence, ProbabilisticTrie trie) {
    trie.insert(sequence);
  }

  protected void thenTheNumberOfNodesOfTheTrie_willBe(ProbabilisticTrie trie, int expected) {
    assertEquals(expected, trie.numberOfNodes());
  }

  protected void thenTheWord_isInTheTrie(Integer[] sequence, ProbabilisticTrie trie) {
    assertTrue(trie.search(sequence));
  }

  protected void thenTheWord_isNotInTheTrie(Integer[] sequence, ProbabilisticTrie trie) {
    assertFalse(trie.search(sequence));
  }

}
