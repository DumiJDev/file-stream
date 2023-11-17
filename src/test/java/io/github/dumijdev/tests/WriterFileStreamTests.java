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

    Assertions.assertTrue(fileOut.exists());

    var contentIn = Files.readString(fileIn.toPath());
    var contentOut = Files.readString(fileOut.toPath());
    Assertions.assertEquals(contentIn, contentOut);
  }

  @SneakyThrows
  @Test
  void shouldWriteFileWithTransform() {
    var stream = readerFileStream.read(fileIn);
    writerFileStream.write(stream, fileOut, a -> a.toString().toUpperCase());

    Assertions.assertTrue(fileOut.exists());

    var contentIn = Files.readString(fileIn.toPath());
    var contentOut = Files.readString(fileOut.toPath());
    Assertions.assertEquals(contentIn.toUpperCase(), contentOut);
  }



}
