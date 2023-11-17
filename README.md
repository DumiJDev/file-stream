# File-Stream

A java library to read and write text file in streaming

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Examples](#examples)
- [License](#license)

## Installation

To use this library in your Java project, add the following dependency to your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>io.github.dumijdev</groupId>
    <artifactId>file-stream</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

Explain how to incorporate and use the library in a Java project.

```java
// Example code snippet

import io.github.dumijdev.filestream.model.ReaderFileStream;
import io.github.dumijdev.filestream.model.WriterFileStream;
import io.github.dumijdev.filestream.model.WriterFileStreamImpl;

import java.io.File;
import java.nio.file.Files;

public class Example {
  public static void main(String[] args) {
    var file = new File("example.txt");
    var reader = new ReaderFileStreamImpl();
    var dataStream = readerFileStream.read(file);

    while (dataStream.hasNext()) {
      System.out.println(dataStream.next());
    }

    File fileOut = Files.createTempFile("test", ".txt").toFile();
    var writer = new WriterFileStreamImpl();
    var stream = readerFileStream.read(fileIn);
    writerFileStream.write(stream, fileOut);
  }
}
```

## Contributing

We welcome contributions! Follow these steps:

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## Examples

Provide examples of how to use your library. You can base these examples on your tests.

### Example 1: Reading Files

Demonstrate how to use the library to read files.

```java
import io.github.dumijdev.filestream.model.ReaderFileStreamImpl;

import java.io.File;
import java.io.IOException;

public class Example1 {
  public static void main(String[] args) throws InterruptedException, IOException {
    var file = new File("example.txt");
    var reader = new ReaderFileStreamImpl();
    var dataStream = reader.read(file);

    while (dataStream.hasNext()) {
      System.out.println(dataStream.next());
    }
  }
}
```

### Example 2: Writing Files with Transformation

Show how to write files with a custom transformation.

```java
import io.github.dumijdev.filestream.model.FileDataStream;
import io.github.dumijdev.filestream.model.WriterFileStreamImpl;

import java.io.File;
import java.nio.file.Files;

public class Example2 {
  public static void main(String[] args) {
    File fileOut = Files.createTempFile("test", ".txt").toFile();
    var writer = new WriterFileStreamImpl();
    var stream = new FileDataStream<>();
    writer.write(stream, fileOut, item -> item.toString().toUpperCase());
  }
}
```

## License

This library is licensed under the MIT - see the [LICENSE](LICENSE) file for details.