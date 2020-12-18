package cn.edu.xmu.oomall;

import cn.edu.xmu.oomall.common.util.JwtHelper;
import cn.edu.xmu.oomall.common.util.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;


/**
 * @author zhibin lan
 * @date 2020-11-28
 */
@SpringBootTest(classes = TestApplication.class)
@Slf4j
public class TotalTest2 {
    private WebTestClient webClient;

    public TotalTest2() {
        this.webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:9800")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                .build();
    }

    /**
     * 计算运费
     *
     * @throws Exception
     */
    @Test
    public void calculateFreight() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "[{\"count\":1,\"skuId\":1275}]";
        byte[] responseString = webClient.post().uri("/region/201/price").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":8}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }

    /**
     * 计算运费
     *
     * @throws Exception 不可达
     */
    @Test
    public void calculateFreight1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "[{\"count\":1,\"skuId\":1275}]";
        byte[] responseString = webClient.post().uri("/region/2001/price").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":805}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/clone").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/9/clone").header("authorization", token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

        String temp = new String(responseString, "UTF-8");
        int startIndex = temp.indexOf("id");
        int endIndex = temp.indexOf("name");
        String id = temp.substring(startIndex + 4, endIndex - 2);

        byte[] queryResponseString = webClient.get().uri("/shops/1/freightmodels/" + id).header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.errno").isEqualTo(ResponseCode.OK.getCode())
                .jsonPath("$.errmsg").isEqualTo(ResponseCode.OK.getMessage())
                .jsonPath("$.data").exists()
                .returnResult()
                .getResponseBodyContent();

        JSONAssert.assertEquals(new String(queryResponseString, "UTF-8"), new String(responseString, "UTF-8"), true);
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/13/clone").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/default").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/10/default").header("authorization", token)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

        byte[] queryResponseString = webClient.get().uri("/shops/1/freightmodels/10").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.errno").isEqualTo(ResponseCode.OK.getCode())
                .jsonPath("$.errmsg").isEqualTo(ResponseCode.OK.getMessage())
                .jsonPath("$.data").exists()
                .returnResult()
                .getResponseBodyContent();

        JSONAssert.assertEquals(expectedResponse, new String(queryResponseString, "UTF-8"), false);
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/13/default").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
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

        byte[] responseString = webClient.post().uri("/shops/1/freightmodels").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();


        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

        String temp = new String(responseString, "UTF-8");
        int startIndex = temp.indexOf("id");
        int endIndex = temp.indexOf("name");
        String id = temp.substring(startIndex + 4, endIndex - 2);

        byte[] queryResponseString = webClient.get().uri("/shops/1/freightmodels/" + id).header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.errno").isEqualTo(ResponseCode.OK.getCode())
                .jsonPath("$.errmsg").isEqualTo(ResponseCode.OK.getMessage())
                .jsonPath("$.data").exists()
                .returnResult()
                .getResponseBodyContent();

        JSONAssert.assertEquals(new String(queryResponseString, "UTF-8"), new String(responseString, "UTF-8"), true);
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
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":802}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试获取模板概要功能
     * 操作的资源id不存在
     *
     * @throws Exception
     */
    @Test
    public void getFreightModelSummary() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels/200").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试获取模板概要功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void getFreightModelSummary1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels/9").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"unit\":500,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

    }

    /**
     * 测试获取模板概要功能
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     */
    @Test
    public void getFreightModelSummary2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels/13").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试获取运费模板功能
     * 全部获取
     *
     * @throws Exception
     */
    @Test
    public void getFreightModels() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        System.out.println(new String(responseString, "UTF-8"));
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":10,\"total\":6,\"pages\":1,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":11,\"name\":\"测试模板3\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":12,\"name\":\"测试模板4\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":22,\"name\":\"ight model/100g\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":23,\"name\":\"piece model/2\",\"type\":1,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}\n";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }


    /**
     * 测试获取运费模板功能
     * 按名字获取
     *
     * @throws Exception
     */
    @Test
    public void getFreightModels1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels?name=测试模板4").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        System.out.println(new String(responseString, "UTF-8"));
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":10,\"total\":1,\"pages\":1,\"list\":[{\"id\":12,\"name\":\"测试模板4\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }

    /**
     * 测试获取运费模板功能
     * 指定页大小
     *
     * @throws Exception
     */
    @Test
    public void getFreightModels2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels?pageSize=2").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();
        System.out.println(new String(responseString, "UTF-8"));

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":2,\"total\":6,\"pages\":3,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }

    /**
     * 测试获取运费模板功能
     * 指定页大小和页数
     *
     * @throws Exception
     */
    @Test
    public void getFreightModels3() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels?pageSize=2&page=2").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();
        System.out.println(new String(responseString, "UTF-8"));

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":2,\"pageSize\":2,\"total\":6,\"pages\":3,\"list\":[{\"id\":11,\"name\":\"测试模板3\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":12,\"name\":\"测试模板4\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);
    }


    /**
     * 测试修改模板功能
     * 操作的资源id不存在
     *
     * @throws Exception
     */
    @Test
    public void modifyFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"测试名\",\"unit\":500}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/200").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试修改模板功能
     * 运费模板名重复
     *
     * @throws Exception
     */
    @Test
    public void modifyFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"测试模板3\",\"unit\":550}";

        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/12").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":802}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试修改模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void modifyFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"模板修改测试名\",\"unit\":550}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/9").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

        byte[] queryResponseString = webClient.get().uri("/shops/1/freightmodels/9").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.errno").isEqualTo(ResponseCode.OK.getCode())
                .jsonPath("$.errmsg").isEqualTo(ResponseCode.OK.getMessage())
                .jsonPath("$.data").exists()
                .returnResult()
                .getResponseBodyContent();

        JSONAssert.assertEquals(expectedResponse, new String(queryResponseString, "UTF-8"), false);
    }

    /**
     * 测试修改模板功能
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     */
    @Test
    public void modifyFreightModel3() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"name\":\"模板修改测试名\",\"unit\":550}";
        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/13").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试删除模板功能
     * 操作的资源id不存在
     *
     * @throws Exception
     */
    @Test
    public void deleteFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/200").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试删除模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void deleteFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/10").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

        byte[] queryResponseString = webClient.get().uri("/shops/1/freightmodels/10").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.errno").isEqualTo(ResponseCode.RESOURCE_ID_NOTEXIST.getCode())
                .jsonPath("$.errmsg").isEqualTo(ResponseCode.RESOURCE_ID_NOTEXIST.getMessage())
                .returnResult()
                .getResponseBodyContent();

        expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(queryResponseString, "UTF-8"), false);

    }

    /**
     * 测试删除模板功能
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     */
    @Test
    public void deleteFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/13").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }


    /**
     * 测试获取计件运费模板明细
     * 成功
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void getPieceFreightModels1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        //todo 过该测试（gmt时间没设置）
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels/11/pieceItems").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试删除重量运费模板明细
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deleteWeightFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/weightItems/209").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试删除重量运费模板明细
     * 操作的资源id不存在
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deleteWeightFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/weightItems/10000").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试删除重量运费模板明细
     * 成功
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deleteWeightFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/weightItems/200").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

    }

    /**
     * 测试修改重量运费模板明细
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyWeightFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"abovePrice\":30,\"fiftyPrice\":14,\"firstWeight\":3,\"firstWeightPrice\":10,\"hundredPrice\":16,\"regionId\":210,\"tenPrice\":12,\"trihunPrice\":18}";
        byte[] responseString = webClient.put().uri("/shops/1/weightItems/209").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试修改重量运费模板明细
     * 操作的资源id不存在
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyWeightFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"abovePrice\":30,\"fiftyPrice\":14,\"firstWeight\":3,\"firstWeightPrice\":10,\"hundredPrice\":16,\"regionId\":210,\"tenPrice\":12,\"trihunPrice\":18}";
        byte[] responseString = webClient.put().uri("/shops/1/weightItems/10000").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试修改重量运费模板明细
     * 运费模板中该地区已经定义
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyWeightFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"abovePrice\":30,\"fiftyPrice\":14,\"firstWeight\":3,\"firstWeightPrice\":10,\"hundredPrice\":16,\"regionId\":200,\"tenPrice\":12,\"trihunPrice\":18}";
        byte[] responseString = webClient.put().uri("/shops/1/weightItems/201").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":803}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }


    /**
     * 测试修改计件运费模板明细
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyPieceFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"additionalItemPrice\":16,\"additionalItems\":2,\"firstItem\":3,\"firstItemPrice\":12,\"regionId\":210}";
        byte[] responseString = webClient.put().uri("/shops/1/pieceItems/209").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试修改计件运费模板明细
     * 操作的资源id不存在
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyPieceFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"additionalItemPrice\":16,\"additionalItems\":2,\"firstItem\":3,\"firstItemPrice\":12,\"regionId\":210}";
        byte[] responseString = webClient.put().uri("/shops/1/pieceItems/10000").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试修改计件运费模板明细
     * 运费模板中该地区已经定义
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void modifyPieceFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        String json = "{\"additionalItemPrice\":16,\"additionalItems\":2,\"firstItem\":3,\"firstItemPrice\":12,\"regionId\":200}";
        byte[] responseString = webClient.put().uri("/shops/1/pieceItems/201").header("authorization", token)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":803}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);
    }

    /**
     * 测试删除计件运费模板明细
     * 操作的资源id不是自己的对象
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deletePieceFreightModel() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/pieceItems/209").header("authorization", token)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }

    /**
     * 测试删除计件运费模板明细
     * 操作的资源id不存在
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deletePieceFreightModel1() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/pieceItems/10000").header("authorization", token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":504}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false);

    }


    /**
     * 测试删除计件运费模板明细
     * 成功
     *
     * @throws Exception
     * @author zhibin lan
     */
    @Test
    public void deletePieceFreightModel2() throws Exception {
        String token = creatTestToken(1L, 1L, 1000);
        byte[] responseString = webClient.delete().uri("/shops/1/pieceItems/200").header("authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), true);

    }


    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        log.debug(token);
        return token;
    }
}
