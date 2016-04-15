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

* The data is persistent and durable in the event of a shutdown or crash
* Both in-process (embedded) and client-server modes
* In client server mode, all communication is handled transparently
* Transactions on all batch operations
* Optimistic Locking, so it can be safely used by more than one client
* Compression and encryption of documents for improved performance and security
* It is extremely flexible: You can select or create plugins for:
    * Storage technology
    * Transaction logging
    * In-memory data structures
    * Communications
    * Compression
    * Encryption
* Options can be combined to create a custom fit for your application
* It is extremely fast for both reads and writes:

    * 1 million random inserts: 8.8 seconds (113,636 OPS or 8.8 micros per insert)
    * 1 million random reads: 3.279 seconds (304,971 OPS or 3.3 micros per read)

## A (rough) roadmap:

### 1.0

* Basic key value functionality: write, get, delete (single and batch)
* Embedded mode only
* Excellent performance for small amounts of data
* Pluggable storage engine and write-ahead logging
* Pluggable serializers for data and messages

At this point, 1.0 is mostly done. More work is needed on tests, documentation, and code cleanup. 

### 1.1

* Pluggable comparators 
* Range search
* Backup and restore


### 1.2
* Optimistic locking
* Transaction support 

### 1.3
* Proper performance testing framework

### 2.0

* Client-Server Mode with pluggable communication protocols

We should now have a good working system, with excellent performance at reasonable scale (say, less than 10 micros per read, up to 100 million records).
We can proceed with making a Low Performance/High Latency version that can be used as a starting point for students.

## Beyond that?
Here are some further extensions to make it a real db (if that would be fun):

### 2.5

* Document schema version management
* Integrated data validation before writes

### 3.0

* Secondary indexes
* A sql-inspired query language
* Reasonable performance on new query functionality with moderate scale

### 4.0

* Catalog

### 5.0
* Full-text-search support
