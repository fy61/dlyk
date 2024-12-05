package com.lyf.query;

import lombok.Data;

@Data
public class TranRemarkQuery extends BaseQuery {

    private Integer tranId;

    private Integer noteWay;

    private String noteContent;
}
