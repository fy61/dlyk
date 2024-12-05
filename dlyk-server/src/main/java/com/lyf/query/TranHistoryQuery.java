package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranHistoryQuery extends Base {

    private Integer tranId;

    private Integer stage;

    private BigDecimal money;

    private Date expectedDate;
}
