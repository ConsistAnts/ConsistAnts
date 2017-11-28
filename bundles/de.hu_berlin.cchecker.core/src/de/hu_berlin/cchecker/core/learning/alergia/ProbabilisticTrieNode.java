package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ProbabilisticTrieNode {

  SortedMap<Integer, ProbabilisticTrieNode> successor;
  SortedMap<Integer, Integer> successorFrequencies;
  int arriving = 0;
  int terminating = 0;
  boolean visited = false;
  int number;

  public ProbabilisticTrieNode() {
    this.successor = new TreeMap<>();
    this.successorFrequencies = new TreeMap<>();
  }

  public boolean isWord() {
    return this.terminating > 0;
  }

  public double getTerminatingProbability() {
    return this.arriving != 0 ? this.terminating / this.arriving : 0;
  }

  public double getSymbolProbability(int symbol) {
    int freq = this.successorFrequencies.get(symbol) == null ? 0 : this.successorFrequencies.get(symbol);
    return this.arriving != 0 ? freq / this.arriving : 0;
  }

  public ProbabilisticTrieNode getSuccessorNode(char symbol) {
    return getSuccessorNode(symbol, false);
  }

  public ProbabilisticTrieNode getSuccessorNode(int symbol, boolean addNodeIfNotExistent) {
    ProbabilisticTrieNode node = this.successor.get(symbol);
    if (node == null && addNodeIfNotExistent) {
      this.successor.put(symbol, new ProbabilisticTrieNode());
      this.successorFrequencies.put(symbol, 0);
      node = this.successor.get(symbol);
    }
    return node;
  }

  public int numberOfTreeNodes() {
    int number = 1;
    for (ProbabilisticTrieNode node : this.successor.values()) {
      number += node.numberOfTreeNodes();
    }
    return number;
  }

  public ProbabilisticTrieNode[] getAllNodesDepthFirst() {
    ProbabilisticTrieNode[] nodes = new ProbabilisticTrieNode[this.numberOfTreeNodes()];
    int i = 0;
    nodes[i++] = this;
    for (ProbabilisticTrieNode succ : this.successor.values()) {
      ProbabilisticTrieNode[] succNodes = succ.getAllNodesDepthFirst();
      for (ProbabilisticTrieNode foundNode : succNodes) {
        nodes[i++] = foundNode;
      }
    }
    return nodes;
  }

  public void printTreeLexikographically() {
    //TODO: yet only for two levels
    System.out.print("{\"\" [" + this.arriving + "," + this.terminating + "]");
    for (Map.Entry<Integer, ProbabilisticTrieNode> succ : this.successor.entrySet()) {
      succ.getValue().printTreeLexikographically(succ.getKey()+"");
    }
    System.out.println("}");
  }

  protected void printTreeLexikographically(String prefix) {
    System.out.print("; " + prefix + " [" + this.arriving + "," + this.terminating + "]");
  }

  public void printTreeDepthFirst() {
    System.out.print("{\"\" [" + this.arriving + "," + this.terminating + "]");
    for (Map.Entry<Integer, ProbabilisticTrieNode> succ : this.successor.entrySet()) {
      succ.getValue().printTreeDepthFirst("" + succ.getKey());
    }
    System.out.println("}");
  }

  public void printTreeDepthFirst(String prefix) {
    System.out.print("; " + prefix + " [" + this.arriving + "," + this.terminating + "]");
    for (Map.Entry<Integer, ProbabilisticTrieNode> succ : this.successor.entrySet()) {
      succ.getValue().printTreeDepthFirst(prefix + succ.getKey());
    }
  }
}
