package io.RCCFicoScoreSimulacion.client.api;

import io.RCCFicoScoreSimulacion.client.model.CatalogoEstados;
import io.RCCFicoScoreSimulacion.client.model.DomicilioPeticion;
import io.RCCFicoScoreSimulacion.client.ApiClient;
import io.RCCFicoScoreSimulacion.client.ApiException;
import io.RCCFicoScoreSimulacion.client.model.Consultas;
import io.RCCFicoScoreSimulacion.client.model.Creditos;
import io.RCCFicoScoreSimulacion.client.model.DomiciliosRespuesta;
import io.RCCFicoScoreSimulacion.client.model.Empleos;
import io.RCCFicoScoreSimulacion.client.model.Mensajes;
import io.RCCFicoScoreSimulacion.client.model.PersonaPeticion;
import io.RCCFicoScoreSimulacion.client.model.Respuesta;
import io.RCCFicoScoreSimulacion.client.model.Scores;

import okhttp3.OkHttpClient;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.junit.Before;

import java.util.concurrent.TimeUnit;

public class ApiTest {

	private Logger logger = LoggerFactory.getLogger(ApiTest.class.getName());
	private final RCCFicoScoreSimulacionApi api = new RCCFicoScoreSimulacionApi();
	private ApiClient apiClient = null;

	@Before()
	public void setUp() {
		this.apiClient = api.getApiClient();
		this.apiClient.setBasePath("the_url");
		OkHttpClient okHttpClient = new OkHttpClient()
				.newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
		apiClient.setHttpClient(okHttpClient);
	}

	@Test
	public void getReporteTest() throws ApiException {

		String xApiKey = "your_api_key";
		Boolean xFullReport = false;

		PersonaPeticion persona = new PersonaPeticion();
		DomicilioPeticion domicilio = new DomicilioPeticion();
		try {
			persona.setApellidoPaterno("ROBERTO");
			persona.setApellidoMaterno("SAHAGUN");
			persona.setApellidoAdicional(null);
			persona.setPrimerNombre("ZARAGOZA");
			persona.setSegundoNombre(null);
			persona.setFechaNacimiento("2001-01-01");
			persona.setRFC("SAZR010101");
			persona.setCURP(null);
			persona.setNacionalidad("MX");
			persona.setResidencia(null);
			persona.setEstadoCivil(null);
			persona.setSexo(null);
			persona.setClaveElectorIFE(null);
			persona.setNumeroDependientes(null);
			persona.setFechaDefuncion(null);
			persona.setDomicilio(null);

			domicilio.setDireccion("HIDALGO 32");
			domicilio.setColoniaPoblacion("CENTRO");
			domicilio.setDelegacionMunicipio("LA BARCA");
			domicilio.setCiudad("BENITO JUAREZ");
			domicilio.setEstado(CatalogoEstados.JAL);
			domicilio.setCP("47917");
			domicilio.setFechaResidencia(null);
			domicilio.setNumeroTelefono(null);
			domicilio.setTipoDomicilio(null);
			domicilio.setTipoAsentamiento(null);

			persona.setDomicilio(domicilio);

			Respuesta response = api.getReporte(xApiKey, persona, xFullReport);

			Assert.assertTrue(response.getFolioConsulta() != null);
			logger.info(response.toString());

			if (response.getFolioConsulta() != null && !xFullReport) {
				String folioConsulta = response.getFolioConsulta();

				Consultas consultas = api.getConsultas(folioConsulta, xApiKey);
				Assert.assertTrue(consultas.getConsultas() != null);
				logger.info(consultas.toString());

				Creditos creditos = api.getCreditos(folioConsulta, xApiKey);
				Assert.assertTrue(creditos.getCreditos() != null);
				logger.info(creditos.toString());

				DomiciliosRespuesta domicilios = api.getDomicilios(folioConsulta, xApiKey);
				Assert.assertTrue(domicilios.getDomicilios() != null);
				logger.info(domicilios.toString());

				Empleos empleos = api.getEmpleos(folioConsulta, xApiKey);
				Assert.assertTrue(empleos.getEmpleos() != null);
				logger.info(empleos.toString());

				Scores scores = api.getScores(folioConsulta, xApiKey);
				Assert.assertTrue(scores.getScores() != null);
				logger.info(scores.toString());

				Mensajes mensajes = api.getMensajes(folioConsulta, xApiKey);
				Assert.assertTrue(mensajes.getMensajes() != null);
				logger.info(mensajes.toString());
			}
		} catch (ApiException e) {
			logger.error(e.getResponseBody());
		}

	}

}