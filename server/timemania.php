<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}
  
function saveTimemania($rows, $table){  
  
  $max_save = getMaxConcurso($table);
  
  /*** loop over the table rows ***/ 
  foreach ($rows as $row){ 

    /*** get each column by tag name ***/ 
    $cols = $row->getElementsByTagName('td'); 

    /*** echo the values ***/ 
    $parse = $cols->item(0)->nodeValue; 

    if (($max_save - 9)> $parse) continue;

    $data = explode("/",$cols->item(1)->nodeValue,3);
    $bola1 = $cols->item(2)->nodeValue;
    $bola2 = $cols->item(3)->nodeValue;
    $bola3 = $cols->item(4)->nodeValue;
    $bola4 = $cols->item(5)->nodeValue;
    $bola5 = $cols->item(6)->nodeValue;
    $bola6 = $cols->item(7)->nodeValue;
    $bola7 = $cols->item(8)->nodeValue;
    $time_coracao = $cols->item(9)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(10)->nodeValue));
    $ganhadores_7 = $cols->item(11)->nodeValue;
    $ganhadores_6 = $cols->item(12)->nodeValue;
    $ganhadores_5 = $cols->item(13)->nodeValue;
    $ganhadores_4 = $cols->item(14)->nodeValue;
    $ganhadores_3 = $cols->item(15)->nodeValue;
    $ganhadores_time = $cols->item(16)->nodeValue;
    $rateio_7 = str_replace(",",".",str_replace(".","",$cols->item(17)->nodeValue));
    $rateio_6 = str_replace(",",".",str_replace(".","",$cols->item(18)->nodeValue));
    $rateio_5 = str_replace(",",".",str_replace(".","",$cols->item(19)->nodeValue));
    $rateio_4 = str_replace(",",".",str_replace(".","",$cols->item(20)->nodeValue));
    $rateio_3 = str_replace(",",".",str_replace(".","",$cols->item(21)->nodeValue));
    $rateio_time = str_replace(",",".",str_replace(".","",$cols->item(22)->nodeValue));
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item(23)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(24)->nodeValue));

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0];

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " $concurso, ";
    $query .= " '$data_sorteio', ";
    $query .= " $bola1, ";
    $query .= " $bola2, ";
    $query .= " $bola3, ";
    $query .= " $bola4, ";
    $query .= " $bola5, ";
    $query .= " $bola6, ";
    $query .= " $bola7, ";
    $query .= " '$time_coracao', ";
    $query .= " '$arrecadacao_total', ";
    $query .= " $ganhadores_7, ";
    $query .= " $ganhadores_6, ";
    $query .= " $ganhadores_5, ";
    $query .= " $ganhadores_4, ";
    $query .= " $ganhadores_3, ";
    $query .= " $ganhadores_time, ";
    $query .= " '$rateio_7', ";
    $query .= " '$rateio_6', ";
    $query .= " '$rateio_5', ";
    $query .= " '$rateio_4', ";
    $query .= " '$rateio_3', ";
    $query .= " '$rateio_time', ";
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
    $query .= " time_coracao = '$time_coracao', ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_7 = $ganhadores_7, ";
    $query .= " ganhadores_6 = $ganhadores_6, ";
    $query .= " ganhadores_5 = $ganhadores_5, ";
    $query .= " ganhadores_4 = $ganhadores_4, ";
    $query .= " ganhadores_3 = $ganhadores_3, ";
    $query .= " ganhadores_time = $ganhadores_time, ";
    $query .= " rateio_7 = '$rateio_7', ";
    $query .= " rateio_6 = '$rateio_6', ";
    $query .= " rateio_5 = '$rateio_5', ";
    $query .= " rateio_4 = '$rateio_4', ";
    $query .= " rateio_3 = '$rateio_3', ";
    $query .= " rateio_time = '$rateio_time', ";
    $query .= " acumulado = '$acumulado', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

	openDB();
    mysql_query($query);
	closeDB();
	
  }
    $parse_result = array(
	"category" => "time",
 	"concursos" => array(
 		"max_save" => $max_save,
		"max_parse" => $parse
		)
	);
    echo json_encode($parse_result, JSON_NUMERIC_CHECK);
}

?>
