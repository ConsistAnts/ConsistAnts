package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.HashSet;

public class ProbabilisticTrie {

  private ProbabilisticTrieNode root;
  private HashSet<Integer> alphabet = new HashSet<Integer>();

  public ProbabilisticTrie() {
    this.root = new ProbabilisticTrieNode();
  }

  public HashSet<Integer> getAlphabet() {
    return this.alphabet;
  }

  public void insert(Integer[] sequence) {
    ProbabilisticTrieNode node = this.root;
    Integer symbol;
    ProbabilisticTrieNode nextNode;
    for (int i = 0; i < sequence.length; i++) {
      symbol = sequence[i];
      this.alphabet.add(symbol);
      node.arriving++;
      nextNode = node.getSuccessorNode(symbol, true);
      node.successorFrequencies.put(symbol, node.successorFrequencies.get(symbol)+1);
      node = nextNode;
    }
    node.arriving++;
    node.terminating++;
  }

  public boolean search(Integer[] sequence) {
    ProbabilisticTrieNode node = this.getNode(sequence);
    return node != null && node.isWord();
  }

  public ProbabilisticTrieNode getNode(Integer[] sequence) {
    ProbabilisticTrieNode node = this.root;
    Integer symbol;
    for (int i = 0; i < sequence.length; i++) {
      symbol = sequence[i];
      node = node.successor.get(symbol);
      if (node == null) {
        return null;
      }
    }
    return node;
  }

  public int numberOfNodes() {
    int number = this.root.numberOfTreeNodes();
    return (number == 1 && !this.root.isWord()) ? 0 : number;
  }

  public ProbabilisticTrieNode[] getAllNodesDepthFirst() {
    return this.root.getAllNodesDepthFirst();
  }

  public void printLexikographically() {
    this.root.printTreeLexikographically();
  }

  public void printDepthFirst() {
    this.root.printTreeDepthFirst();
  }

  public ProbabilisticTrieNode getRoot() {
    return this.root;
  }

  //TODO: equals

  //TODO: toString

}
