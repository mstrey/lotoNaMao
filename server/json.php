<?php
include_once 'connection.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}

function getConcurso($table, $concurso){  
	$query = " SELECT * FROM ".$table." WHERE concurso = ".anti_sql_injection($concurso)." ; ";
	
	openDB();
	$resultSet = mysql_query($query);
	closeDB();
	
	$resultado = mysql_fetch_array($resultSet, MYSQL_ASSOC);
	die( json_encode($resultado, JSON_NUMERIC_CHECK));
}

?>
