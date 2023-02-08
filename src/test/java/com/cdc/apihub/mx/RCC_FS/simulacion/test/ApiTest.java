package com.cdc.apihub.mx.RCC_FS.simulacion.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdc.apihub.mx.RCC_FS.simulacion.client.ApiClient;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.ApiException;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.api.RCCFSApi;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.model.CatalogoEstados;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.model.DomicilioPeticion;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.model.PersonaPeticion;
import com.cdc.apihub.mx.RCC_FS.simulacion.client.model.Respuesta;

import okhttp3.OkHttpClient;

public class ApiTest {

	private Logger logger = LoggerFactory.getLogger(ApiTest.class.getName());
	private final RCCFSApi api = new RCCFSApi();
	private ApiClient apiClient = null;
    private String xApiKey = "your_api_key";
    private String url = "the_url";

	@Before()
	public void setUp() {
		this.apiClient = api.getApiClient();
		this.apiClient.setBasePath(url);
		OkHttpClient okHttpClient = new OkHttpClient()
				.newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
		apiClient.setHttpClient(okHttpClient);
	}

	@Test
	public void getReporteTest() throws ApiException {

		PersonaPeticion persona = new PersonaPeticion();
		DomicilioPeticion domicilio = new DomicilioPeticion();
		try {
			persona.setApellidoPaterno("SESENTAYDOS");
			persona.setApellidoMaterno("PRUEBA");
			persona.setPrimerNombre("JUAN");
			persona.setSegundoNombre(null);
			persona.setFechaNacimiento("1965-08-09");
			persona.setRFC("SEPJ650809JG1");
			persona.setCURP(null);
			persona.setNacionalidad("MX");
			persona.setResidencia(null);
			persona.setEstadoCivil(null);
			persona.setSexo(null);
			persona.setClaveElectorIFE(null);
			persona.setNumeroDependientes(null);
			persona.setFechaDefuncion(null);
			persona.setDomicilio(null);

			domicilio.setDireccion("PASADISO ENCONTRADO 58");
			domicilio.setColoniaPoblacion("MONTEVIDEO");
			domicilio.setDelegacionMunicipio("GUSTAVO A MADERO");
			domicilio.setCiudad("CIUDAD DE MÉXICO");
			domicilio.setEstado(CatalogoEstados.CDMX);
			domicilio.setCP("07730");
			domicilio.setFechaResidencia(null);
			domicilio.setNumeroTelefono(null);
			domicilio.setTipoDomicilio(null);
			domicilio.setTipoAsentamiento(null);

			persona.setDomicilio(domicilio);

			Respuesta response = api.getReporte(xApiKey, persona);

			logger.info(response.toString());
			Assert.assertTrue(response.getFolioConsulta() != null);
			
		} catch (ApiException e) {
			logger.error(e.getResponseBody());
		}

	}

}