<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}

function saveQuina($rows, $table){  
  
  $max_save = getMaxConcurso($table);
  
  /*** loop over the table rows ***/ 
  foreach ($rows as $row){ 

    /*** get each column by tag name ***/ 
    $cols = $row->getElementsByTagName('td'); 

    $index = 0;
    /*** echo the values ***/ 
    $concurso_parse = $cols->item($index++)->nodeValue; 

    if (($max_save - 9)> $concurso_parse) continue;

    $data = explode("/",$cols->item($index++)->nodeValue,3);
    $bola1 = $cols->item($index++)->nodeValue;
    $bola2 = $cols->item($index++)->nodeValue;
    $bola3 = $cols->item($index++)->nodeValue;
    $bola4 = $cols->item($index++)->nodeValue;
    $bola5 = $cols->item($index++)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $ganhadores_5 = $cols->item($index++)->nodeValue;
    $cidade_5 = $cols->item($index++)->nodeValue;
    $uf_5 = $cols->item($index++)->nodeValue;
    $rateio_5 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $ganhadores_4 = $cols->item($index++)->nodeValue;
    $rateio_4 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $ganhadores_3 = $cols->item($index++)->nodeValue;
    $rateio_3 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $acumulado_vlr = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $acumulado_sao_joao = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0];

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " '$concurso_parse', ";
    $query .= " '$data_sorteio', ";
    $query .= " '$bola1', ";
    $query .= " '$bola2', ";
    $query .= " '$bola3', ";
    $query .= " '$bola4', ";
    $query .= " '$bola5', ";
    $query .= " '$arrecadacao_total', ";
    $query .= " '$ganhadores_5', ";
    $query .= " '$rateio_5', ";
    $query .= " '$ganhadores_4', ";
    $query .= " '$rateio_4', ";
    $query .= " '$ganhadores_3', ";
    $query .= " '$rateio_3', ";
    $query .= " '$acumulado_vlr', ";
    $query .= " '$estimativa_premio', ";
    $query .= " '$acumulado_sao_joao', ";
	$query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

    $query .= " data_sorteio = '$data_sorteio', ";
    $query .= " bola1 = '$bola1', ";
    $query .= " bola2 = '$bola2', ";
    $query .= " bola3 = '$bola3', ";
    $query .= " bola4 = '$bola4', ";
    $query .= " bola5 = '$bola5', ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_5 = '$ganhadores_5', ";
    $query .= " rateio_5 = '$rateio_5', ";
    $query .= " ganhadores_4 = '$ganhadores_4', ";
    $query .= " rateio_4 = '$rateio_4', ";
    $query .= " ganhadores_3 = '$ganhadores_3', ";
    $query .= " rateio_3 = '$rateio_3', ";
    $query .= " acumulado_5 = '$acumulado_vlr', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " acumulado_sao_joao = '$acumulado_sao_joao', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

	openDB();
    mysql_query($query);
	closeDB();
	
  }
    
    $parse_result = array(
	"category" => "quina",
 	"concursos" => array(
 		"max_save" => $max_save,
		"max_parse" => $concurso_parse
		)
	);
    echo json_encode($parse_result, JSON_NUMERIC_CHECK);
}

?>
