<?php

function openDB(){
	$con = mysql_connect("mysql03-farm26","maicon04","L0t3r14") or die(mysql_error());
	$db = mysql_select_db("maicon04") or die(mysql_error());
}

function closeDB(){
mysql_close();
}
?>