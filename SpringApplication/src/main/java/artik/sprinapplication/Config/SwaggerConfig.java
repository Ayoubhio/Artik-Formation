package artik.sprinapplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//    Dans cette classe, la méthode "api()" crée une instance de "Docket" qui configure Swagger pour générer la documentation de l'API RESTful. Elle spécifie également que
//    Swagger doit scanner toutes les classes de contrôleurs et toutes les méthodes de ces classes pour générer la documentation , il connait les controlleures par les annotations (@RestController" et "@RequestMapping").
//    La méthode "addResourceHandlers()" ajoute les ressources statiques pour l'interface utilisateur Swagger dans le registre des gestionnaires de ressources de Spring.
//    Swagger est accessible à l'adresse "http://localhost:8080/swagger-ui.html".

//@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport
{
@Bean
public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("artik.springapplication"))
            .paths(PathSelectors.any()) //pour creer la documentation a tous  , sinon on met paths("/rest.*") : pour creer de doc juste pour les controller qui ont un path commence par rest
            .build();
}
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Demo")
                .description("API pour le calcul du carré d'un nombre")
                .version("1.0")
                .build();
    }
//@Bean
//public Docket api2() {
//    return new Docket(DocumentationType.SWAGGER_2)
//            .groupName("u9a")
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(input -> input.startsWith("/api"))
//            .build()
//            .apiInfo(new ApiInfo(
//                    "Swagger for  on env ",
//                    "Just a swagger to execute on this app :)",
//                    version,
//                    "",
//                    new Contact( "Tracking Unifie Team", "", ""),
//                    "La Poste BSCC - Tracking Unifie (T&T Marchandise),",
//                    "",
//                    new ArrayList<VendorExtension>())
//            )
//            .tags(new Tag("metric","Envoi de metrics sur la durée d'appels WS, de parsing et post dans Kafka, d'appels Cassandra etc ..."));
//}
}

