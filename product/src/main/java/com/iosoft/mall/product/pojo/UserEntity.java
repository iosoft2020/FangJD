package com.iosoft.mall.product.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
private String loginName;
private Number id;
private Number bindType;

}
