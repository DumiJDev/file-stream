package io.github.dumijdev.filestream.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

public final class ReaderFileStreamImpl implements ReaderFileStream<Object> {

  private Thread executor;

  @Override
  public DataStream<Object> read(File file, Function<String, Object> transformer) throws IOException {
    var fileDataStream = new FileDataStream<Object>();

    Runnable task = () -> {
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (transformer != null) {
            fileDataStream.add(transformer.apply(line).toString());
          } else {
            fileDataStream.add(line);
          }
        }
        fileDataStream.done();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };

    executor = new Thread(task);

    executor.start();

    return fileDataStream;
  }

  @Override
  public boolean stop() {
    
    executor.interrupt();
    
    return true;
  }
}
