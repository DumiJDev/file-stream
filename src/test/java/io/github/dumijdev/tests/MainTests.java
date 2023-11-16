package io.github.dumijdev.tests;

import io.github.dumijdev.filestream.model.DataStream;
import io.github.dumijdev.filestream.model.FileStream;
import io.github.dumijdev.filestream.model.FileStreamImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

public class MainTests {
  private static DataStream<String, Object> dataStream;
  private static DataStream<String, Object> dataStreamT;
  private static DataStream<String, Object> dataStreamA;

  @SneakyThrows
  @BeforeAll
  static void loadFile() {
    File file = new File(Objects.requireNonNull(MainTests.class.getResource("/test.txt")).toURI());
    FileStream<Object> fileStream = new FileStreamImpl();
    dataStream = fileStream.read(file);
    dataStreamT = fileStream.read(file, String::toLowerCase);
    dataStreamA = fileStream.read(file);
  }

  @SneakyThrows
  @Test
  void shouldReadInBackground() {
    var time1 = dataStream.isDone();
    while (dataStream.hasNext()) {
      System.out.println(dataStream.next());
    }
    var time2 = dataStream.isDone();

    Assertions.assertEquals(time1, time2);
  }

  @SneakyThrows
  @Test
  void shouldReadInBackgroundWithTransform() {
    while (dataStreamT.hasNext()) {
      System.out.println(dataStreamT.next());
    }
  }

  @SneakyThrows
  @Test
  void shouldReadInBackgroundReturnAll() {
    Assertions.assertFalse(dataStreamA.getAll().isEmpty());
  }
}
