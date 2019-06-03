package com.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.camel.domain.Pessoa;

@Component
public class RouteJetty1 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	
    	from("jetty:http://localhost:8082/hello")
        .to("direct:teste");
    	
    	from("direct:teste")
    	.setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.GET))
        .to("http4://localhost:8081/hello?bridgeEndpoint=true")
        .transform().simple("Hello from Camel http4");
    	
        from("jetty:http://localhost:8081/hello")
            .transform().simple("Hello from Camel");
        
        from("jetty:http://localhost:8083/hello")
        .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.GET))
        .to("http4://localhost:8081/hello?bridgeEndpoint=true")
        .transform().simple("Hello from Camel http45");
        
        JacksonDataFormat format = new ListJacksonDataFormat(Pessoa.class);
        
        from("jetty:http://localhost:8084/hello")
        .transform().simple("MENSAGEM")
        .process( new Processor() {
        	@Override
			public void process(Exchange exchange) throws Exception {
        		exchange.setProperty("teste", "setter mensagem");
        		log.info("Body 1 '{}'", exchange.getIn().getBody(String.class));
			}
        })
       // .to("http4://localhost:8080/api/all?bridgeEndpoint=true") // String.class
       // .to("http4://localhost:8080/api/obj?bridgeEndpoint=true") // Pessoa.class
        .to("http4://localhost:8080/api/xml?bridgeEndpoint=true") 
        .convertBodyTo(String.class)
       // .unmarshal().jacksonxml()
       // .unmarshal(format)
       // .marshal().json(JsonLibrary.Jackson)
        .process( new Processor() {
        	@Override
			public void process(Exchange exchange) throws Exception {
        		log.info("Teste '{}'", exchange.getProperty("teste"));
        		log.info("Body IN '{}'", exchange.getIn().getBody().toString());
//				log.info("Body OUT '{}'", exchange.getOut().getBody());
			}
        });
    }

}
