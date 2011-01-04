#!/bin/bash


getHEADER() {
cat <<EOF
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.5.xsd">
EOF

}

getFOOTER() {
cat <<EOF
</beans>
EOF
}

 getBeans() {
cat <<EOF
  <bean id="$greek.uniform" parent="barParent">
    <property name="value" value="\${uniform}"/>
  </bean>
  
  <bean id="$greek.victor" parent="barParent">
    <property name="value" value="\${victor}"/>
  </bean>
  
  <bean id="$greek.whiskey" parent="barParent">
    <property name="value" value="\${whiskey}"/>
  </bean>
  
  <bean id="$greek.xray" parent="barParent">
    <property name="value" value="\${xray}"/>
  </bean>
  
  <bean id="$greek.yankee" parent="barParent">
    <property name="value" value="\${yankee}"/>
  </bean>
  
  <bean id="$greek.zulu" parent="barParent">
    <property name="value" value="\${zulu}"/>
  </bean>

EOF


}

 OUTDIR=${OUTDIR:-src/test/resources/etc}

 export LETTERS="omega psi upsilon tau sigma"
 for greek in $LETTERS;
 do
     if [ -d ${OUTDIR} ] ;then
	 OUTFILE=${OUTDIR}/${greek}.xml
	 getHEADER >$OUTFILE
	 getBeans >>$OUTFILE
	 getFOOTER  >>$OUTFILE
     else
	 echo "$OUTDIR does not exist"
     fi
 done

