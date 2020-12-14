package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.external.service.IShopService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.Reply;
import cn.edu.xmu.oomall.vo.ShopOrderDeliverPutRequest;
import cn.edu.xmu.oomall.vo.ShopOrderMessageAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-27
 * @modified 2020-12-12
 */
@Service
public class ShopOrderService {

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private OrderDao orderDao;

    private ICustomerService customerService;
    private IShopService shopService;

    @PostConstruct
    public void init() {
        customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
        shopService = (IShopService) serviceFactory.get(IShopService.class);
    }

    public Reply<List<Order>> getShopOrders(Long shopId, Long customerId,
                                            String orderSn,
                                            LocalDateTime beginTime,
                                            LocalDateTime endTime,
                                            PageInfo pageInfo, Boolean withParent) {
        return orderDao.getShopOrders(shopId, customerId, orderSn, beginTime, endTime, pageInfo, withParent);
    }

    public Reply<Object> addShopOrderMessage(Long shopId, Long id, String message) {
        return orderDao.addShopOrderMessage(shopId, id, message);
    }


    public Reply<Order> getShopOrderById(Long shopId, Long id) {
        Reply<Order> r = orderDao.getShopOrderById(shopId, id);
        Order o = r.getData();
        if (o == null) {
            return r;
        }

        // 设置用户和商铺
        Customer customer = customerService.getCustomer(o.getCustomer().getId());
        Shop shop = shopService.getShop(o.getShop().getId());
        o.setCustomer(customer);
        o.setShop(shop);

        return new Reply<>(o);
    }

    public Reply<Object> cancelShopOrder(Long shopId, Long id) {
        return orderDao.cancelShopOrder(shopId, id);
    }

    public Reply<Object> markShopOrderDelivered(Long shopId, Long id, String shipmentSn) {
        return orderDao.markShopOrderDelivered(shopId, id, shipmentSn);
    }

}
