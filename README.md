dabooDb
=======

DabooDb is a pure Java, key-value store, designed for high performance on small to moderate-sized databases. It's 
named after my daughter, who thought her name was "Daboo" when she was little - thanks to her big brother. 

This software was originally written for use in a course on Java performance engineering, but the course never materialized and so the slower "starter" version was jettisoned and this higher-performance version is all that remains. It does work, however ;). 

## The API
The database provides standard key-value operations:

* Write(), Get(), and Delete(); for both single documents and batches
* GetRange(); for range queries

## The Features
What makes dabooDb different?

* It's persistent; Durable in the event of a shutdown or crash
* Supports in-process (embedded) and client-server modes
* Transactions on batch operations
* Optimistic Locking, so it can be safely used by more than one client
* Integrated document compression and encryption
* Integrated document validation using JSR 349 annotations before writes
* Built-in backup and restore
* Extremely flexible: Select or create plugins for:
    * Storage technology and in-memory data structures
    * Communications
    * Compression
    * Encryption
* These options can be combined to create a custom storage solution for your application
* It is quite fast for both reads and writes:
    * 1 million random inserts: 8.8 seconds (113,636 OPS or 8.8 micros per insert)
    * 1 million random reads: 3.279 seconds (304,971 OPS or 3.3 micros per read)    
    
## Getting Started
        
Download the latest release from:

    https://github.com/lwhite1/daboodb/releases

Build and install: 

    mvn clean install
    
Then add a dependency to your pom file:
    
    <dependency>
        <groupId>org.daboodb</groupId>
        <artifactId>daboo</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
## A (rough) roadmap:

### 1.1

* Pluggable comparators 
* Optimistic locking
* Transaction support 

### 1.2

* Performance testing framework
* Performance logging

### 2.0

* High-Performance client-Server mode with new communication plugin

### 2.5

* Document schema version management

## Beyond that?
Here are some further extensions to make it a full database (if that would be fun):

### 3.0

* Secondary indexes
* A sql-inspired query language
* Reasonable performance on new query functionality with moderate scale

### 4.0

* Catalog

### 5.0
* Full-text-search support
