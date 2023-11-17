package io.github.dumijdev.tests;

import io.github.dumijdev.filestream.model.ReaderFileStream;
import io.github.dumijdev.filestream.model.ReaderFileStreamImpl;
import io.github.dumijdev.filestream.model.WriterFileStream;
import io.github.dumijdev.filestream.model.WriterFileStreamImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

public class WriterFileStreamTests {
  private WriterFileStream<Object> writerFileStream;
  private ReaderFileStream<Object> readerFileStream;
  private File fileOut;
  private File fileIn;

  @SneakyThrows
  @BeforeEach
  void loadFile() {
    fileOut = Files.createTempFile("test_out", ".txt").toFile();
    fileIn = new File(Objects.requireNonNull(ReaderFileStreamTests.class.getResource("/test.txt")).toURI());
    writerFileStream = new WriterFileStreamImpl();
    readerFileStream = new ReaderFileStreamImpl();
  }

  @SneakyThrows
  @Test
  void shouldWriteFile() {
    var stream = readerFileStream.read(fileIn);
    writerFileStream.write(stream, fileOut);

    stream.begin();

    Assertions.assertTrue(fileOut.exists());
  }

  @SneakyThrows
  @Test
  void shouldWriteFileWithTransform() {
    var stream = readerFileStream.read(fileIn);
    writerFileStream.write(stream, fileOut, a -> a.toString().toUpperCase());

    stream.begin();

    Assertions.assertTrue(fileOut.exists());
  }

}