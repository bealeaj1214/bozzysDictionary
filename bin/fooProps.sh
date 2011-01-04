#!/bin/bash

export LETTERS="bravo charlie delta echo foxtrot gold hotel india juliet kilo lima mike"
for letter in $LETTERS; 
do 
    echo "<prop key=\"$letter\"> NATO phonetic letter $letter</prop>" 
done

