package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.RefundPo;
import cn.edu.xmu.oomall.vo.RefundPostRequest;
import cn.edu.xmu.oomall.vo.RefundResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020/11/29
 * modified 2020/12/11
 */
@Data
public class Refund {

    public enum State {
        NEW(0, "新创建"),
        WAITING(1,"等待完成退款"),
        SUCCESS(2, "完成退款"),
        FAILED(3, "退款失败");

        private static final Map<Integer, Refund.State> stateMap;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (Refund.State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Refund.State getStateByCode(Integer code) {
            return stateMap.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;

    private Long paymentId;

    private Long amount;

    private String paySn;

    private Long orderId;

    private Long aftersaleId;

    private State state;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 根据vo 创建返款
     * @param paymentId 返款的支付id
     * @param vo        请求vo
     */
    public Refund(Long paymentId, RefundPostRequest vo) {
        this.paymentId = id;
        this.amount = vo.getAmount();
        this.orderId = null;
        this.aftersaleId = null;
        this.state = State.NEW;
        this.gmtCreated = LocalDateTime.now();
        this.gmtModified = LocalDateTime.now();
    }

    public Refund(RefundPo po) {
        this.id = po.getId();
        this.paymentId = po.getPaymentId();
        this.amount = po.getAmount();
        this.paySn = po.getPaySn();
        this.orderId = po.getOrderId();
        this.aftersaleId = po.getAftersaleId();
        this.state = State.getStateByCode(po.getState());
        this.gmtCreated = po.getGmtCreated();
        this.gmtModified = po.getGmtModified();
    }

    /**
     * 创建 po
     * @return
     */
    public RefundPo createPo() {
        RefundPo po = new RefundPo();
        po.setId(this.id);
        po.setPaymentId(this.paymentId);
        po.setAmount(this.amount);
        po.setPaySn(this.paySn);
        po.setOrderId(this.orderId);
        po.setAftersaleId(this.aftersaleId);
        po.setState(this.state.code);
        po.setGmtCreated(this.gmtCreated);
        po.setGmtModified(this.gmtModified);

        return po;
    }

    /**
     * 创建 vo
     * @return
     */
    public RefundResponse createVo() {
        RefundResponse vo = new RefundResponse();
        vo.setId(this.id);
        vo.setPaymentId(this.paymentId);
        vo.setAmount(this.amount);
        vo.setState(this.state.description);
        vo.setGmtCreateTime(this.gmtCreated.toString());
        vo.setGmtModifiedTime(this.gmtModified.toString());

        return vo;
    }

    /**
     * 返款是否成功
     * @return
     */
    public Boolean isRefundSuccess() {
        return this.state == State.SUCCESS;
    }
}
