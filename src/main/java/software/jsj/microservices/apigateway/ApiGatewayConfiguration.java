package software.jsj.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder)  {
        /* Configure custom routes  */

        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f
                            .addRequestHeader("MyHeader", "MyURI")
                            .addRequestParameter("MyParam", "MyValue"))
                        .uri("http://httpbin.org:80")) //customised routes
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-jjj/**")
                        .filters(f -> f.rewritePath("/currency-conversion-jjj/(?<segment>.*)", "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();

    }

}
