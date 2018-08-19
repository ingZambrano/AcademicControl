package com.avior.academic.app;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.avior.academic.comunes.ConexionesBD;
import com.avior.academic.pojo.ConexionBase;
import com.avior.academic.pojo.VersionServicios;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.avior.academic")
@EnableSwagger2
public class Application {

	private static final Logger logger=Logger.getLogger(Application.class);
	
	private ConexionBase conBase;
	private static Scanner sc;
	private static String host, port, usr, pass;
	private static VersionServicios verServicios;
	
	@Autowired
	public void setVersionServicios(VersionServicios versionServicios){
		
		versionServicios.setEmpresa(verServicios.getEmpresa());
		versionServicios.setFecha(verServicios.getFecha());
		versionServicios.setMensaje(verServicios.getMensaje());
		versionServicios.setProgramador(verServicios.getProgramador());
		versionServicios.setVersion(verServicios.getVersion());
	}
	
	@Autowired
	public void setConBase(ConexionBase conBase) {
		this.conBase = conBase;
		this.conBase.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		this.conBase.setIpBase(host); //172.21.5.159
		this.conBase.setPortBase(port); //1433
		this.conBase.setUserBD(usr); //sa
		this.conBase.setPassBD(pass); //C3ntavo
		this.conBase.setUrl("jdbc:sqlserver://"+conBase.getIpBase()+":"+conBase.getPortBase());
		this.conBase.setDataSource(ConexionesBD.getDataSource(this.conBase));

	}
	
    public static void main(String[] args) throws Throwable {
    	
    	sc = new Scanner(System.in);	

        System.out.print("Introduzca la ip de la base de datos, puede ser localhost:");       
        host = sc.nextLine();  
        System.out.print("Introduzca el puerto donde escucha la base de datos:");
        port= sc.nextLine();
        System.out.print("Introduzca el usuario de la base:");
        usr= sc.nextLine();
        System.out.print("Introduzca la contraseña:");
        pass=sc.nextLine();
       
      

        
        
    	if(ConexionesBD.testConexionBD(host, port, usr, pass)){
    		verServicios = new VersionServicios();    		
    		verServicios.setEmpresa("Avior - Academic + Control");
    		verServicios.setFecha("24/05/2017");
    		verServicios.setMensaje("Desarrollamos tecnología como tu la necesitas");
    		verServicios.setProgramador("J. Manuel Zambrano");
    		verServicios.setVersion("1.5");
    		
    		logger.info("Iniciando servicios "+verServicios.getEmpresa());
    		logger.info("Version "+verServicios.getVersion());
    		logger.info("Creado por "+verServicios.getProgramador());
    		logger.info(verServicios.getFecha());
    		logger.info(verServicios.getMensaje());
    		
    		SpringApplication.run(Application.class, args);
    	}else{
    		logger.error("\n\nEl sistema no puedo iniciar\n\nLos datos de conexión que ingresaste son incorrectos");
    	}
    	
        
    }

   
    
    @Bean
    public Docket academicApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Authentication.class)
                .apiInfo(apiInfo())
                //.securitySchemes(newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.avior.academic.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @SuppressWarnings("unused")
	private ApiKey apiKey(){
        logger.info("En la API Key");
        return new ApiKey("MyAPIKey", "keyname", "header");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Avior REST API para Academic Control")
                .description("API de Servicios de Academic Control para Apps")
                .contact("Avior")
                .version("0.1")
                .build();
    }


}
