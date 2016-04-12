# dabuDB

DabuDB is a simple, pure Java document store.

It's in development. You shouldn't use it for production until the 1.0 release is final.

DabuDB is named after my daughter, who thought her name was "Dabu" when she was very young - thanks to her big brother. Love you both. 

Here's a rough roadmap:

## 1.0

* Basic key value functionality: write, get, delete
* Embedded mode only
* Excellent performance for small amounts of data
* Pluggable storage engine
* Pluggable support for encryption and compression of data and messages

At this point, 1.0 is mostly done. More work is needed on tests, documentation, and code cleanup. 
Performance with small databases (my laptop):

* 1 million random inserts: 8.8 seconds (8.8 micros per insert)
* 1 million random reads: 3.279 seconds (3.3 micros per insert)

1.0 should not be used for critical data as transactions and recovery are not yet implemented.

## 1.5

* Optimistic locking
* Simple transactions
* Proper performance testing framework

## 2.0

* Client-Server or embedded modes
* Good performance with reasonable scalability


## 2.5

* Document schema version management
* Integrated data validation before writes

## 3.0

* Secondary indexes
* Full-text-search support
* A sql-inspired query language
* Reasonable performance on new query functionality with moderate scale

## 4.0

* Catalog
* Backup and restore


More info on APIs, architecture, performance, etc. will follow.
