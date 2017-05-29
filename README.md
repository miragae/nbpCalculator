# NBP Calculator
Simple command line application calculating mean of ask values and standard deviation of bid values of given currency in given range using data from NBP server (National Bank of Poland).

## Running the project
1. Download "build" folder
2. Start command line inside "build" folder
3. Start program using syntax
```
java pl.parser.nbp.MainClass <CURRENCY CODE> <START DATE> <END DATE>
```
e.g.
```
java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31
```
4. Received output:
```
4,1505 - mean of ask values
0,0125 - standard deviation of bid values
```
