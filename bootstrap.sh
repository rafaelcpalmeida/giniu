#!/usr/bin/env bash

mkdir -p /giniu-bin

cd /giniu-bin || exit

cp -r /app/src/edu/ .
cp -r /app/lib .
cp -r /app/data .

echo ""
echo "***************************************************"
echo "*  Compiling binaries. This may take some time... *"
echo "***************************************************"
echo ""

JARS="$(find ./* | grep .jar)"

for JAR in $JARS
do
	JAR_TO_COMPILE+="$JAR:"
done

# shellcheck disable=SC2046
javac -cp .:"$JAR_TO_COMPILE":/giniu-bin $(find ./* | grep .java)

echo ""
echo "*********************"
echo "*  Running GINIU... *"
echo "**********************"
echo ""

java -cp .:"$JAR_TO_COMPILE":/giniu-bin edu/ufp/aed2/project/Main