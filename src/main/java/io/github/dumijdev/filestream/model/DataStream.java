package io.github.dumijdev.filestream.model;

import java.util.List;
import java.util.function.Function;

public interface DataStream<T, R> {
  boolean isReady();
  boolean isDone();
  R get(int index) throws InterruptedException;
  List<R> getAll();
  boolean hasNext() throws InterruptedException;
  R next();
}
