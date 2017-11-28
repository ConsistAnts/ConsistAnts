# ConsistAnts Command Line Interface

This bundles contains the Command Line Interface of ConsistAnts.

The main class is src/de/hu\_berlin/cchecker/headless/ConsistAntsCommandLineInterface.java.

## Exporting a Runnable JAR for the use on the command line

To export the CLI as a runnable JAR archive the following steps have to be followed.


1. Select src/de/hu\_berlin/cchecker/headless/ConsistAntsCommandLineInterface.java in the Eclipse Project Explorer
2. Right-click and select `Export...`
3. Expand `Java` and select `Runnable JAR file`
4. Select the `ConsistAntsCommandLineInterface` launch configuration at the root of this bundle
5. Enter an export destination and name for the JAR file
6. Press `Finish`

> For *Library Handling* the option *Copy required libraries into a sub-folder next to the generated JAR* has proven to yield the best performance since no on-the-fly unpacking has to be done to access the packaged dependencies


