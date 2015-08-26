# Data sampler

Generate random representative sample with given length from stream.

Algorithm, used for sampling implementation - https://en.wikipedia.org/wiki/Reservoir_sampling

## Brief application description
To produce data sample we need RNG. Three different options supported:
 * Standard Java Random class (worst choice)
 * Improved generator - http://www.javamex.com/tutorials/random_numbers/numerical_recipes.shtml
 * Standard Java SecureRandom class - very good quality, but very slow

Stream to be consumed:
 * stdin
 * internal emulator (produces given number of bytes). emulator uses the same RNG, as for the algorithm.
 
Whole program left thread-unsafe for simplicity.
Another option was to implement with PipeInput(Output)Stream and several threads.
This way was intentionally discarded, since the whole app would be more error-prone and complicated.

## Build
Thanks to Gradle wrapper, there is nothing really difficult here.
Make sure you have JAVA_HOME env variable set to JDK 1.7/1.8
Run `./gradlew check` to build and run tests
Run `./gradlew installApp` to build and produce directory with ready-to-run application.
`./build/install/sampler/bin/sampler` is the target script.

## Usage
CLI help:
```
sampler [options...]
 --byte                                 : Treat stream as bytes (text by
                                          default) (default: false)
 --emulate                              : Set flag to use internal random
                                          stream (default: false)
 --emulate-length N                     : Bytes to generate, defaults to 2048
                                          (default: 2048)
 --generator [STANDARD | FAST | SECURE] : Random generator (defaults to classic
                                          Random) (default: FAST)
 --length N                             : Specify sample length

  Example: sampler --byte --emulate --emulate-length N --generator [STANDARD | FAST | SECURE] --length N
```