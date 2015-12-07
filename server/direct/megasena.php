<?php

/* Criamos a instância do SoapServer.
 * A opção uri indica o namespace do webservice no servidor.
 * O primeiro parâmetro null indica que estamos trabalhando
 * com um webservice no modo não WSDL.
 */
$options = array('uri' => 'http://127.0.0.1/soap/server/');
$server = new SoapServer(null, $options);

/*
 * Informamos a classe em que o webservice irá se basear.
 * Podemos usar também o método addFunction() para adicionar
 * funções em nosso webservice.
 */
$server -> setClass('SoapServerExemplo');

/*
 * O método handle() processa a requisição SOAP e envia uma resposta
 * para o cliente.
 */
$server -> handle();

/*
 * A classe SoapServerExemplo será disponibilizada em nosso
 * webservice. Portanto temos disponíveis no webservice os métodos
 * mensagem e soma.
 */
class SoapServerExemplo {
	public function mensagem($nome) {
		return "Boas Vindas $nome !";
	}

	public function soma($a, $b) {
		return $a + $b;
	}

}
