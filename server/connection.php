<?php

function openDB(){
	$con = mysql_connect("mysql03-farm26","maicon04","L0t3r14") or die(mysql_error());
	$db = mysql_select_db("maicon04") or die(mysql_error());
}

function closeDB(){
mysql_close();
}

function anti_sql_injection($str) {
    if (!is_numeric($str)) {
        $str = get_magic_quotes_gpc() ? stripslashes($str) : $str;
        $str = function_exists('mysql_real_escape_string') ? mysql_real_escape_string($str) : mysql_escape_string($str);
    }
    return $str;
}

?>
