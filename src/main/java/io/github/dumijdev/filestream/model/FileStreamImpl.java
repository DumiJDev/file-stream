package io.github.dumijdev.filestream.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

public class FileStreamImpl implements FileStream<Object> {

  @Override
  public DataStream<String, Object> read(File file, Function<String, Object> transformer) {
    var fileDataStream = new FileDataStream();

    new Thread(() -> {
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (transformer != null) {
            fileDataStream.add(transformer.apply(line));
          } else {
            fileDataStream.add(line);
          }
        }
        fileDataStream.done();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).start();

    return fileDataStream;
  }
}
