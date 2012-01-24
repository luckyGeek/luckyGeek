#!/bin/bash
if [ $1 == "-h" -o $1 == "--help" ]
then
	echo "Usage: Just execute ./run.sh witout parameters. Configuration is done by .properties files in conf."
else
	java -Djava.util.logging.config.file=logging.properties -jar luckyGeek.jar    
fi

