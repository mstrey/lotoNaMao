<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}
  
function saveDuplasena($rows, $table){  
  
  $max_save = getMaxConcurso($table);
  
  foreach ($rows as $row){ 

    $cols = $row->getElementsByTagName('td'); 
    $concurso_parse = $cols->item(0)->nodeValue; 

    if (($max_save - 9)> $concurso_parse) continue;

    $data = explode("/",$cols->item(1)->nodeValue,3);
    $bola1_s1 = $cols->item(2)->nodeValue;
    $bola2_s1 = $cols->item(3)->nodeValue;
    $bola3_s1 = $cols->item(4)->nodeValue;
    $bola4_s1 = $cols->item(5)->nodeValue;
    $bola5_s1 = $cols->item(6)->nodeValue;
    $bola6_s1 = $cols->item(7)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(8)->nodeValue));
    $ganhadores_6_s1 = $cols->item(9)->nodeValue;
    $rateio_6_s1 = str_replace(",",".",str_replace(".","",$cols->item(10)->nodeValue));
    $ganhadores_5_s1 = $cols->item(11)->nodeValue;
    $rateio_5_s1 = str_replace(",",".",str_replace(".","",$cols->item(12)->nodeValue));
    $ganhadores_4_s1 = $cols->item(13)->nodeValue;
    $rateio_4_s1 = str_replace(",",".",str_replace(".","",$cols->item(14)->nodeValue));
    $acumulado_s1 = str_replace(",",".",str_replace(".","",$cols->item(16)->nodeValue));
    $bola1_s2 = $cols->item(17)->nodeValue;
    $bola2_s2 = $cols->item(18)->nodeValue;
    $bola3_s2 = $cols->item(19)->nodeValue;
    $bola4_s2 = $cols->item(20)->nodeValue;
    $bola5_s2 = $cols->item(21)->nodeValue;
    $bola6_s2 = $cols->item(22)->nodeValue;
    $ganhadores_6_s2 = $cols->item(23)->nodeValue;
    $rateio_6_s2 = str_replace(",",".",str_replace(".","",$cols->item(24)->nodeValue));
    $ganhadores_5_s2 = $cols->item(25)->nodeValue;
    $rateio_5_s2 = str_replace(",",".",str_replace(".","",$cols->item(26)->nodeValue));
    $ganhadores_4_s2 = $cols->item(27)->nodeValue;
    $rateio_4_s2 = str_replace(",",".",str_replace(".","",$cols->item(28)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(29)->nodeValue));

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0];

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " $concurso_parse, ";
    $query .= " '$data_sorteio', ";
    $query .= " $bola1_s1, ";
    $query .= " $bola2_s1, ";
    $query .= " $bola3_s1, ";
    $query .= " $bola4_s1, ";
    $query .= " $bola5_s1, ";
    $query .= " $bola6_s1, ";
    $query .= " '$arrecadacao_total', ";
    $query .= " $ganhadores_6_s1, ";
    $query .= " '$rateio_6_s1', ";
    $query .= " $ganhadores_5_s1, ";
    $query .= " '$rateio_5_s1', ";
    $query .= " $ganhadores_4_s1, ";
    $query .= " '$rateio_4_s1', ";
    $query .= " '$acumulado_s1', ";
    $query .= " $bola1_s2, ";
    $query .= " $bola2_s2, ";
    $query .= " $bola3_s2, ";
    $query .= " $bola4_s2, ";
    $query .= " $bola5_s2, ";
    $query .= " $bola6_s2, ";
    $query .= " $ganhadores_6_s2, ";
    $query .= " '$rateio_6_s2', ";
    $query .= " $ganhadores_5_s2, ";
    $query .= " '$rateio_5_s2', ";
    $query .= " $ganhadores_4_s2, ";
    $query .= " '$rateio_4_s2', ";
    $query .= " '$estimativa_premio', ";
    $query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

    $query .= " data_sorteio = '$data_sorteio', ";
    $query .= " bola1_s1 = $bola1_s1, ";
    $query .= " bola2_s1 = $bola2_s1, ";
    $query .= " bola3_s1 = $bola3_s1, ";
    $query .= " bola4_s1 = $bola4_s1, ";
    $query .= " bola5_s1 = $bola5_s1, ";
    $query .= " bola6_s1 = $bola6_s1, ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_6_s1 = $ganhadores_6_s1, ";
    $query .= " rateio_6_s1 = '$rateio_6_s1', ";
    $query .= " ganhadores_5_s1 = $ganhadores_5_s1, ";
    $query .= " rateio_5_s1 = '$rateio_5_s1', ";
    $query .= " ganhadores_4_s1 = $ganhadores_4_s1, ";
    $query .= " rateio_4_s1 = '$rateio_4_s1', ";
    $query .= " bola1_s2 = $bola1_s2, ";
    $query .= " bola2_s2 = $bola2_s2, ";
    $query .= " bola3_s2 = $bola3_s2, ";
    $query .= " bola4_s2 = $bola4_s2, ";
    $query .= " bola5_s2 = $bola5_s2, ";
    $query .= " bola6_s2 = $bola6_s2, ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_6_s2 = $ganhadores_6_s2, ";
    $query .= " rateio_6_s2 = '$rateio_6_s2', ";
    $query .= " ganhadores_5_s2 = $ganhadores_5_s2, ";
    $query .= " rateio_5_s2 = '$rateio_5_s2', ";
    $query .= " ganhadores_4_s2 = $ganhadores_4_s2, ";
    $query .= " rateio_4_s2 = '$rateio_4_s2', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

    openDB();
    mysql_query($query);
    closeDB();
	
  }
  
    $parse_result = array(
	"category" => "dupla",
 	"concursos" => array(
 		"max_save" => $max_save,
		"max_parse" => $concurso_parse
		)
	);
    echo json_encode($parse_result, JSON_NUMERIC_CHECK);
  
}

?>
