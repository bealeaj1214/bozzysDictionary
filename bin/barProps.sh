#!/bin/bash

export LETTERS="november oscar papa quebec romeo sierra tango uniform victor whisky xray yankee zulu"
for letter in $LETTERS; 
do 
    echo "<prop key=\"$letter\"> NATO phonetic letter $letter</prop>" 
done

