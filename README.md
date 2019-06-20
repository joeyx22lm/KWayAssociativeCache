## K-Way Set Associative Cache in Java       [![Build Status](https://travis-ci.com/joeyx22lm/KWayAssociativeCache.svg?token=XFzRrEHp76sxknttwnK8&branch=master)](https://travis-ci.com/joeyx22lm/KWayAssociativeCache)
	Author: Joseph Orlando
	Written for EEL4768.
	Characterizes LRU and FIFO caching techniques for a K-Way Set Associative cache.

### Prerequisites

	Java 8 / JRE1.8 is required in order to build and run this source.

### Definitions

	Clean task will wipe any existing builds.
	Check task will run a checkstyle upon the source code.
	Test task will run all unit tests, including the following complete sample tests:
		1. 32KB 8-way associative LRU - MiniFE
		2. 32KB 8-way associative LRU - XSBench
		3. 32KB 8-way associative FIFO - MiniFE
		4. 32KB 8-way associative FIFO - XSBench
		5. 32KB 2-way associative LRU - MiniFE
		6. 32KB 2-way associative LRU - XSBench

### How to build

	Gradle will automagically clean, check, test and build the source for you.

	Run command: "gradle clean check test build"

	Example:
	```
	.$:  gradle clean check test
	```

	Depending on your OS, you may need to run "gradlew.bat clean check test build".
	Depending on your OS, you may need to run "./gradlew clean check test build"

	You should see BUILD SUCCESSFUL ->

	You can view the test results in "build/test-results/test"
	You can find the executable in "build/libs"

### How to run

	Run command: "java -jar build/libs/SIM.jar <CACHE_SIZE> <ASSOC> <REPLACEMENT> <TRACE_FILE>"

	Example:

		```
		java -jar build/libs/SIM.jar 32768 2 0 ~/Downloads/MINIFE.t
		```

	Expected Output:
		[TIMESTAMP] INFO: Cache Size: 32768B
		[TIMESTAMP] INFO: Associativity: 2-Way
		[TIMESTAMP] INFO: Replacement Policy: LRU
		[TIMESTAMP] INFO: Trace File: MINIFE.t
		[TIMESTAMP] INFO: Write Misses: 0.03728772017477289
		[TIMESTAMP] INFO: Read Misses: 0.07057086962575254
		[TIMESTAMP] INFO: Total Miss Ratio: 0.06622455685237727

### Example

	```
	.$:	  gradle clean check test build
	.$:	  java -jar build/libs/SIM.jar 32768 2 0 ~/Downloads/MINIFE.t
	```
