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
  <bean id="$greek.alfa" parent="fooParent">
    <property name="value" value="\${alfa}"/>
  </bean>
  
  <bean id="$greek.bravo" parent="fooParent">
    <property name="value" value="\${bravo}"/>
  </bean>
  
  <bean id="$greek.charlie" parent="fooParent">
    <property name="value" value="\${charlie}"/>
  </bean>
  
  <bean id="$greek.delta" parent="fooParent">
    <property name="value" value="\${delta}"/>
  </bean>
  
  <bean id="$greek.echo" parent="fooParent">
    <property name="value" value="\${echo}"/>
  </bean>
  
  <bean id="$greek.foxtrot" parent="fooParent">
    <property name="value" value="\${foxtrot}"/>
  </bean>

EOF


}

 OUTDIR=${OUTDIR:-src/test/resources/etc}

 export LETTERS="alpha beta gamma delta epsilon"
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

