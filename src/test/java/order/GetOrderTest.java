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
import user.api.UserApi;
import user.api.UserGeneration;

import java.util.List;

import static java.util.Arrays.asList;

public class GetOrderTest {
    private final UserGeneration userGeneration = new UserGeneration();
    private final UserClientApi userClient = new UserClientApi();
    private final OrderClientApi orderClient = new OrderClientApi();
    private String token;

    @Before
    public void setUp() {
        UserApi user = userGeneration.getDefault();
        ValidatableResponse createUser = userClient.create(user);
        token = createUser.extract().path("accessToken");
        orderClient.createOrder(new OrderApi(BurgersApi.FIFTH_BURGER), token);
    }

    @After
    public void cleanUp() {
        if (token != null) {
            userClient.delete(token);
        }
    }

    @Test
    @DisplayName("Получение заказа")
    public void getOrder() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        Assert.assertEquals(200, getOrder.extract().statusCode());
    }

    @Test
    @DisplayName("Проверка переданных ингредиетов в заказе")
    public void checkGetIngredients() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        List<String> ingredients = asList(BurgersApi.FIFTH_BURGER);
        Assert.assertEquals(ingredients, getOrder.extract().path("orders.ingredients[0]"));
    }

    @Test
    @DisplayName("Проверка статуса заказа в структуре")
    public void checkStatus() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        Assert.assertEquals("done", getOrder.extract().path("orders.status[0]"));
    }

    @Test
    @DisplayName("Проверка, что в структуре есть ID")
    public void checkID() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        Assert.assertNotNull(getOrder.extract().path("orders.id"));
    }

    @Test
    @DisplayName("ППроверка номера заказа в структуре")
    public void checkNumber() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        Assert.assertNotNull(getOrder.extract().path("orders.number"));
    }

    @Test
    @DisplayName("Проверка даты заказа в структуре")
    public void checkDate() {
        ValidatableResponse getOrder = orderClient.getOrder(token);
        Assert.assertNotNull(getOrder.extract().path("orders.createdAt"));
        Assert.assertNotNull(getOrder.extract().path("orders.updatedAt"));
    }

    @Test
    @DisplayName("Получение заказа неавторизованным пользователем")
    public void getOrderWithOutAuthorization() {
        ValidatableResponse getOrder = orderClient.getOrder("");
        Assert.assertEquals(401, getOrder.extract().statusCode());
    }

}
