# dabuDB

DabuDB is a simple, pure Java document store. It is designed as a vehicle for teaching how to write High-Performance/Low-Latency Java.

DabuDB is named after my daughter, who thought her name was "Dabu" when she was little - thanks to her big brother. Love you both. 

## (rough) roadmap:

### 1.0

* Basic key value functionality: write, get, delete
* Embedded mode only
* Excellent performance for small amounts of data
* Pluggable storage engine
* Pluggable serializers for data and messages
* Pluggable write-ahead logging

At this point, 1.0 is mostly done. More work is needed on tests, documentation, and code cleanup. 
Performance with small databases is encouraging: (my laptop):

* 1 million random inserts: 8.8 seconds (113,636 OPS or 8.8 micros per insert)
* 1 million random reads: 3.279 seconds (304,971 OPS or 3.3 micros per read)

### 1.5

* Optimistic locking
* Simple transactions
* Proper performance testing framework

### 2.0

* Client-Server or embedded modes
* Excellent performance at scale (say, up to 100 million records)

At this point, we will have a good working system, and proceed with making a Low Performance/High Latency version, 
that can be used as a starting point for students.

## Beyond that?
Here are some further extensions to make it a real db (should we decide that would be fun):

### 2.5

* Document schema version management
* Integrated data validation before writes

### 3.0

* Secondary indexes
* A sql-inspired query language
* Reasonable performance on new query functionality with moderate scale

### 4.0

* Catalog
* Backup and restore

### 5.0
* Full-text-search support
