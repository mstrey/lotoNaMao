<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}

function saveQuina($rows, $table){  
  
  $max = getMaxConcurso($table);
  
  /*** loop over the table rows ***/ 
  foreach ($rows as $row){ 

    /*** get each column by tag name ***/ 
    $cols = $row->getElementsByTagName('td'); 

    /*** echo the values ***/ 
    $concurso = $cols->item(0)->nodeValue; 

    if (($max - 9)> $concurso) continue;

    $data = explode("/",$cols->item(1)->nodeValue,3);
    $bola1 = $cols->item(2)->nodeValue;
    $bola2 = $cols->item(3)->nodeValue;
    $bola3 = $cols->item(4)->nodeValue;
    $bola4 = $cols->item(5)->nodeValue;
    $bola5 = $cols->item(6)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(7)->nodeValue));
    $ganhadores_5 = $cols->item(8)->nodeValue;
    $rateio_5 = str_replace(",",".",str_replace(".","",$cols->item(9)->nodeValue));
    $ganhadores_4 = $cols->item(10)->nodeValue;
    $rateio_4 = str_replace(",",".",str_replace(".","",$cols->item(11)->nodeValue));
    $ganhadores_3 = $cols->item(12)->nodeValue;
    $rateio_3 = str_replace(",",".",str_replace(".","",$cols->item(13)->nodeValue));
    $acumulado = str_replace(",",".",str_replace(".","",$cols->item(15)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(16)->nodeValue));
    $acumulado_sao_joao = str_replace(",",".",str_replace(".","",$cols->item(17)->nodeValue));

    $data_sorteio = $data[2]."-".$data[1]."-".$data[0];

    $query =  " INSERT INTO ".$table." VALUES ( ";
    $query .= " $concurso, ";
    $query .= " '$data_sorteio', ";
    $query .= " $bola1, ";
    $query .= " $bola2, ";
    $query .= " $bola3, ";
    $query .= " $bola4, ";
    $query .= " $bola5, ";
    $query .= " '$arrecadacao_total', ";
    $query .= " $ganhadores_5, ";
    $query .= " '$rateio_5', ";
    $query .= " $ganhadores_4, ";
    $query .= " '$rateio_4', ";
    $query .= " $ganhadores_3, ";
    $query .= " '$rateio_3', ";
    $query .= " '$acumulado', ";
    $query .= " '$estimativa_premio', ";
    $query .= " '$acumulado_sao_joao', ";
	$query .= " null, null, sysdate() )	ON DUPLICATE KEY UPDATE ";

    $query .= " data_sorteio = '$data_sorteio', ";
    $query .= " bola1 = $bola1, ";
    $query .= " bola2 = $bola2, ";
    $query .= " bola3 = $bola3, ";
    $query .= " bola4 = $bola4, ";
    $query .= " bola5 = $bola5, ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_5 = $ganhadores_5, ";
    $query .= " rateio_5 = '$rateio_5', ";
    $query .= " ganhadores_4 = $ganhadores_4, ";
    $query .= " rateio_4 = '$rateio_4', ";
    $query .= " ganhadores_3 = $ganhadores_3, ";
    $query .= " rateio_3 = '$rateio_3', ";
    $query .= " acumulado_5 = '$acumulado', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " acumulado_sao_joao = '$acumulado_sao_joao', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

	openDB();
    mysql_query($query);
	closeDB();
	
  }
  echo "<br/> QUINA - maior salvo: ".$max."<br/> ultimo parseado: ".$concurso; 
}

?>
