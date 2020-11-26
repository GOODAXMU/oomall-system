package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "订单收货信息修改的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPutRequest {
    private String consignee;
    private Long regionId;
    private String address;
    private String mobile;
}
