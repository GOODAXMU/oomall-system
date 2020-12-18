package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.vo.PaymentPostRequest;
import cn.edu.xmu.oomall.vo.PaymentResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020/11/24
 * modified 2020/12/18
 */

@Data
public class Payment {

    /**
     * 支付等待时间, 超时则取消
     */
    private static Long waitTime = 30L;

    public enum State {
        WAITING(0,"未支付"),
        SUCCESS(1, "已支付"),
        FAILED(2, "支付失败");

        private static final Map<Integer, Payment.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Payment.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Payment.State getStateByCode(Integer code) {
            return stateMap.get(code);
        }

        public static Integer getCodeByState(State state) {
            if (null == state) {
                return null;
            }

            return state.code;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;

    private Long afterSaleId;

    private Long amount;

    private Long actualAmount;

    public String pattern;

    private LocalDateTime payTime;

    private String paySn;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Long orderId;

    private State state;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 根据对应的订单id 以及应支付金额创建新的 payment
     * @param orderId       订单id
     * @param afterSaleId   售后id
     * @param request
     */
    public Payment(Long orderId, Long afterSaleId, PaymentPostRequest request){
        this.orderId = orderId;
        this.afterSaleId = afterSaleId;
        this.amount = request.getPrice();
        this.actualAmount = request.getPrice();
        this.pattern = request.getPaymentPattern();
        this.beginTime = LocalDateTime.now().withNano(0);
        this.endTime = this.beginTime.plusMinutes(30).withNano(0);
        this.state = State.WAITING;
        this.gmtCreated = LocalDateTime.now();
        this.gmtModified = LocalDateTime.now();
    }

    /**
     * 根据po 对象创建 payment
     * @param po
     */
    public Payment(PaymentPo po){
        this.id = po.getId();
        this.afterSaleId = po.getAftersaleId();
        this.amount = po.getAmount();
        this.actualAmount = po.getActualAmount();
        this.pattern = po.getPaymentPattern();
        this.payTime = po.getPayTime();
        this.paySn = po.getPaySn();
        this.beginTime = po.getBeginTime();
        this.endTime = po.getEndTime();
        this.orderId = po.getOrderId();
        this.state = Payment.State.getStateByCode(po.getState());
        this.gmtCreated = po.getGmtCreate();
        this.gmtModified = po.getGmtModified();
    }

    /**
     * 为返款创建反向支付信息
     * @param pattern
     * @param amount
     */
    public Payment(String pattern, Long amount, Long orderId, Long afterSaleId) {
        this.orderId = orderId;
        this.afterSaleId = afterSaleId;
        this.pattern = pattern;
        this.amount = amount;
        this.actualAmount = amount;
        this.state = State.WAITING;
        // 反向支付的开始支付与结束支付时间无意义
    }

    /**
     * 创建 vo
     * @return
     */
    public PaymentResponse createVo() {
        PaymentResponse vo = new PaymentResponse();
        vo.setId((this.id));
        vo.setOrderId(this.orderId);
        vo.setAftersaleId(this.afterSaleId);
        vo.setAmount(this.amount);
        vo.setActualAmount(this.actualAmount);
        vo.setPayTime(String.valueOf(this.payTime));
        vo.setPaymentPattern(this.pattern);
        vo.setBeginTime(String.valueOf(this.beginTime));
        vo.setEndTime(String.valueOf(this.endTime));
        vo.setState(State.getCodeByState(this.state));
        vo.setGmtCreate(String.valueOf(this.gmtCreated));
        vo.setGmtModified(String.valueOf(this.gmtModified));

        return vo;
    }

    /**
     * 创建 po
     * @return
     */
    public PaymentPo createPo() {
        PaymentPo po = new PaymentPo();
        po.setId(this.id);
        po.setAftersaleId(this.afterSaleId);
        po.setAmount(this.amount);
        po.setActualAmount(this.actualAmount);
        po.setPaymentPattern(this.pattern);
        po.setPayTime(this.payTime);
        po.setPaySn(this.paySn);
        po.setBeginTime(this.beginTime);
        po.setEndTime(this.endTime);
        po.setOrderId(this.orderId);
        po.setState(State.getCodeByState(this.state));
        po.setGmtCreate(this.gmtCreated);
        po.setGmtModified(this.gmtModified);

        return po;
    }

    /**
     * 判断支付是否成功
     * @return
     */
    public Boolean isPaySuccess() {
        return this.state == State.SUCCESS;
    }

    /**
     * 设置现在时间为支付时间
     */
    public void setPayTime() {
        this.payTime = LocalDateTime.now().withNano(0);
    }
}
