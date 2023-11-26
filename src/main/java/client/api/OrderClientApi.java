package client.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.api.OrderApi;
import utils.AnotherConfig;

import static io.restassured.RestAssured.given;

public class OrderClientApi extends Client{
    private final String ORDER_URL = AnotherConfig.ORDER_URL;

    @Step("Создание заказа методом POST: {{Order_URL}}")
    public ValidatableResponse createOrder(OrderApi orderApi, String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(orderApi)
                .when()
                .post(ORDER_URL)
                .then();
    }

    @Step("Получение данных заказа GET: {{Order_URL}}")
    public ValidatableResponse getOrder(String accessToken) {
        return given().log().all()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_URL)
                .then();
    }

}
