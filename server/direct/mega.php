<?php
include_once '../connection.php';
include_once '../getMax.php';
include_once '../error.php';

$table = megasena;
$resultado = array();

function parseXml($content, $node){
	/*** a new dom object ***/ 
	$dom = new domDocument; 

	/*** load the html into the object ***/
	$dom->loadHTML($content); 

//	echo $dom->saveHTML();
//	die();

	/*** discard white space ***/ 
	$dom->preserveWhiteSpace = false; 

	/*** the table by its tag name ***/ 
	$nodes = $dom->getElementsByTagName($node); 
	
	return $nodes;

}

function parseResult($site){
	$sorteio = split("| ",stream_get_contents(fopen($site, "r")));
	$resultado["concurso"] = $sorteio[0];
	$resultado["acumulado_5"] = $sorteio[1];
	
	$numeros = parseXml($sorteio[2], 'li');
	
	$resultado["bola_1"] = $numeros->item(0)->nodeValue;
	$resultado["bola_2"] = $numeros->item(1)->nodeValue;
	$resultado["bola_3"] = $numeros->item(2)->nodeValue;
	$resultado["bola_4"] = $numeros->item(3)->nodeValue;
	$resultado["bola_5"] = $numeros->item(4)->nodeValue;
	$resultado["bola_6"] = $numeros->item(5)->nodeValue;
	
	
	$resultado["ganhadores_6"] = $sorteio[3];
	$resultado["rateio_6"] = $sorteio[4];
	$resultado["ganhadores_5"] = $sorteio[5];
	$resultado["rateio_5"] = $sorteio[6];
	$resultado["ganhadores_4"] = $sorteio[7];
	$resultado["rateio_4"] = $sorteio[8];

	$resultado["data"] = explode("/",$sorteio[11],3);
	
	$resultado["cidade_sorteio"] = $sorteio[12];
	$resultado["uf_sorteio"] = $sorteio[13];
	$resultado["tipo_sorteio"] = $sorteio[14]; // C= Caminhão, A= Estúdio
	$resultado["obs_sorteio"] = $sorteio[15];
	
	$resultado["prox_acumulado_5"] = $sorteio[16];
	$resultado["final_acumulado_5"] = $sorteio[17];
	$resultado["valor_acumulado_5"] = $sorteio[18];

	// verificar como validar a classe de cada tr pra ver se é um estado ou cidade
	$tabela_ganhadores = parseXml($sorteio[19], 'tbody');
	$linhas_ganhadores = parseXml(tabela_ganhadores, 'tr');
	
	$estado = "";
	$pega_estado = true;
	$cidades = array();
	$cont_estado = 0;
	$idx_cidades = 0;
	
	foreach($linhas_ganhadores as $linha){
		$valores = parseXml($linha, 'td');
		if($pega_estado){
			$estado = $valores->item(0)->nodeValue;
			$count_estado = $valores->item(1)->nodeValue;
			$pega_estado = false;
		} else {
			$cidades[$idx_cidades] = array (
				"estado" => $estado ,
				"cidade" => strip_tags($valores->item(0)->nodeValue) ,
				"ganhadores" => $valores->item(1)->nodeValue
			);
			$idx_cidades++;
			$cont_estado -= $valores->item(1)->nodeValue;
		}
		
		if($count_estado == 0){
			$pega_estado = true;
		}
	}
	
	$resultado["premio_proximo"] = $sorteio[21];
	$resultado["data_proximo"] = $sorteio[22];
	$resultado["arrecadacao"] = $sorteio[23];
	$resultado["acumulado_especial"] = $sorteio[24];
	
}

