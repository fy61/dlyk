package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranQuery extends Base {

    private Integer customerId;

    private BigDecimal money;

    private Date expectedDate;

    private Integer stage;

    private String description;

    private Date nextContactTime;
}
