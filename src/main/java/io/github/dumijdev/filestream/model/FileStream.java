package io.github.dumijdev.filestream.model;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public interface FileStream<R> {
  default DataStream<String, R> read(File file) throws IOException {
    return read(file, null);
  }

  DataStream<String, R> read(File file, Function<String, R> transformer) throws IOException;
}
