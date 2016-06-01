package org.daboodb.examples.findduplicates.model;

/*
 * Copyright (c) 2010.  by Spencer A Marks. All rights reserved.
 */

/**
 * Copyright (c) Spencer A Marks. All rights reserved
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

/**
 * This class is used as a wrapper class for File to
 * be easier compared.
 *
 * @author Spencer A Marks
 */
public class FileInfo implements Comparable {

    private Path file;
    private FileTime lastModified;
    private long size;
    private long checksum;

    public static FileInfo getInstance(Path file) throws IOException {
        FileTime lastModified = Files.getLastModifiedTime(file);
        final long size = Files.size(file);
        final long checksum = getChecksum(file);
        return new FileInfo(file, lastModified,size,checksum);
    }

    private FileInfo(Path file, FileTime lastModified, long size, long checksum) {
        this.file = file;
        this.lastModified = lastModified;
        this.size = size;
        this.checksum = checksum;
    }

    public Path getFile() {
        return file;
    }

    public FileTime getLastModified() {
        return lastModified;
    }

    public long getSize() {
        return size;
    }

    public long getChecksum() {
        return checksum;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "file=" + file +
                ", lastModified=" + lastModified +
                ", size=" + size +
                ", checksum=" + checksum +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof FileInfo)) {
            throw new IllegalStateException("Object to compare must be a FileInfo note a " + o.getClass());

        }
        FileInfo fileInfoToCompare = (FileInfo) o;

        // first see file objects match
        int compareValue = this.file.compareTo(fileInfoToCompare.getFile());

        // if they do match, then compare their checksum values for a match
        if (compareValue == 0) {
            long thisChecksumValue = getChecksum();
            long fileInfoToCompareValue = fileInfoToCompare.getChecksum();
            compareValue = new Long(thisChecksumValue).compareTo(fileInfoToCompareValue);
        }
        return compareValue;
    }

    private static long getChecksum(Path file) throws CouldNotCheckSumException {
        // Compute Adler-32 checksum
        long checksum = 0;
        CheckedInputStream cis = null;
        try {
            cis = new CheckedInputStream(new BufferedInputStream(new FileInputStream(file.toFile())), new Adler32());
            byte[] tempBuf = new byte[256];
            while (cis.read(tempBuf) >= 0) {
                checksum += cis.getChecksum().getValue();
            }
            cis.close();
            // todo consider finally block - but if so  how to handle?
        } catch (IOException e) {
            try {
                cis.close();
            } catch (Throwable t) {
                throw new IllegalStateException("Could not close file in catch block: " + t.getMessage(), t);
            }

            throw new CouldNotCheckSumException(file.toString(), e);
        }
        return checksum;
    }
}
