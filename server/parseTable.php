<?php

function parseTable($file){
	/*** a new dom object ***/ 
	$dom = new domDocument; 

	/*** load the html into the object ***/
	$dom->loadHTMLFile("cef/".$file); 

	/*** discard white space ***/ 
	$dom->preserveWhiteSpace = false; 

	/*** the table by its tag name ***/ 
	$tables = $dom->getElementsByTagName('table'); 

	/*** get all rows from the table ***/ 
	$trs = $tables->item(0)->getElementsByTagName('tr'); 
	return $trs;
}

?>