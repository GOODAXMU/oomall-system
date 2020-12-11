package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yan song
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelInner {
    private Long id;
    private String name;
    private Integer type;
    private Boolean defaultModel;
    private String gmtCreate;
    private String gmtModified;
}
