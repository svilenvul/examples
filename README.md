## About this repository

This repository contains simple Java example projects.

### Assignment sample project

This project is a sample Java project, that solves the following task:

- Find which pair of employees has worked for the the most time together.

Input data is text file with the following format:

`EmpID, ProjectID, DateFrom, DateTo`

Example data :

```
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
...

where NULL is equivalent to today
```

### Running the projects

The project requires Java 8 JDK to be installed on the host machine.

A simple Java FX UI is included. You can drag and drop a text file into the upper box and the result will be displayed below.

#### Eclipse

You can run the `AssignmentsTask.launch` run configuration.

#### Maven

Execute `mvn clean install` from the project folder `assignments`
