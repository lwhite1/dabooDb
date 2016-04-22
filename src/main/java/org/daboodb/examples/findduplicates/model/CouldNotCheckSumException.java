package org.daboodb.examples.findduplicates.model;

/**
 *
 */

public class CouldNotCheckSumException extends RuntimeException {

    public CouldNotCheckSumException(String s) {
        super(s);
    }

    public CouldNotCheckSumException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
