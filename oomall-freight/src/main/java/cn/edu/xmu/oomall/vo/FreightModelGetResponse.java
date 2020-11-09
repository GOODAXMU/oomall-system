package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer total;
    private Integer pages;
    List<FreightModelInner> lists;
}
