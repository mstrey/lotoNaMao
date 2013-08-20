<?php

include_once 'connection.php';

function getMaxConcurso($table){
	$query = " SELECT max(concurso) FROM ".$table."; ";
	
	openDB();
	$resultSet = mysql_query($query);
	closeDB();
	
	$maior = mysql_fetch_array($resultSet);
	
	return $maior[0];
}

?>
