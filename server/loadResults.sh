#!/bin/bash

# define a sort of loteries
declare -a loterias="megase lotfac quina lotoma dplsen timema"

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
