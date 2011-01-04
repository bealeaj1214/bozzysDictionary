#!/bin/bash

export LETTERS="november oscar papa quebec romeo sierra tango uniform victor whisky xray yankee zulu"
for letter in $LETTERS; 
do 
    echo -e "<bean id=\"$letter\" parent=\"booParent\">\n\t<property name=\"value\" value=\"\${$letter}\"/>\n</bean>\n" 
done

