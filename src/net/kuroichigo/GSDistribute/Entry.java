package net.kuroichigo.GSDistribute;

import net.kuroichigo.GSDistribute.support.GSDistribute;

/**
 * @author mk2
 */
public class Entry {

  public static void main(String[] args) {
    new Entry(args);
  }

  public Entry(String[] args) {
    GSDistribute gsd = new GSDistribute(args);
  }

}
