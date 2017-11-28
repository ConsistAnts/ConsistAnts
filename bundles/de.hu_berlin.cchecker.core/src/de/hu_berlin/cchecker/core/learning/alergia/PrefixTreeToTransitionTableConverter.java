package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class PrefixTreeToTransitionTableConverter {

  ProbabilisticTrie trie;
  HashMap<Integer, ProbabilisticState> transitionTable;

  public PrefixTreeToTransitionTableConverter(ProbabilisticTrie trie) {
    this.trie = trie;
  }

  public void convert() {
    this.transitionTable = new HashMap<Integer, ProbabilisticState>();
    Stack<ProbabilisticTrieNode> stack = this.putNodesIntoStack();
    ProbabilisticTrieNode node;
    ProbabilisticState state;
    while (!stack.isEmpty()) {
      node = stack.pop();
      state = new ProbabilisticState();
      state.number = node.number;
      state.arriving = node.arriving;
      state.terminating = node.terminating;
      state.successor = new TreeMap<>();
      state.successorFrequencies = new TreeMap<>();
      state.predecessor = new TreeMap<>();
      this.transitionTable.put(state.number, state);
      for (int i = node.successor.size()-1; i >= 0 ; i--) {
        ProbabilisticTrieNode succNode = (ProbabilisticTrieNode) node.successor.values().toArray()[i];
        Integer symbol = (Integer) node.successor.keySet().toArray()[i];
        state.successor.put(symbol, succNode.number);
        state.successorFrequencies.put(symbol, node.successorFrequencies.get(symbol));
        this.transitionTable.get(succNode.number).predecessor.put(symbol, state.number);
      }
    }
  }

  public void convert2() {
    this.transitionTable = new HashMap<Integer, ProbabilisticState>();
    ProbabilisticTrieNode node = this.trie.getRoot();
    ProbabilisticState state = this.convertNodeToState(node);
    state.terminating = node.terminating;
    state.arriving = node.arriving;
    this.transitionTable.put(state.number, state);
  }

  private ProbabilisticState convertNodeToState(ProbabilisticTrieNode node) {
    ProbabilisticState state = new ProbabilisticState();
    state.number = this.transitionTable.size() + 1;
    this.transitionTable.put(state.number, state);
    state.successor = new TreeMap<>();
    state.successorFrequencies = new TreeMap<>();
    state.predecessor = new TreeMap<>();
    ProbabilisticState succState;
    for (int i = node.successor.size()-1; i >= 0 ; i--) {
      ProbabilisticTrieNode succNode = (ProbabilisticTrieNode) node.successor.values().toArray()[i];
      succState = convertNodeToState(succNode);
      succState.terminating = succNode.terminating;
      succState.arriving = succNode.arriving;
      Integer symbol = (Integer) node.successor.keySet().toArray()[i];
      state.successor.put(symbol, succState.number);
      state.successorFrequencies.put(symbol, node.successorFrequencies.get(symbol));
      succState.predecessor.put(symbol, state.number);
    }
    return state;
  }

  private Stack<ProbabilisticTrieNode> putNodesIntoStack() {
    Stack<ProbabilisticTrieNode> stack = new Stack<>();
    Queue<ProbabilisticTrieNode> queue = new LinkedList<>();
    int cnr = 1;
    queue.offer(trie.getRoot());
    stack.push(trie.getRoot());
    trie.getRoot().visited = true;
    trie.getRoot().number = cnr++;
    ProbabilisticTrieNode node;
    while(!queue.isEmpty()) {
      node = queue.poll();
      for (ProbabilisticTrieNode child : node.successor.values()) {
        if (!child.visited) {
          queue.offer(child);
          stack.push(child);
          child.visited = true;
          child.number = cnr++;
        }
      }
    }

    return stack;
  }

  public HashMap<Integer, ProbabilisticState> getResult() {
    if (this.transitionTable == null) {
      this.convert();
    }
    return this.transitionTable;
  }

}
