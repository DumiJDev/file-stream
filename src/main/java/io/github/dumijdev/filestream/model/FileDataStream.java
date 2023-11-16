package io.github.dumijdev.filestream.model;

import io.github.dumijdev.filestream.exception.ExceedTimesException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FileDataStream implements DataStream<String, Object> {
  private final List<Object> elements;
  private final AtomicInteger currentIndex;
  private final AtomicBoolean isDone;

  public FileDataStream() {
    this.elements = Collections.synchronizedList(new LinkedList<>());
    isDone = new AtomicBoolean(false);
    currentIndex = new AtomicInteger(0);
  }

  public void add(Object line) {
    elements.add(line);
  }

  @Override
  public boolean isReady() {
    return !elements.isEmpty();
  }

  public void done() {
    isDone.set(true);
  }

  @Override
  public boolean isDone() {
    return isDone.get();
  }

  @Override
  public Object get(int index) {
        return elements.get(index);
  }

  @Override
  public List<Object> getAll() {
    synchronized (elements) {
      return new ArrayList<>(elements);
    }
  }

  @Override
  public boolean hasNext() {
    int times = 0;
    while (!isDone.get() && currentIndex.get() >= elements.size()) {

      if (times == 10) {
        throw new ExceedTimesException("Wait for any elements and re-trying 10 times");
      }

      try {
        Thread.sleep(100);
        times++;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    return currentIndex.get() < elements.size() || !isDone.get();
  }

  @Override
  public Object next() {
    return elements.get(currentIndex.getAndIncrement());
  }
}
