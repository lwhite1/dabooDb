# dabuDB

DabuDB is a simple key-value document store. It is designed as a vehicle for teaching how to write High-Performance/Low-Latency Java.

DabuDB is named after Larry's daughter, who thought her name was "Dabu" when she was little - thanks to her big brother. 

## Features/Requirements
The system is a key-value document store with the following capabilities:

#### Documents
* are arbitrary POJOs implementing a simple interface.
* must provide a byte array key that is unique within the database
* must serialize to a byte array, so they can be stored as a value in the database
* are ordered by a Comparator, and can be range-searched in that order


#### The database:
* supports Write, Delete, Get, and GetRange(from, to) operations
* supports Client-Server and Embedded (client and server in a single JVM) modes
* is durable (application can be closed and re-opened without losing data)
* is transactional: batches written in a single call are processed atomically (committed or rolled-back together)
* uses Optimistic Locking to allow multiple writers
* supports idempotent Writes to allow clients to safely retry updates

## A (rough) roadmap:

### 1.0

* Basic key value functionality: write, get, delete (single and batch)
* Embedded mode only
* Excellent performance for small amounts of data
* Pluggable storage engine and write-ahead logging
* Pluggable serializers for data and messages

At this point, 1.0 is mostly done. More work is needed on tests, documentation, and code cleanup. 
Performance with small databases is quite good: (my laptop):

* 1 million random inserts: 8.8 seconds (113,636 OPS or 8.8 micros per insert)
* 1 million random reads: 3.279 seconds (304,971 OPS or 3.3 micros per read)

### 1.1

* Pluggable comparator 
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
