# rcc-ficoscore-simulacion-client-java [![GitHub Packages](https://img.shields.io/badge/Maven&nbsp;package-Last&nbsp;version-lemon)](https://github.com/orgs/APIHub-CdC/packages?repo_name=rcc-ficoscore-simulacion-client-java) 

Simula el reporta del historial crediticio con los Campos Asociados a Nómina, el cumplimiento de pago de los compromisos que la persona ha adquirido con entidades financieras, no financieras e instituciones comerciales que dan crédito o participan en actividades afines al crédito.<br/><img src='https://github.com/APIHub-CdC/imagenes-cdc/blob/master/circulo_de_credito-apihub.png' height='37' width='160'/></p><br/>

## Requisitos

1. Java >= 1.7
2. Maven >= 3.3

## Instalación

Para la instalación de las dependencias se deberá ejecutar el siguiente comando:

```shell
mvn install -Dmaven.test.skip=true
```

> **NOTA:** Este fragmento del comando *-Dmaven.test.skip=true* evitará que se lance la prueba unitaria.


## Guía de inicio

### Paso 1. Agregar el producto a la aplicación

Al iniciar sesión seguir los siguientes pasos:

 1. Dar clic en la sección "**Mis aplicaciones**".
 2. Seleccionar la aplicación.
 3. Ir a la pestaña de "**Editar '@tuApp**' ".
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/edit_applications.jpg" width="900">
    </p>
 4. Al abrirse la ventana emergente, seleccionar el producto.
 5. Dar clic en el botón "**Guardar App**":
    <p align="center">
      <img src="https://github.com/APIHub-CdC/imagenes-cdc/blob/master/selected_product.jpg" width="400">
    </p>

### Paso 2. Capturar los datos de la petición

Los siguientes datos a modificar se encuentran en ***src/test/java/com/cdc/apihub/mx/RCC_FS/simulacion/test/ApiTest.java***

Es importante contar con el setUp() que se encargará de inicializar la url. Modificar la URL ***('the_url')***, como se muestra en el siguiente fragmento de código:

```java
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
```

De igual manera, en el archivo **ApiTest**, se deberá modificar el siguiente fragmento de código con los datos correspondientes:

> **NOTA:** Para más ejemplos de simulación, consulte la colección de Postman.

```java
@Test
public void getReporteTest() throws ApiException {

	PersonaPeticion persona = new PersonaPeticion();
	DomicilioPeticion domicilio = new DomicilioPeticion();
	try {
		persona.setApellidoPaterno("SESENTAYDOS");
		persona.setApellidoMaterno("PRUEBA");
		persona.setPrimerNombre("JUAN");
		persona.setFechaNacimiento("1965-08-09");
		persona.setRFC("SEPJ650809JG1");
		persona.setNacionalidad("MX");

		domicilio.setDireccion("PASADISO ENCONTRADO 58");
		domicilio.setColoniaPoblacion("MONTEVIDEO");
		domicilio.setDelegacionMunicipio("GUSTAVO A MADERO");
		domicilio.setCiudad("CIUDAD DE MÉXICO");
		domicilio.setEstado(CatalogoEstados.CDMX);
		domicilio.setCP("07730");

		persona.setDomicilio(domicilio);

		Respuesta response = api.getReporte(xApiKey, persona);

		logger.info(response.toString());
		Assert.assertTrue(response.getFolioConsulta() != null);
		
	} catch (ApiException e) {
		logger.error(e.getResponseBody());
	}

}
```

### Paso 3. Ejecutar la prueba unitaria

Teniendo los pasos anteriores ya solo falta ejecutar la prueba unitaria, con el siguiente comando:

```shell
mvn test -Dmaven.install.skip=true
```
