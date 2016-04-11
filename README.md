# dabuDB

DabuDB is a simple, pure Java document store.

It's in development. You probably shouldn't use it for production until the 1.0 release is final.

DabuDB is named after my daughter, who thought her name was "Dabu" when she was very young - thanks to her big brother. Love you both. 

Here's a rough roadmap:

## 1.0

* Embedded mode only
* Key value functionality
* Good performance for modest amounts of data
* Pluggable storage engine
* Pluggable support for encryption and compression of data and messages

At this point, I think 1.0 is mostly done. There's more work needed on tests, documentation, and code cleanup.

## 1.5

* Optimistic locking
* Simple transactions

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

* Backup and restore


More info on APIs, performance, etc. will follow.
