package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.util.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yan song
 * @date 2020-11-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelGetResponse {
    private Integer page;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    List<FreightModelInner> list;

    public FreightModelGetResponse(PageInfo pageInfo, List<FreightModel> freightModels) {
        page = pageInfo.getPage();
        pageSize = pageInfo.getPageSize();
        total = pageInfo.getTotal();
        pages = pageInfo.getPages();
        list = new ArrayList<FreightModelInner>();
        for (FreightModel freightModel : freightModels) {
            FreightModelInner freightModelInner = new FreightModelInner(
                    freightModel.getId(), freightModel.getName(),
                    freightModel.getType(), freightModel.getUnit(),
                    freightModel.getIsDefault() == null ? false : freightModel.getIsDefault() == 1,
                    freightModel.getGmtCreate() == null ? null : freightModel.getGmtCreate().toString(),
                    freightModel.getGmtModified() == null ? null : freightModel.getGmtModified().toString());
            list.add(freightModelInner);
        }
    }
}
