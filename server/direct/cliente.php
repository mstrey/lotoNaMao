<?php
/* 
 * Criamos a instância de nosso cliente de webservice. 
 * Especificamos a localização e o namespace do servidor de 
 * webservice. 
 * Passando null no primeiro parâmetro do construtor indicamos 
 * que o webservice irá trabalhar no modo não WSDL. 
 */ 
$options = array( 
 		'location' => 'http://127.0.0.1/soap/server/server.php', 
 		'uri' => 'http://127.0.0.1/soap/server/' 
); 

$client = new SoapClient(null, $options); 
 
 
/* 
 * No Objeto $client podemos usar os métodos da classe 
 * SoapServerExemplo disponível em nosso webservice. 
 */ 
echo $client->mensagem('Maicon') . "<br />";
echo $client->soma(3, 5) . "<br />" ;
