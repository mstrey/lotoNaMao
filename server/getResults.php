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

$htmls = array(
			'mega' => 'D_MEGA.HTM', 
			'lotofacil' => 'D_LOTFAC.HTM', 
			'quina' => 'D_QUINA.HTM', 
			'lotomania' => 'D_LOTMAN.HTM', 
			'dupla' => 'D_DPLSEN.HTM', 
			'time' => 'D_TIMEMA.HTM');

$tables = array(
			'mega' => 'megasena_resultados', 
			'lotofacil' => 'lotofacil_resultados', 
			'quina' => 'quina_resultados', 
			'lotomania' => 'lotomania_resultados', 
			'dupla' => 'duplasena_resultados', 
			'time' => 'timemania_resultados');

if (!isset($_GET["loto"])) error();
if (is_null($_GET["loto"])) error();
if (empty($_GET["loto"]) AND "0" != $_GET["loto"]) error();
if ($_GET["loto"] < 0 OR $_GET["loto"] > 5) error();

$loteria = $_GET["loto"];

$json_concurso = 0 ;
if (isset($_GET["concurso"])){
	$json_concurso = $_GET["concurso"];
	if ($json_concurso == 0){
		getJsonMax($tables[$loteria]);
	} else {
		getJsonConcurso($tables[$loteria], $_GET["concurso"]);
	}
	die();
}
  
writeMenu('main');


case 'todos': 
  echo "TODOS";
  foreach ($tables as $key => $value) {
    saveLoto($key);
  }
  break;


function saveLoto($index){

	$file = $htmls[$index];
	$rows = parseTable($file);

	switch ($index) {
	
	case 'mega': 
	  echo "MEGA-SENA";
	  saveMegasena($rows, $tables[$index]);
	  break;
	
	case 'lotofacil': 
	  echo "LOTO FACIL";
	  saveLotofacil($rows, $tables[$index]);
	  break;
	
	case 'quina': 
	  echo "QUINA";
	  saveQuina($rows, $tables[$index]);
	  break;
	
	case 'lotomania': 
	  echo "LOTO MANIA";
	  saveLotomania($rows, $tables[$index]);
	  break;
	
	case 'dupla': 
	  echo "DUPLA SENA";
	  saveDuplasena($rows, $tables[$index]);
	  break;
	
	case 'time': 
	  echo "TIME MANIA";
	  saveTimemania($rows, $tables[$index]);
	  break;
	
	default:
	  error();
	  break;
	
	}

}
	
?>
