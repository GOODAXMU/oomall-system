package cn.edu.xmu.oomall.constant;


/**
 * 模板类型
 */
public enum ModelType {
    //计重
    WEIGHT_MODEL(0),

    //计件
    PIECE_MODEL(1);

    /**
     * 类型
     */
    private final Integer type;

    public Integer type() {
        return this.type;
    }

    ModelType(Integer type) {
        this.type = type;
    }
}
