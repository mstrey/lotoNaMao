<?php
include_once 'connection.php';
include_once 'menu.php';
include_once 'error.php';

if (!$main) {
	error();
}

function getJsonConcurso($table, $concurso){
	$query = " SELECT * FROM ".$table." WHERE concurso = ".$concurso." ; ";
    printJson($query);
}
  
function getJsonMax($table){
    $query = " SELECT MAX( concurso ) AS max_conc FROM ".$table." where 1=1; ";
    printJson($query);
}
  
  
function printJson($query){
	openDB();
    $resultSet = mysql_query($query);
	$resultado = mysql_fetch_array($resultSet, MYSQL_ASSOC);
	closeDB();
 
	echo json_encode($resultado, JSON_NUMERIC_CHECK);
	die();
}

?>
