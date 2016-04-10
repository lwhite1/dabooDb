package org.dabudb.dabu.server;

import java.util.Comparator;

/**
 * TODO(lwhite): Generify
 */
public interface DataTree {

  Comparator getComparator();

  void put(Object key, Object value);

  void delete(Object key, Object value);

  Object get(Object key);
}
