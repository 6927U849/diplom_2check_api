package order;

import client.api.OrderClientApi;
import client.api.UserClientApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.api.BurgersApi;
import order.api.OrderApi;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.api.UserApi;
import user.api.UserGeneration;
import utils.ThreadSleep;

@RunWith(Parameterized.class)
public class OrderTest {
    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClientApi userClient = new UserClientApi();
    private final OrderClientApi orderClient = new OrderClientApi();
    private final String[] ingredients;
    private final boolean needAccessToken;
    private final int expectedStatusCode;
    private final String expectedStatus;
    private String token;

    public OrderTest(String[] ingredients, boolean needAccessToken, int expectedStatusCode, String expectedStatus) {
        this.ingredients = ingredients;
        this.needAccessToken = needAccessToken;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters(name =
            "Создание заказа. Тестовые данные: burger = {0}, needTkn = {1}, statusCode = {2}, status = {3}")
    public static Object[] getIngredientData() {
        return new Object[][]{
                // burger, needAccessToken, statusCode, status
                {BurgersApi.FIRST_BURGER, true, 200, "done"},
                {BurgersApi.SECOND_BURGER, true, 200, "done"},
                {BurgersApi.THIRTH_BURGER, true, 200, "done"},
                {BurgersApi.FOURTH_BURGER, true, 200, "done"},
                {BurgersApi.FIFTH_BURGER, true, 200, "done"},
                {BurgersApi.FIFTH_BURGER, false, 200, null},
                {BurgersApi.EMPTY_BURGER, true, 400, null},
                {BurgersApi.INCORRECT_BURGER, true, 500, null}
        };
    }

    @Before
    public void waiting() throws InterruptedException {
        ThreadSleep.run();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClient.delete(token);
        }
    }

    @Test
    @DisplayName("Создание заказа")
    public void checkOrderCreation() {
        UserApi userApi = userGeneration.getDefault();
        ValidatableResponse userData = userClient.create(userApi);
        token = needAccessToken ? userData.extract().path("accessToken") : "";

        ValidatableResponse orderData = orderClient.createOrder(new OrderApi(ingredients), token);
        int statusCode = orderData.extract().statusCode();

        if (expectedStatusCode < 500) {
            Assert.assertEquals(expectedStatus, orderData.extract().path("order.status"));
        }
        Assert.assertEquals(expectedStatusCode, statusCode);
    }

}

