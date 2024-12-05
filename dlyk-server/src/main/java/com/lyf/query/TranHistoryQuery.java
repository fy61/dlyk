package com.lyf.query;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TranHistoryQuery extends BaseQuery {

    private Integer tranId;

    private Integer stage;

    private BigDecimal money;

    private Date expectedDate;
}
