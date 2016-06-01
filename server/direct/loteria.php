/* arquivo temporário. refatorar para ter uma url para cada modalidade */
<!DOCTYPE html>
<html>
<head>
<style>
body{
	font-family: "Verdana";
	font-size:11px;
}

table, th, td {
    border: 1px solid black;
}
td {
	padding: 3px;
  	text-align: center;
  	vertical-align: middle;
}
td.numero {
    width:30px;
    align:center;
}
.concurso { background:#9bcdff; color:#000080; font-weight:bold; }
</style>
</head>
<body>

<?php

/********************** MEGA SENA *****************************/

$codMega = $_REQUEST['mega']; //1820
$codLotoFacil = $_REQUEST['facil']; //1368

$urlFacil = 'http://www1.caixa.gov.br/loterias/loterias/lotofacil/lotofacil_pesquisa_new.asp?submeteu=sim&opcao=concurso&txtConcurso='.$codLotoFacil;
$urlMega = 'http://www1.caixa.gov.br/loterias/loterias/megasena/megasena_pesquisa_new.asp?submeteu=sim&opcao=concurso&txtConcurso='.$codMega;

$separador = '|:-|-:|';

function getPage($url) {
	$useragent = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36';
	$timeout= 120;
	$dir            = dirname(__FILE__);
	$cookie_file    = $dir . '/cookies/' . md5($_SERVER['REMOTE_ADDR']) . '.txt';
	
	$ch = curl_init($url);
	curl_setopt($ch, CURLOPT_FAILONERROR, true);
	curl_setopt($ch, CURLOPT_HEADER, 0);
	curl_setopt($ch, CURLOPT_COOKIEFILE, $cookie_file);
	curl_setopt($ch, CURLOPT_COOKIEJAR, $cookie_file);
	curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true );
	curl_setopt($ch, CURLOPT_ENCODING, "" );
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt($ch, CURLOPT_AUTOREFERER, true );
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, $timeout );
	curl_setopt($ch, CURLOPT_TIMEOUT, $timeout );
	curl_setopt($ch, CURLOPT_MAXREDIRS, 10 );
	curl_setopt($ch, CURLOPT_USERAGENT, $useragent);
	curl_setopt($ch, CURLOPT_REFERER, 'http://www.google.com/');
	$content = curl_exec($ch);
	
	if(curl_errno($ch)) {
	    echo 'error:' . curl_error($ch);
	} else {
	    return $content;        
	}
    curl_close($ch);

}

$conteudo_pagina = getPage($urlMega);

$temp 		= explode('|', $conteudo_pagina);
$concurso	= $temp[0];
$valor		= $temp[1];
$dadosResultados	= $temp[2];

$dadosResultados = str_replace('</li>', $separador.'</li>', $dadosResultados);
$dadosResultados = strip_tags($dadosResultados);
$resultados 	 = explode($separador, $dadosResultados);

echo("<h1>MEGASENA</h1>");
echo("<table><tr>");
echo("<td>Nº CONCURSO</td>");
echo("<td colspan=6>RESULTADOS</td>");
echo("</tr>");

echo("<tr><td class='concurso'>".$concurso."</td>");

foreach ($resultados as $n => $v) {
	if ($n<6) echo("<td class='numero'>".(strlen($v)==1?"0":"")."$v</td>");
}

echo("</tr></table>");

/******** LOTO FÁCIL ********/

$conteudo_pagina = getPage($urlFacil);
$temp 		= explode('|', $conteudo_pagina);
$concurso	= $temp[0];
$valor		= $temp[19];

echo("<hr><h1>LOTOFACIL</h1>");
echo("<table><tr>");
echo("<td>Nº CONCURSO</td>");
//echo("<td>|Estimativa de Prêmio</td>");
echo("<td colspan=15>RESULTADOS</td>");
echo("</tr>");
echo("<tr><td class='concurso'>".$concurso."</td>");
$ini = 3;
$maximo = $ini+14;
foreach ($temp as $n => $v) {
	if ($n>=$ini && $n<=$maximo) echo("<td class='numero'>".(strlen($v)==1?"0":"")."$v</td>");
}
echo("</tr></table>");

?>

</body>
</html>
