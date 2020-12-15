package cn.edu.xmu.oomall.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhibin lan
 * @date 2020-12-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelDto implements Serializable {
    private Long id;

    private String name;

    private Integer type;

    private Long unit;

    private Boolean defaultModel;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
