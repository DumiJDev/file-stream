package io.github.dumijdev.tests;

import io.github.dumijdev.filestream.model.ReaderFileStream;
import io.github.dumijdev.filestream.model.ReaderFileStreamImpl;
import io.github.dumijdev.filestream.model.WriterFileStream;
import io.github.dumijdev.filestream.model.WriterFileStreamImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;
import java.util.stream.Collectors;

public class WriterFileStreamTests {
  private WriterFileStream<Object> writerFileStream;
  private ReaderFileStream<Object> readerFileStream;
  private File fileOut;
  private File fileIn;

  @BeforeEach
  void setup() {
    writerFileStream = new WriterFileStreamImpl();
    readerFileStream = new ReaderFileStreamImpl();
  }

  @AfterEach
  void cleanup() {
    if (fileOut != null && fileOut.exists()) {
      fileOut.delete();
    }
  }

  @SneakyThrows
  @Test
  void shouldWriteFile() {
    // Arrange
    fileIn = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    fileOut = new File("test_out4.txt");

    // Act
    var writeStream = readerFileStream.read(fileIn);

    writerFileStream.write(writeStream, fileOut);

    while (!writerFileStream.isDone()) {
      System.out.println("Waiting...");
    }

    // Assert
    Assertions.assertTrue(fileOut.exists(), "The output file should exist");
    Assertions.assertTrue(Files.readAllBytes(fileOut.toPath()).length > 0, "Content should match");
  }

  @SneakyThrows
  @Test
  void shouldWriteFileWithTransform() {
    // Arrange
    fileIn = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    fileOut = new File("upper_case.txt");

    // Act
    var writeStream = readerFileStream.read(fileIn);

    writerFileStream.write(writeStream, fileOut, o -> o.toString().toUpperCase());

    while (!writerFileStream.isDone()) {
      System.out.println("Waiting...");
    }

    var readStream = readerFileStream.read(fileOut);

    // Assert
    Assertions.assertTrue(fileOut.exists(), "The output file should exist");
    Assertions.assertEquals(writeStream.getAll().stream().map(o -> o.toString().toUpperCase()).collect(Collectors.toList()),
        readStream.getAll(),
        "Content should match");
  }

  @SneakyThrows
  @Test
  void shouldWriteAndReadFile() {
    // Arrange
    fileIn = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    fileOut = new File("test_out2.txt");

    // Act
    var writeStream = readerFileStream.read(fileIn);

    writerFileStream.writeAndWait(writeStream, fileOut);

    var readStream = readerFileStream.read(fileOut);

    // Assert
    Assertions.assertTrue(fileOut.exists(), "The output file should exist");
    Assertions.assertEquals(writeStream.getAll().size(), readStream.getAll().size(), "Content should match");
  }

  @SneakyThrows
  @Test
  void shouldWriteAsyncAndReadFile() {
    // Arrange
    fileIn = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    fileOut = new File("test_out1.txt");

    // Act
    var writeStream = readerFileStream.read(fileIn);

    writerFileStream.write(writeStream, fileOut);

    while (!writerFileStream.isDone()) {
      System.out.println("Waiting...");
    }

    var readStream = readerFileStream.read(fileOut);

    // Assert
    Assertions.assertTrue(fileOut.exists(), "The output file should exist");
    Assertions.assertEquals(writeStream.getAll().size(), readStream.getAll().size(), "Content should match");
  }

}
