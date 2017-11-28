package de.hu_berlin.cchecker.core.learning.alergia.tests;

import org.junit.Test;

import de.hu_berlin.cchecker.core.learning.alergia.ProbabilisticTrie;

public class ConcreteExampleTest extends AbstractBasicTest {

  @Test
  public void testEmptyTree() {
    ProbabilisticTrie trie = givenIHaveAProbabilisticTrie();
    thenTheNumberOfNodesOfTheTrie_willBe(trie, 0);
  }

}
