# Motif
## Created By: James Prillaman

### Description:

Java 1.7
Hadoop 2.6.0


Motif uses Map Reduce to find the best median string representation for an entire dna sequence input.
Hadoop is used to optimize the solution so as that it can be run in a minimal amount of time given the large
problem input set.

### Usage

```
hadoop -jar Motif.jar <DNA_Input> <OutPut>

```

####Components

The Application jar: Motif/Motif.jar
Test Data: Mofif/testData.txt
Small input test data: Motif/AltTestData.txt
Output from my test: output.txt

#### Example

```
hadoop jar ~/Motif/Motif.jar ~/Motif/testData.txt ~/Motif/Output

```
