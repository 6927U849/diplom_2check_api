package client.api;

import utils.AnotherConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;



public class Client {
    String BASE_URL = AnotherConfig.BASE_URL;

    protected RequestSpecification getSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(AnotherConfig.BASE_URL)
                .build();
    }
}
