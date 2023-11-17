package io.github.dumijdev.filestream.model;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public interface WriterFileStream<R> {
  default void write(DataStream<R> dataStream, File file) throws IOException {
    write(dataStream, file, null);
  }

  void write(DataStream<R> dataStream, File file, Function<R, String> transformer) throws IOException;

  void writeAndWait(DataStream<R> dataStream, File file, Function<R, String> transformer) throws IOException;

  boolean isDone();

  default void writeAndWait(DataStream<R> dataStream, File file) throws IOException {
    writeAndWait(dataStream, file, null);
  }

  void stop();
}
