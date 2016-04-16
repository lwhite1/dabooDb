package org.dabudb.dabu.testutil;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 */
public class BasicTest {

  private String testDataFolder = "/tmp/dabudb/testdata";

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {
    removeRecursive(Paths.get(testDataFolder));
  }


  /**
   * Recursively deletes the given path, if it exists
   */
  private static void removeRecursive(Path path) throws IOException {

    if (! path.toFile().exists()) {
      return;
    }

    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc == null) {
          Files.delete(dir);
          return FileVisitResult.CONTINUE;
        } else {
          // directory iteration failed; propagate exception
          throw exc;
        }
      }
    });
  }
}
