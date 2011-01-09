#!/bin/bash



 getBeans() {
cat <<EOF
  ${phoenician}_uniform.value=\${uniform}
  
  ${phoenician}_victor.value=\${victor}
  
  ${phoenician}_whiskey.value=\${whiskey}
  
  ${phoenician}_xray.value=\${xray}
  
  ${phoenician}_yankee.value=\${yankee}
  
  ${phoenician}_zulu.value=\${zulu}

EOF


}

 OUTDIR=${OUTDIR:-src/test/resources/etc/common}

 export LETTERS="gaml delt"
 for phoenician in $LETTERS;
 do
     if [ -d ${OUTDIR} ] ;then
	 OUTFILE=${OUTDIR}/${phoenician}.properties
	 getBeans >$OUTFILE
     else
	 echo "$OUTDIR does not exist"
     fi
 done

