package io.github.dumijdev.filestream.model;

import java.util.List;

public interface DataStream<R> {
  boolean isReady();
  boolean isDone();
  R get(int index) throws InterruptedException;
  List<R> getAll();
  boolean hasNext() throws InterruptedException;
  R next();

  int size();
  void begin();
}
