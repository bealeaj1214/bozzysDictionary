#!/bin/bash

export LETTERS="alfa bravo charlie delta echo foxtrot gold hotel india juliet kilo lima mike"
for letter in $LETTERS; 
do 
    echo -e "<bean id=\"$letter\" parent=\"fooParent\">\n\t<property name=\"value\" value=\"\${$letter}\"/>\n</bean>\n" 
done

