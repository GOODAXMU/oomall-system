package cn.edu.xmu.oomall;


import cn.edu.xmu.oomall.common.util.JacksonUtil;
import cn.edu.xmu.oomall.common.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;


/**
 * @author zhibin lan
 * @date 2020-11-28
 */
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class TotalTest {
    private WebTestClient webClient;

    public TotalTest() {
        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:9800")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                .build();
    }

    /**
     * 测试克隆模板功能
     * 资源不存在
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel() throws Exception {

        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/clone").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试克隆模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/9/clone").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试克隆模板功能
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/13/clone").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试定义默认模板功能
     * 操作资源不存在
     *
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/default").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试定义默认模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/9/default").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试定义默认模板功能
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/13/default").header("authorization",token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }


    /**
     * 测试定义模板功能
     *
     * @throws Exception
     */
    @Test
    public void defineFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"测试名\",\"type\":0,\"unit\":500}";

        byte[] responseString = webClient.post().uri("/shops/1/freightmodels").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试定义模板功能
     * 模板名重复
     *
     * @throws Exception
     */
    @Test
    public void defineFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"测试模板5\",\"type\":0,\"unit\":500}";
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":802,\"errmsg\":\"运费模板名重复\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    @Test
    public void getFreightModelSummary() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/freightmodels/200").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    @Test
    public void getFreightModelSummary1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/freightmodels/9").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":[2020,12,2,20,33,8],\"gmtModified\":[2020,12,2,20,33,8]}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

    }

    @Test
    public void getFreightModelSummary2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/freightmodels/13").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }


    @Test
    public void getFreightModels() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":10,\"total\":4,\"pages\":1,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":[2020,12,2,20,33,8],\"gmtModified\":[2020,12,2,20,33,8]},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":[2020,12,2,20,33,8],\"gmtModified\":[2020,12,2,20,33,8]},{\"id\":11,\"name\":\"测试模板3\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":[2020,12,2,20,33,8],\"gmtModified\":[2020,12,2,20,33,8]},{\"id\":12,\"name\":\"测试模板4\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":[2020,12,2,20,33,8],\"gmtModified\":[2020,12,2,20,33,8]}]}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }

    @Test
    public void modifyFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"测试名\",\"unit\":500}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/200").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    @Test
    public void modifyFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 2000);
        String json = "{\"name\":\"测试模板3\",\"unit\":550}";

        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/12").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        log.debug(new String(responseString, "UTF-8"));

        String expectedResponse = "{\"errno\":802,\"errmsg\":\"运费模板名重复\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    @Test
    public void modifyFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"模板修改测试名\",\"unit\":550}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/9").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    @Test
    public void modifyFreightModel3() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"模板修改测试名\",\"unit\":550}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/13").header("authorization",token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    @Test
    public void deleteFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/200").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    @Test
    public void deleteFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/10").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    @Test
    public void deleteFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/13").header("authorization",token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        log.debug(token);
        return token;
    }
}
