package cn.edu.xmu.oomall.entity;

import cn.edu.xmu.oomall.bo.WeightFreightModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jianheng huang
 * @date 2020-11-7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weight_freight_model")
public class WeightFreightModelPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long freightModelId;

	private Long firstWeight;

	private Long firstWeightFreight;

	private Long tenPrice;

	private Long fiftyPrice;

	private Long hundredPrice;

	private Long trihunPrice;

	private Long abovePrice;

	private Long regionId;

	private String gmtCreate;

	private String gmtModified;

	public WeightFreightModelPo(WeightFreightModel weightFreightModel){
		id=weightFreightModel.getId();
		freightModelId=weightFreightModel.getFreightModelId();
		firstWeight=weightFreightModel.getFirstWeight();
		firstWeightFreight=weightFreightModel.getFirstWeightFreight();
		tenPrice=weightFreightModel.getTenPrice();
		fiftyPrice=weightFreightModel.getFiftyPrice();
		hundredPrice=weightFreightModel.getHundredPrice();
		trihunPrice=weightFreightModel.getTrihunPrice();
		abovePrice=weightFreightModel.getAbovePrice();
		regionId=weightFreightModel.getRid();
		gmtModified=LocalDateTime.now().toString();
	}

}
