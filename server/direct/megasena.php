<?php
include_once 'Utils.php';

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
		$urlMega = "http://www1.caixa.gov.br/loterias/loterias/megasena/megasena_pesquisa_new.asp";
		// $urlMega .= "?submeteu=sim\&opcao=concurso\&txtConcurso=1765";
		$postFields = array(
						'submeteu' => 'sim'
						,'opcao' => 'concurso'
						,'txtConcurso' => $concurso
					);

		// Define a URL original
		curl_setopt($ch, CURLOPT_URL,$urlMega);
		
		// Imita o comportamento padrão dos navegadores: manipular cookies 
		curl_setopt ($ch, CURLOPT_COOKIEJAR, 'cookie.txt'); 
		
		// define que browser que está acessando é o firefox para enganar o site
		curl_setopt($ch, CURLOPT_USERAGENT, "Mozilome/44.0.2403.125 Safari/537.36");

		// define o header de idioma do browser
	    curl_setopt($ch, CURLOPT_HEADER, array(
				'Accept-Language:pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4'
				, 'Cache-Control:max-age=0'
				, 'Proxy-Connection:keep-alive'
				, 'Upgrade-Insecure-Requests:1'
				, 'Host:www1.caixa.gov.br'
				, 'Cookie:security=true; ASPSESSIONIDQQSRSQTC=LDJKBNOAEMFFEJMKINMFMAKC'
				)
			);
				
		// Executa a primeira requisição 	
		$store = curl_exec ($ch); 	
		
		// Define a URL vedadeira para ser chamada
		curl_setopt($ch, CURLOPT_URL,$urlMega);
		
		// define parametros post do concurso que será recuperado
		// curl_setopt($ch, CURLOPT_POST, true);
		// curl_setopt($ch, CURLOPT_POSTFIELDS, $postFields);
		
		// Define o tipo de transferência (Padrão: 1) e o tempo (10)
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_TIMEOUT, 10); 
		
		// Executa a segunda requisição
		$result = curl_exec($ch); 
		
		curl_close($ch);
		return $result;
		
	}

}
