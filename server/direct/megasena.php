<?php

/* Criamos a instância do SoapServer.
 * A opção uri indica o namespace do webservice no servidor.
 * O primeiro parâmetro null indica que estamos trabalhando
 * com um webservice no modo não WSDL.
 */
$options = array('uri' => 'http://maicon.strey.nom.br/loto/direct/');
$server = new SoapServer(null, $options);

/*
 * Informamos a classe em que o webservice irá se basear.
 * Podemos usar também o método addFunction() para adicionar
 * funções em nosso webservice.
 */
$server -> setClass('WsMegaSena');

/*
 * O método handle() processa a requisição SOAP e envia uma resposta
 * para o cliente.
 */
$server -> handle();

/*
 * A classe WsMegaSena será disponibilizada em nosso
 * webservice. Portanto temos disponíveis no webservice os métodos.
 */
class WsMegaSena {
	public function getResultado($concurso) {
		$urlMega = "http://www1.caixa.gov.br/loterias/loterias/megasena/megasena_pesquisa_new.asp?submeteu=sim";
		$urlMega .= "&opcao=concurso&txtConcurso=1765";
		return getURL($urlMega); 
	}

	public function soma($a, $b) {
		return $a + $b;
	}
	
	public function getURL($url) {
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$output = curl_exec($ch);
		curl_close($ch);
		return $output;
	}	
	

}
