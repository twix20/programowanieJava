#!/bin/bash

rm *.so
rm *.class
rm *.h
rm *.log

export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk

javac -h . *.java

g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o DotProduct.so DotProduct.cpp

java -Djava.library.path=. Program