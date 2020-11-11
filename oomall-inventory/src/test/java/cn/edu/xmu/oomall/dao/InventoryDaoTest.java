package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallInventoryApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author xincong yao
 * @date 2020-11-11
 */
@SpringBootTest(classes = OomallInventoryApplicationTest.class)
@RunWith(SpringRunner.class)
public class InventoryDaoTest {

	@Autowired
	private InventoryDao inventoryDao;

	@Test
	public void modifyInventoryTest() {
		inventoryDao.modifyInventory(1L, -123);
	}
}
