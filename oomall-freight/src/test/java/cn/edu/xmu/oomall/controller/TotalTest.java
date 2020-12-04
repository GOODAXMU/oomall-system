package cn.edu.xmu.oomall.controller;


import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.FreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.FreightModelPutRequest;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhibin lan
 * @date 2020-11-28
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
public class TotalTest {
    private WebTestClient webClient;

    public TotalTest(){
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
    public void cloneFreightModel() throws Exception{
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/clone")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    /**
     * 测试克隆模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel1() throws Exception{
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/9/clone")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    /**
     * 测试定义默认模板功能
     * 操作资源不存在
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel() throws Exception {
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/200/default")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    /**
     * 测试定义默认模板功能
     * 成功
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel1() throws Exception {
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels/9/default")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }

    /**
     * 测试定义模板功能
     *
     * @throws Exception
     */
    @Test
    public void defineFreightModel() throws Exception {
        FreightModelDefineRequest freightModelDefineRequest = new FreightModelDefineRequest();
        freightModelDefineRequest.setName("测试名");
        freightModelDefineRequest.setType(0);
        freightModelDefineRequest.setUnit(Long.valueOf(500));
        String json = JSON.toJSONString(freightModelDefineRequest);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels")
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    /**
     * 测试定义模板功能
     * 模板名重复
     * @throws Exception
     */
    @Test
    public void defineFreightModel1() throws Exception {
        FreightModelDefineRequest freightModelDefineRequest = new FreightModelDefineRequest();
        freightModelDefineRequest.setName("测试模板");
        freightModelDefineRequest.setType(0);
        freightModelDefineRequest.setUnit(Long.valueOf(500));
        String json = JSON.toJSONString(freightModelDefineRequest);
        byte[] responseString = webClient.post().uri("/shops/1/freightmodels")
                .bodyValue(json)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":802,\"message\":\"运费模板名重复\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    @Test
    public void getFreightModelSummary() throws Exception{
        byte[] responseString = webClient.get().uri("/freightmodels/200")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    @Test
    public void getFreightModelSummary1() throws Exception{
        byte[] responseString = webClient.get().uri("/freightmodels/9")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }

    @Test
    public void getFreightModels() throws Exception{
        byte[] responseString = webClient.get().uri("/shops/1/freightmodels")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }

    @Test
    public void modifyFreightModel() throws Exception{
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("测试名");
        freightModelPutRequest.setUnit(Long.valueOf(500));
        String json = JSON.toJSONString(freightModelPutRequest);

        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/200")
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }

    @Test
    public void modifyFreightModel1() throws Exception{
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("测试模板");
        freightModelPutRequest.setUnit(Long.valueOf(550));
        String json = JSON.toJSONString(freightModelPutRequest);


        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/10")
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":802,\"message\":\"运费模板名重复\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    @Test
    public void modifyFreightModel2() throws Exception{
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("模板修改测试名");
        freightModelPutRequest.setUnit(Long.valueOf(550));
        String json = JSON.toJSONString(freightModelPutRequest);

        byte[] responseString = webClient.put().uri("/shops/1/freightmodels/9")
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );
    }

    @Test
    public void deleteFreightModel() throws Exception{
        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/200")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }

    @Test
    public void deleteFreightModel1() throws Exception{

        byte[] responseString = webClient.delete().uri("/shops/1/freightmodels/10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBodyContent();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, new String(responseString, "UTF-8"), false );

    }
}
