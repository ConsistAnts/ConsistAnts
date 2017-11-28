package de.hu_berlin.cchecker.core.learning.alergia;

import java.util.SortedMap;

public class ProbabilisticState {

  int number;
  Integer terminating;
  Integer arriving;
  SortedMap<Integer, Integer> successor;
  SortedMap<Integer, Integer> successorFrequencies;
  SortedMap<Integer, Integer> predecessor;

  @Override
  public String toString() {
    return "["+arriving+", "+terminating+"]:{"+successorFrequencies.toString()+"}\n";
  }
}