function getMegaResults($concurso){  
	$page = "http://www1.caixa.gov.br/loterias/loterias/megasena/megasena_pesquisa_new.asp";
	$page .= "?submeteu=sim";
	$page .= "&opcao=concurso";
	$page .= "&txtConcurso=".strval($concurso);

	parseResult($page);
	
	print_r($resultado);
	
	die();

	$max = getMaxConcurso($table);

	/*** loop over the table rows ***/ 
	foreach ($rows as $row){ 

		/*** get each column by tag name ***/ 
		$cols = $row->getElementsByTagName('td'); 

		/*** echo the values ***/ 
		$concurso_parse = $cols->item(0)->nodeValue; 

		if (($max - 9)> $concurso_parse) continue;

		$data = explode("/",$cols->item(1)->nodeValue,3);
		$bola1 = $cols->item(2)->nodeValue;
		$bola2 = $cols->item(3)->nodeValue;
		$bola3 = $cols->item(4)->nodeValue;
		$bola4 = $cols->item(5)->nodeValue;
		$bola5 = $cols->item(6)->nodeValue;
		$bola6 = $cols->item(7)->nodeValue;
		$arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(8)->nodeValue));
		$ganhadores_6 = $cols->item(9)->nodeValue;
		$rateio_6 = str_replace(",",".",str_replace(".","",$cols->item(10)->nodeValue));
		$ganhadores_5 = $cols->item(11)->nodeValue;
		$rateio_5 = str_replace(",",".",str_replace(".","",$cols->item(12)->nodeValue));
		$ganhadores_4 = $cols->item(13)->nodeValue;
		$rateio_4 = str_replace(",",".",str_replace(".","",$cols->item(14)->nodeValue));
		$acumulado_5 = str_replace(",",".",str_replace(".","",$cols->item(16)->nodeValue));
		$estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(17)->nodeValue));
		$acumulado_virada = str_replace(",",".",str_replace(".","",$cols->item(18)->nodeValue));

		$data_sorteio = $data[2]."-".$data[1]."-".$data[0];

		$query =  " INSERT INTO ".$table." VALUES ( ";
		$query .= " $concurso_parse, ";
		$query .= " '$data_sorteio', ";
		$query .= " $bola1, ";
		$query .= " $bola2, ";
		$query .= " $bola3, ";
		$query .= " $bola4, ";
		$query .= " $bola5, ";
		$query .= " $bola6, ";
		$query .= " '$arrecadacao_total', ";
		$query .= " $ganhadores_6, ";
		$query .= " '$rateio_6', ";
		$query .= " $ganhadores_5, ";
		$query .= " '$rateio_5', ";
		$query .= " $ganhadores_4, ";
		$query .= " '$rateio_4', ";
		$query .= " '$acumulado_5', ";
		$query .= " '$estimativa_premio', ";
		$query .= " '$acumulado_virada', ";
		$query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

		$query .= " data_sorteio = '$data_sorteio', ";
		$query .= " bola1 = $bola1, ";
		$query .= " bola2 = $bola2, ";
		$query .= " bola3 = $bola3, ";
		$query .= " bola4 = $bola4, ";
		$query .= " bola5 = $bola5, ";
		$query .= " bola6 = $bola6, ";
		$query .= " arrecadacao_total = '$arrecadacao_total', ";
		$query .= " ganhadores_6 = $ganhadores_6, ";
		$query .= " rateio_6 = '$rateio_6', ";
		$query .= " ganhadores_5 = $ganhadores_5, ";
		$query .= " rateio_5 = '$rateio_5', ";
		$query .= " ganhadores_4 = $ganhadores_4, ";
		$query .= " rateio_4 = '$rateio_4', ";
		$query .= " acumulado_5 = '$acumulado_5', ";
		$query .= " estimativa_premio = '$estimativa_premio', ";
		$query .= " acumulado_virada = '$acumulado_virada', ";
		$query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

		openDB();
		mysql_query($query);
		closeDB();

	}

	/*  
	$parse_result = array(
	"category" => "mega",
	"concursos" => array(
	"max_save" => $max,
	"max_parse" => $concurso_parse
	)
	);
	//echo "<br/> MEGASENA - maior salvo: ".$max."<br/> ultimo parseado: ".$parse; 
	echo json_encode($parse_result, JSON_NUMERIC_CHECK);
	*/
}

if(isset($_GET["concurso"])){
	getMegaResults($_GET["concurso"]);
} else {
	echo "concurso não informado";
}
  

?>
