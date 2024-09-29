package com.example.ShanruanShopping.entity.vo;

import com.example.ShanruanShopping.entity.CommodityEvaluation;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommodityEvaluationInfo extends CommodityEvaluation {
    private String name;    //userName
}
