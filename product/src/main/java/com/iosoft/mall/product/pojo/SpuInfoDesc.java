package com.iosoft.mall.product.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * spu信息介绍
 * </p>
 *
 * @author iosoft
 * @since 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_info_desc")
public class SpuInfoDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "spu_id", type = IdType.AUTO)
    private Long spuId;

    /**
     * 商品介绍
     */
    private String decript;


}
