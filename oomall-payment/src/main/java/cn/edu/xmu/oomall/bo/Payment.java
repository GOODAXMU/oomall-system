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
 * modified 2020/12/10
 */

@Data
public class Payment {

    /**
     * 支付等待时间, 超时则取消
     */
    private static Long waitTime = 30L;

    public enum State {
        NEW(0, "新创建"),
        WAITING(1,"等待完成支付"),
        SUCCESS(2, "完成支付"),
        FAILED(3, "支付失败");

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

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Pattern {
        REBATE(1, "UserRebatePayment"),
        SIMPLE(2, "SimplePayment");

        private static final Map<Integer, Payment.Pattern> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Payment.Pattern enum1 : values()) {
                stateMap.put(enum1.patternId, enum1);
            }
        }

        private int patternId;
        private String patternName;

        Pattern(int patternId, String patternName) {
            this.patternId = patternId;
            this.patternName = patternName;
        }

        public static Payment.Pattern getById(Integer patternId) {
            return stateMap.get(patternId);
        }

        public Integer getPatternId() {
            return patternId;
        }

        public String getPatternName() {
            return patternName;
        }
    }

    private Long id;

    private Long afterSaleId;

    private Long amount;

    private Long actualAmount;

    private Pattern paymentPattern;

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
        this.paymentPattern = Pattern.valueOf(request.getPattern());
        this.beginTime = LocalDateTime.now();
        this.endTime = this.beginTime.plusMinutes(30);
        this.state = State.NEW;
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
        this.paymentPattern = Pattern.getById(po.getPaymentPattern());
        this.payTime = po.getPayTime();
        this.paySn = po.getPaySn();
        this.beginTime = po.getBeginTime();
        this.endTime = po.getEndTime();
        this.orderId = po.getOrderId();
        this.state = Payment.State.getStateByCode(po.getState());
        this.gmtCreated = po.getGmtCreated();
        this.gmtModified = po.getGmtModified();
    }

    /**
     * 创建 vo
     * @return
     */
    public PaymentResponse createVo() {
        PaymentResponse vo = new PaymentResponse();
        vo.setId((this.id));
        vo.setOrderId(this.orderId);
        vo.setAmount(this.amount);
        vo.setActualAmount(this.actualAmount);
        vo.setPayTime(this.payTime.toString());
        vo.setPayPattern(this.paymentPattern.toString());
        vo.setBeginTime(this.beginTime.toString());
        vo.setEndTime(this.endTime.toString());
        vo.setState(this.state.getDescription());
        vo.setGmtCreateTime(this.gmtCreated.toString());
        vo.setGmtModifiedTime(this.gmtModified.toString());

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
        po.setPaymentPattern(this.paymentPattern.getPatternId());
        po.setPayTime(this.payTime);
        po.setPaySn(this.paySn);
        po.setBeginTime(this.beginTime);
        po.setEndTime(this.endTime);
        po.setOrderId(this.orderId);
        po.setState(this.state.getCode());
        po.setGmtCreated(this.gmtCreated);
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
}
