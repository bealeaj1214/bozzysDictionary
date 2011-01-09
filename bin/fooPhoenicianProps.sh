#!/bin/bash



 getBeans() {
cat <<EOF
  ${phoenician}_alfa.value=\${alfa}
  
  ${phoenician}_bravo.value=\${bravo}
  
  ${phoenician}_charlie.value=\${charlie}
  
  ${phoenician}_delta.value=\${delta}
  
  ${phoenician}_echo.value=\${echo}
  
  ${phoenician}_foxtrot.value=\${foxtrot}

EOF


}

 OUTDIR=${OUTDIR:-src/test/resources/etc/common}

 export LETTERS="alf bet"
 for phoenician in $LETTERS;
 do
     if [ -d ${OUTDIR} ] ;then
	 OUTFILE=${OUTDIR}/${phoenician}.properties
	 getBeans >$OUTFILE
     else
	 echo "$OUTDIR does not exist"
     fi
 done

