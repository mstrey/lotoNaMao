<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}

function saveMegasena($rows, $table){  

  $max = getMaxConcurso($table);
  
  /*** loop over the table rows ***/ 
  foreach ($rows as $row){ 

    /*** get each column by tag name ***/ 
    $cols = $row->getElementsByTagName('td'); 

    $index = 0;

    /*** echo the values ***/ 
    $concurso_parse = $cols->item($index++)->nodeValue; echo $concurso_parse."|";

    if (($max - 9)> $concurso_parse) continue;

    $data = explode("/",$cols->item($index++)->nodeValue,3); print_r($data)."|";
    $bola1 = $cols->item($index++)->nodeValue; echo $bola1."|";
    $bola2 = $cols->item($index++)->nodeValue; echo $bola2."|";
    $bola3 = $cols->item($index++)->nodeValue; echo $bola3."|";
    $bola4 = $cols->item($index++)->nodeValue; echo $bola4."|";
    $bola5 = $cols->item($index++)->nodeValue; echo $bola5."|";
    $bola6 = $cols->item($index++)->nodeValue; echo $bola6."|";
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $arrecadacao_total."|";
    $ganhadores_6 = $cols->item($index++)->nodeValue; echo $ganhadores_6."|";
    $cidade_6 = $cols->item($index++)->nodeValue; echo $cidade_6."|";
    $UF_6 = $cols->item($index++)->nodeValue; echo $UF_6."|";
    $rateio_6 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $rateio_6."|";
    $ganhadores_5 = $cols->item($index++)->nodeValue; echo $ganhadores_5."|";
    $rateio_5 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $rateio_5."|";
    $ganhadores_4 = $cols->item($index++)->nodeValue; echo $ganhadores_4."|";
    $rateio_4 = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $rateio_4."|";
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $acumulado."|";
    $acumulado_vlr = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $acumulado_vlr."|";
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $estimativa_premio."|";
    $acumulado_natal = str_replace(",",".",str_replace(".","",$cols->item($index++)->nodeValue)); echo $acumulado_natal."|";

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0]; echo $data_sorteio."|";

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " '$concurso_parse', ";
    $query .= " '$data_sorteio', ";
    $query .= " '$bola1', ";
    $query .= " '$bola2', ";
    $query .= " '$bola3', ";
    $query .= " '$bola4', ";
    $query .= " '$bola5', ";
    $query .= " '$bola6', ";
    $query .= " '$arrecadacao_total', ";
    $query .= " '$ganhadores_6', ";
    $query .= " '$rateio_6', ";
    $query .= " '$ganhadores_5', ";
    $query .= " '$rateio_5', ";
    $query .= " '$ganhadores_4', ";
    $query .= " '$rateio_4', ";
    $query .= " '$acumulado_vlr', ";
    $query .= " '$estimativa_premio', ";
    $query .= " '$acumulado_natal', ";
    $query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

    $query .= " data_sorteio = '$data_sorteio', ";
    $query .= " bola1 = '$bola1', ";
    $query .= " bola2 = '$bola2', ";
    $query .= " bola3 = '$bola3', ";
    $query .= " bola4 = '$bola4', ";
    $query .= " bola5 = '$bola5', ";
    $query .= " bola6 = '$bola6', ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_6 = '$ganhadores_6', ";
    $query .= " rateio_6 = '$rateio_6', ";
    $query .= " ganhadores_5 = '$ganhadores_5', ";
    $query .= " rateio_5 = '$rateio_5', ";
    $query .= " ganhadores_4 = '$ganhadores_4', ";
    $query .= " rateio_4 = '$rateio_4', ";
    $query .= " acumulado_5 = '$acumulado_vlr', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " acumulado_virada = '$acumulado_natal', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

    openDB();
    mysql_query($query);
    echo "<br />".$query."<br />";
    closeDB();

  }
  
  $parse_result = array(
  			"category" => "mega",
  			"concursos" => array(
  					"max_save" => $max,
  					"max_parse" => $concurso_parse
  					)
  			);
  //echo "<br/> MEGASENA - maior salvo: ".$max."<br/> ultimo parseado: ".$parse; 
  echo json_encode($parse_result, JSON_NUMERIC_CHECK);
  
}

?>
