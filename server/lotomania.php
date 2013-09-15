<?php
include_once 'connection.php';
include_once 'getMax.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}
  
function saveLotomania($rows, $table){  
  
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
    $bola6 = $cols->item(7)->nodeValue;
    $bola7 = $cols->item(8)->nodeValue;
    $bola8 = $cols->item(8)->nodeValue;
    $bola9 = $cols->item(10)->nodeValue;
    $bola10 = $cols->item(11)->nodeValue;
    $bola11 = $cols->item(12)->nodeValue;
    $bola12 = $cols->item(13)->nodeValue;
    $bola13 = $cols->item(14)->nodeValue;
    $bola14 = $cols->item(15)->nodeValue;
    $bola15 = $cols->item(16)->nodeValue;
    $bola16 = $cols->item(17)->nodeValue;
    $bola17 = $cols->item(18)->nodeValue;
    $bola18 = $cols->item(19)->nodeValue;
    $bola19 = $cols->item(20)->nodeValue;
    $bola20 = $cols->item(21)->nodeValue;
    $arrecadacao_total = str_replace(",",".",str_replace(".","",$cols->item(22)->nodeValue));
    $ganhadores_20 = $cols->item(23)->nodeValue;
    $ganhadores_19 = $cols->item(24)->nodeValue;
    $ganhadores_18 = $cols->item(25)->nodeValue;
    $ganhadores_17 = $cols->item(26)->nodeValue;
    $ganhadores_16 = $cols->item(27)->nodeValue;
    $ganhadores_0 = $cols->item(28)->nodeValue;
    $rateio_20 = str_replace(",",".",str_replace(".","",$cols->item(29)->nodeValue));
    $rateio_19 = str_replace(",",".",str_replace(".","",$cols->item(30)->nodeValue));
    $rateio_18 = str_replace(",",".",str_replace(".","",$cols->item(31)->nodeValue));
    $rateio_17 = str_replace(",",".",str_replace(".","",$cols->item(32)->nodeValue));
    $rateio_16 = str_replace(",",".",str_replace(".","",$cols->item(33)->nodeValue));
    $rateio_0 = str_replace(",",".",str_replace(".","",$cols->item(34)->nodeValue));
    $acumulado_20 = str_replace(",",".",str_replace(".","",$cols->item(29)->nodeValue));
    $acumulado_19 = str_replace(",",".",str_replace(".","",$cols->item(30)->nodeValue));
    $acumulado_18 = str_replace(",",".",str_replace(".","",$cols->item(31)->nodeValue));
    $acumulado_17 = str_replace(",",".",str_replace(".","",$cols->item(32)->nodeValue));
    $acumulado_16 = str_replace(",",".",str_replace(".","",$cols->item(33)->nodeValue));
    $acumulado_0 = str_replace(",",".",str_replace(".","",$cols->item(34)->nodeValue));
    $estimativa_premio = str_replace(",",".",str_replace(".","",$cols->item(35)->nodeValue));

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
    $query .= " $bola8, ";
    $query .= " $bola9, ";
    $query .= " $bola10, ";
    $query .= " $bola11, ";
    $query .= " $bola12, ";
    $query .= " $bola13, ";
    $query .= " $bola14, ";
    $query .= " $bola15, ";
    $query .= " $bola16, ";
    $query .= " $bola17, ";
    $query .= " $bola18, ";
    $query .= " $bola19, ";
    $query .= " $bola20, ";
    $query .= " '$arrecadacao_total', ";
    $query .= " $ganhadores_20, ";
    $query .= " $ganhadores_19, ";
    $query .= " $ganhadores_18, ";
    $query .= " $ganhadores_17, ";
    $query .= " $ganhadores_16, ";
    $query .= " $ganhadores_0, ";
    $query .= " '$rateio_20', ";
    $query .= " '$rateio_19', ";
    $query .= " '$rateio_18', ";
    $query .= " '$rateio_17', ";
    $query .= " '$rateio_16', ";
    $query .= " '$rateio_0', ";
    $query .= " '$acumulado_20', ";
    $query .= " '$acumulado_19', ";
    $query .= " '$acumulado_18', ";
    $query .= " '$acumulado_17', ";
    $query .= " '$acumulado_16', ";
    $query .= " '$acumulado_0', ";
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
    $query .= " bola16 = $bola16, ";
    $query .= " bola17 = $bola17, ";
    $query .= " bola18 = $bola18, ";
    $query .= " bola19 = $bola19, ";
    $query .= " bola20 = $bola20, ";
    $query .= " arrecadacao_total = '$arrecadacao_total', ";
    $query .= " ganhadores_20 = $ganhadores_20, ";
    $query .= " ganhadores_19 = $ganhadores_19, ";
    $query .= " ganhadores_18 = $ganhadores_18, ";
    $query .= " ganhadores_17 = $ganhadores_17, ";
    $query .= " ganhadores_16 = $ganhadores_16, ";
    $query .= " ganhadores_0 = $ganhadores_0, ";
    $query .= " rateio_20 = '$rateio_20', ";
    $query .= " rateio_19 = '$rateio_19', ";
    $query .= " rateio_18 = '$rateio_18', ";
    $query .= " rateio_17	= '$rateio_17', ";
    $query .= " rateio_16 = '$rateio_16', ";
    $query .= " rateio_0 = '$rateio_0', ";
    $query .= " acumulado_20 = '$acumulado_20', ";
    $query .= " acumulado_19 = '$acumulado_19', ";
    $query .= " acumulado_18 = '$acumulado_18', ";
    $query .= " acumulado_17 = '$acumulado_17', ";
    $query .= " acumulado_16 = '$acumulado_16', ";
    $query .= " acumulado_0 = '$acumulado_0', ";
    $query .= " estimativa_premio = '$estimativa_premio', ";
    $query .= " local = null, local_gps = null, data_inclusao = sysdate() ;";

	openDB();
    mysql_query($query);
	closeDB();
	
  }
  echo "<br/> LOTOMANIA - maior salvo: ".$max."<br/> ultimo parseado: ".$concurso; 
}

?>
