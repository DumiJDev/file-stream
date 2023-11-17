package io.github.dumijdev.filestream.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public final class WriterFileStreamImpl implements WriterFileStream<Object> {
  private Thread executor;
  private boolean isDone = false;

  @Override
  public void write(DataStream<Object> dataStream, File file, Function<Object, String> transformer) throws IOException {
    dataStream.begin();

    if (!file.exists()) {
      file.createNewFile();
    }

    Runnable task = () -> {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        while (dataStream.hasNext()) {
          var next = dataStream.next();
          if (transformer != null) {
            bw.append(transformer.apply(next));
          } else {
            bw.append(next.toString());
          }
          bw.newLine();
        }
        bw.flush();
      } catch (InterruptedException | IOException e) {
        throw new RuntimeException(e);
      } finally {
        isDone = true;
      }
    };

    executor = new Thread(task);

    executor.start();
  }

  @Override
  public void writeAndWait(DataStream<Object> dataStream, File file, Function<Object, String> transformer) throws IOException {
    dataStream.begin();

    if (!file.exists()) {
      file.createNewFile();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      while (dataStream.hasNext()) {
        var next = dataStream.next();
        if (transformer != null) {
          bw.append(transformer.apply(next));
        } else {
          bw.append(next.toString());
        }
        bw.newLine();
      }
      bw.flush();
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public void stop() {
    executor.interrupt();
  }
}
