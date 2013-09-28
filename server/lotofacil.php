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

    /*** echo the values ***/ 
    $concurso_parse = $cols->item(0)->nodeValue; 

    if (($max_save - 9) > $concurso_parse) continue;

    $data = explode("/",$cols->item(1)->nodeValue,3);
    $bola1 = $cols->item(2)->nodeValue;
    $bola2 = $cols->item(3)->nodeValue;
    $bola3 = $cols->item(4)->nodeValue;
    $bola4 = $cols->item(5)->nodeValue;
    $bola5 = $cols->item(6)->nodeValue;
    $bola6 = $cols->item(7)->nodeValue;
    $bola7 = $cols->item(8)->nodeValue;
    $bola8 = $cols->item(9)->nodeValue;
    $bola9 = $cols->item(10)->nodeValue;
    $bola10 = $cols->item(11)->nodeValue;
    $bola11 = $cols->item(12)->nodeValue;
    $bola12 = $cols->item(13)->nodeValue;
    $bola13 = $cols->item(14)->nodeValue;
    $bola14 = $cols->item(15)->nodeValue;
    $bola15 = $cols->item(16)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(17)->nodeValue));
    $ganhadores_15 = $cols->item(18)->nodeValue;
    $ganhadores_14 = $cols->item(19)->nodeValue;
    $ganhadores_13 = $cols->item(20)->nodeValue;
    $ganhadores_12 = $cols->item(21)->nodeValue;
    $ganhadores_11 = $cols->item(22)->nodeValue;
    $rateio_15 = str_replace(",",".",str_replace(".","",$cols->item(23)->nodeValue));
    $rateio_14 = str_replace(",",".",str_replace(".","",$cols->item(24)->nodeValue));
    $rateio_13 = str_replace(",",".",str_replace(".","",$cols->item(25)->nodeValue));
    $rateio_12 = str_replace(",",".",str_replace(".","",$cols->item(26)->nodeValue));
    $rateio_11 = str_replace(",",".",str_replace(".","",$cols->item(27)->nodeValue));
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item(28)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(29)->nodeValue));

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
    $query .= " $bola7, ";
    $query .= " $bola8, ";
    $query .= " $bola9, ";
    $query .= " $bola10, ";
    $query .= " $bola11, ";
    $query .= " $bola12, ";
    $query .= " $bola13, ";
    $query .= " $bola14, ";
    $query .= " $bola15, ";
    $query .= " '$arrecadacao_total', ";
    $query .= " $ganhadores_15, ";
    $query .= " $ganhadores_14, ";
    $query .= " $ganhadores_13, ";
    $query .= " $ganhadores_12, ";
    $query .= " $ganhadores_11, ";
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
