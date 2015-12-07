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
		$urlMega = "http://www1.caixa.gov.br/loterias/loterias/megasena/megasena_pesquisa_new.asp?submeteu=sim";
		$urlMega .= "\&opcao=concurso\&txtConcurso=1765";
		echo $urlMega;
		
		$ch = curl_init($urlMega);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_USERAGENT, "Mozilome/44.0.2403.125 Safari/537.36");
		//curl_setopt($ch, CURLOPT_ENCODING, "gzip, deflate, sdch");
		$header = array(
				'Accept-Language:pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4'
//				, 'Cache-Control:max-age=0'
//				, 'Cookie:security=true; ASPSESSIONIDQQSRSQTC=LDJKBNOAEMFFEJMKINMFMAKC'
//				, 'Host:www1.caixa.gov.br'
//				, 'Proxy-Connection:keep-alive'
//				, 'Upgrade-Insecure-Requests:1'
								);
// --compressed
    curl_setopt($ch, CURLOPT_HEADER, $header);

    $output = curl_exec($ch);
		curl_close($ch);
		return $output;
		
	}

}
