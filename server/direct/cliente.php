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
 * SoapServerExemplo disponível em nosso webservice. 
 */ 
echo $client->mensagem('Maicon') . "<br />";
echo $client->soma(3, 5) . "<br />" ;
