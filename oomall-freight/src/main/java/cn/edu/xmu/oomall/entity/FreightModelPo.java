package cn.edu.xmu.oomall.entity;

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
@Table(name = "freight_model")
public class FreightModelPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;

    private String name;

    private Integer defaultModel;

    private Integer type;

    @Column(name = "unit")
    private Long weightUnit;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;
}
