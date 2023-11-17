package io.github.dumijdev.filestream.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public final class WriterFileStreamImpl implements WriterFileStream<Object> {
  private Thread executor;

  @Override
  public void write(DataStream<Object> dataStream, File file, Function<Object, String> transformer) {

    Runnable task = () -> {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        dataStream.begin();
        while (dataStream.hasNext()) {
          if (transformer != null) {
            bw.append(transformer.apply(dataStream.next()));
          } else {
            bw.append(dataStream.next().toString());
          }
          bw.newLine();
        }
      } catch (InterruptedException | IOException e) {
        throw new RuntimeException(e);
      }
    };

    executor = new Thread(task);

    executor.start();
  }

  @Override
  public void stop() {
    executor.interrupt();
  }
}
