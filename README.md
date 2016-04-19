dabuDB
======

DabuDB is a pure Java, key-value store, designed for very high performance on small to moderate-sized databases. 

DabuDB is named after Larry's daughter, who thought her name was "Dabu" when she was little - thanks to her big brother. 

## The API
The database provides standard key-value operations:

* Write(), Get(), and Delete(); for both single documents and batches
* GetRange(); for range queries

## The Features
What makes dabuDB different from, say, a standard Java TreeMap?

* Data is persistent and durable in the event of a shutdown or crash
* Both in-process (embedded) and client-server modes
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

    https://github.com/lwhite1/dabudb/releases

Build and install: 

    mvn clean install
    
    
Now you can use dabuDB in your own projects.
    


## A (rough) roadmap:

### 1.1

* Pluggable comparators 
* Optimistic locking
* Transaction support 

### 1.2

* Performance testing framework
* Performance logging

### 2.0

* Client-Server Mode with pluggable communication protocols

### 2.5

* Document schema version management

We should now have a good working system, with excellent performance at reasonable scale (say, less than 10 micros per read, up to 100 million records).

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
