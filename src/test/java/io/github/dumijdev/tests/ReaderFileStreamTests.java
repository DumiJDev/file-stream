package io.github.dumijdev.tests;

import io.github.dumijdev.filestream.model.DataStream;
import io.github.dumijdev.filestream.model.ReaderFileStream;
import io.github.dumijdev.filestream.model.ReaderFileStreamImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

public class ReaderFileStreamTests {
  private ReaderFileStream<Object> readerFileStream;
  private File file;

  @SneakyThrows
  @BeforeEach
  void loadFile() {
    file = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    readerFileStream = new ReaderFileStreamImpl();
  }

  @SneakyThrows
  @Test
  void shouldReadInBackground() {
    DataStream<Object> dataStream = readerFileStream.read(file);
    Assertions.assertFalse(dataStream.isDone());
    
    while (dataStream.hasNext()) {
      dataStream.next();
    }

    Assertions.assertTrue(dataStream.isDone());
  }

  @SneakyThrows
  @Test
  void shouldReadInBackgroundWithTransform() {
    DataStream<Object> dataStream = readerFileStream.read(file, String::toLowerCase);
    
    Assertions.assertFalse(dataStream.isDone());
    
    while (dataStream.hasNext()) {
      dataStream.next();
    }
    
    Assertions.assertTrue(dataStream.isDone());
  }

  @SneakyThrows
  @Test
  void shouldReadInBackgroundReturnAll() {
    DataStream<Object> dataStream = readerFileStream.read(file);
    Assertions.assertFalse(dataStream.getAll().isEmpty());
  }

  @SneakyThrows
  @Test
  void shouldCheckHasNext() {
    DataStream<Object> dataStream = readerFileStream.read(file);
    Assertions.assertTrue(dataStream.hasNext());
    
    while (dataStream.hasNext()) {
      dataStream.next();
    }
    
    Assertions.assertFalse(dataStream.hasNext());
  }

}
