<?php
/* 
 * Criamos a instância de nosso cliente de webservice. 
 * Especificamos a localização e o namespace do servidor de 
 * webservice. 
 * Passando null no primeiro parâmetro do construtor indicamos 
 * que o webservice irá trabalhar no modo não WSDL. 
 */ 
$options = array( 
 		'location' => 'http://maicon.strey.nom.br/loto/direct/megasena.php', 
 		'uri' => 'http://maicon.strey.nom.br/loto/direct/' 
); 

$client = new SoapClient(null, $options); 
 
 
/* 
 * No Objeto $client podemos usar os métodos da classe 
 * WsMegaSena disponível em nosso webservice. 
 */ 
echo $client->getResultado('1565') . "<br />";
