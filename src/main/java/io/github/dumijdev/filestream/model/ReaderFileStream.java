package io.github.dumijdev.filestream.model;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public interface ReaderFileStream<R> {
  default DataStream<R> read(File file) throws IOException {
    return read(file, null);
  }

  DataStream<R> read(File file, Function<String, R> transformer) throws IOException;
  
  boolean stop();
}
