package org.daboodb.examples.findduplicates;

import org.daboodb.daboo.client.DbClient;
import org.daboodb.daboo.shared.DocumentContents;
import org.daboodb.daboo.shared.StandardDocument;
import org.daboodb.daboo.shared.exceptions.DatastoreException;
import org.daboodb.examples.findduplicates.model.FileInfo;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * This is example application whose purpose is twofold: 1) one to drive daboodb development and
 * 2) provide a useful solution for finding duplicates by data content as well name and other
 * criteria.
 */
public class FindDuplicateFiles {


    /**
     * @param args One argument - the directory to start searching from.
     */
    public static void main(String[] args) {
        final Path path = Paths.get(args[0]);
        DbClient dbClient = DbClient.get();
        try {

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    final FileInfo fileInfo = FileInfo.getInstance(file);

                    StandardDocument standardDocument = new StandardDocument(new DocumentContents() {
                        @Override
                        public byte[] getKey() {
                            byte[] longAsBytes = new byte[8];
                            longToByteArray(fileInfo.getChecksum(), longAsBytes);
                            return longAsBytes;
                        }

                        @Override
                        public String getContentType() {
                            return fileInfo.toString();
                        }
                    });

                    try {
                        dbClient.write(standardDocument);
                    } catch (DatastoreException e) {
                        throw new IOException("Could not presist:" + standardDocument.toString(), e);
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (Exception e) {
            throw new IllegalStateException(e);

        }
    }
    private static void longToByteArray(long l, byte[] b) {
        b[7] = (byte) (l);
        l >>>= 8;
        b[6] = (byte) (l);
        l >>>= 8;
        b[5] = (byte) (l);
        l >>>= 8;
        b[4] = (byte) (l);
        l >>>= 8;
        b[3] = (byte) (l);
        l >>>= 8;
        b[2] = (byte) (l);
        l >>>= 8;
        b[1] = (byte) (l);
        l >>>= 8;
        b[0] = (byte) (l);
    }

    private static long byteArrayToLong(byte[] b) {
        return (((long) b[7]) & 0xFF) +
                ((((long) b[6]) & 0xFF) << 8) +
                ((((long) b[5]) & 0xFF) << 16) +
                ((((long) b[4]) & 0xFF) << 24) +
                ((((long) b[3]) & 0xFF) << 32) +
                ((((long) b[2]) & 0xFF) << 40) +
                ((((long) b[1]) & 0xFF) << 48) +
                ((((long) b[0]) & 0xFF) << 56);
    }


}
