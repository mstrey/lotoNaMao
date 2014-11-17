#!/bin/bash

# define a sort of loteries
# declare -a loterias="megase lotfac quina lotoma dplsen timema"
declare -a loterias="megase quina"

#go to directory
cd ~/www/loto/cef

# download and unzip results
for lot in ${loterias[@]}
do
	wget -N http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_$lot.zip
	unzip -o D_$lot.zip
done

#clean
rm *.GIF

# find usage is locked on KingHost
# find . -name "*.htm" | xargs sed -i s/nbsp</nbsp;</g
