<?php 

$main = true;

include_once 'connection.php';
include_once 'parseTable.php';
include_once 'menu.php';
include_once 'error.php';
include_once 'json.php';

// include games
include_once 'megasena.php';
include_once 'lotofacil.php';
include_once 'quina.php';
include_once 'lotomania.php';
include_once 'duplasena.php';
include_once 'timemania.php';

#variables
$htmls = array(
			'mega' => 'D_MEGA.HTM', 
			'lotofacil' => 'D_LOTFAC.HTM', 
			'quina' => 'D_QUINA.HTM', 
			'lotomania' => 'D_LOTMAN.HTM', 
			'dupla' => 'D_DPLSEN.HTM', 
			'time' => 'D_TIMEMA.HTM');

$tables_names = array(
			'mega' => 'megasena_resultados', 
			'lotofacil' => 'lotofacil_resultados', 
			'quina' => 'quina_resultados', 
			'lotomania' => 'lotomania_resultados', 
			'dupla' => 'duplasena_resultados', 
			'time' => 'timemania_resultados');

#verify get
if (!isset($_GET["loto"])) error();
if (is_null($_GET["loto"])) error();
if (empty($_GET["loto"]) AND "0" != $_GET["loto"]) error();
if ($_GET["loto"] < 0 OR $_GET["loto"] > 5) error();

$loteria = $_GET["loto"];

function saveLoto($index, $files, $tables){

	$file = $files[$index];
	$rows = parseTable($file);

	switch ($index) {
	
	case 'mega': 
	  saveMegasena($rows, $tables[$index]);
	  break;
	
	case 'lotofacil': 
	  saveLotofacil($rows, $tables[$index]);
	  break;
	
	case 'quina': 
	  saveQuina($rows, $tables[$index]);
	  break;
	
	case 'lotomania': 
	  saveLotomania($rows, $tables[$index]);
	  break;
	
	case 'dupla': 
	  saveDuplasena($rows, $tables[$index]);
	  break;
	
	case 'time': 
	  saveTimemania($rows, $tables[$index]);
	  break;
	
	default:
	  error();
	  break;
	
	}

}

function retornoJson($json_concurso, $tables, $loto){

	if ($json_concurso == 0){
		getJsonMax($tables[$loto]);
	} else {
		getJsonConcurso($tables[$loto], $_GET["concurso"]);
	}
} 
 
writeMenu('main');

if($loteria == "todos"){
  foreach ($tables_names as $key => $value) {
  	echo "<br/>";
    saveLoto($key, $htmls, $tables_names);
  }
  die;
}

if (isset($_GET["concurso"])){
  retornoJson($_GET["concurso"], $tables_names, $loto);
  die();
}

saveLoto($loteria, $htmls, $tables_names);


	
?>
