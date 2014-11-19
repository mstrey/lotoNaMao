<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}
  
function saveLotofacil($rows, $table){  
  
  $max_save = getMaxConcurso($table);
  
    /*** loop over the table rows ***/ 
  foreach ($rows as $row){ 

    /*** get each column by tag name ***/ 
    $cols = $row->getElementsByTagName('td'); 

    $index = 0;
    /*** echo the values ***/ 
    $concurso_parse = $cols->item($index++)->nodeValue; 

    if (($max_save - 9) > $concurso_parse) continue;

    $data = explode("/",$cols->item($index++)->nodeValue,3);
    $bola1 = $cols->item($index++)->nodeValue;
    $bola2 = $cols->item($index++)->nodeValue;
    $bola3 = $cols->item($index++)->nodeValue;
    $bola4 = $cols->item($index++)->nodeValue;
    $bola5 = $cols->item($index++)->nodeValue;
    $bola6 = $cols->item($index++)->nodeValue;
    $bola7 = $cols->item($index++)->nodeValue;
    $bola8 = $cols->item($index++)->nodeValue;
    $bola9 = $cols->item($index++)->nodeValue;
    $bola10 = $cols->item($index++)->nodeValue;
    $bola11 = $cols->item($index++)->nodeValue;
    $bola12 = $cols->item($index++)->nodeValue;
    $bola13 = $cols->item($index++)->nodeValue;
    $bola14 = $cols->item($index++)->nodeValue;
    $bola15 = $cols->item($index++)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $ganhadores_15 = $cols->item($index++)->nodeValue;
    $cidade_15 = $cols->item($index++)->nodeValue;
    $uf_15 = $cols->item($index++)->nodeValue;
    $ganhadores_14 = $cols->item($index++)->nodeValue;
    $ganhadores_13 = $cols->item($index++)->nodeValue;
    $ganhadores_12 = $cols->item($index++)->nodeValue;
    $ganhadores_11 = $cols->item($index++)->nodeValue;
    $rateio_15 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $rateio_14 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $rateio_13 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $rateio_12 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $rateio_11 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));
    $acumulado_especial = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue));

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0];

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " '$concurso_parse', ";
    $query .= " '$data_sorteio', ";
    $query .= " '$bola1', ";
    $query .= " '$bola2', ";
    $query .= " '$bola3', ";
    $query .= " '$bola4', ";
    $query .= " '$bola5', ";
    $query .= " '$bola6', ";
    $query .= " '$bola7', ";
    $query .= " '$bola8', ";
    $query .= " '$bola9', ";
    $query .= " '$bola10', ";
    $query .= " '$bola11', ";
    $query .= " '$bola12', ";
    $query .= " '$bola13', ";
    $query .= " '$bola14', ";
    $query .= " '$bola15', ";
    $query .= " '$arrecadacao_total', ";
    $query .= " '$ganhadores_15', ";
    $query .= " '$ganhadores_14', ";
    $query .= " '$ganhadores_13', ";
    $query .= " '$ganhadores_12', ";
    $query .= " '$ganhadores_11', ";
    $query .= " '$rateio_15', ";
    $query .= " '$rateio_14', ";
    $query .= " '$rateio_13', ";
    $query .= " '$rateio_12', ";
    $query .= " '$rateio_11', ";
    $query .= " '$acumulado', ";
    $query .= " '$estimativa_premio', ";
    $query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

    $query .= " data_sorteio = '$data_sorteio', ";
    $query .= " bola1 = $bola1, ";
    $query .= " bola2 = $bola2, ";
    $query .= " bola3 = $bola3, ";
    $query .= " bola4 = $bola4, ";
    $query .= " bola5 = $bola5, ";
    $query .= " bola6 = $bola6, ";
    $query .= " bola7 = $bola7, ";
    $query .= " bola8 = $bola8, ";
    $query .= " bola9 = $bola9, ";
    $query .= " bola10 = $bola10, ";
    $query .= " bola11 = $bola11, ";
    $query .= " bola12 = $bola12, ";
    $query .= " bola13 = $bola13, ";
    $query .= " bola14 = $bola14, ";
    $query .= " bola15 = $bola15, ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_15 = $ganhadores_15, ";
    $query .= " ganhadores_14 = $ganhadores_14, ";
    $query .= " ganhadores_13 = $ganhadores_13, ";
    $query .= " ganhadores_12 = $ganhadores_12, ";
    $query .= " ganhadores_11 = $ganhadores_11, ";
    $query .= " rateio_15 = '$rateio_15', ";
    $query .= " rateio_14 = '$rateio_14', ";
    $query .= " rateio_13 = '$rateio_13', ";
    $query .= " rateio_12 = '$rateio_12', ";
    $query .= " rateio_11 = '$rateio_11', ";
    $query .= " acumulado_15 = '$acumulado', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

	openDB();
    mysql_query($query);
    print_r($query);
    echo "<br /><br />";
	closeDB();
	
  }
  
    $parse_result = array(
	"category" => "lotofacil",
 	"concursos" => array(
 		"max_save" => $max_save,
		"max_parse" => $concurso_parse
		)
	);
    echo json_encode($parse_result, JSON_NUMERIC_CHECK);

}

?>
