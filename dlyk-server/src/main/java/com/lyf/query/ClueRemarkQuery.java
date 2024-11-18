package com.lyf.query;

import lombok.Data;

@Data
public class ClueRemarkQuery extends BaseQuery {

    private Integer clueId;

    private String noteContent;

    private Integer noteWay;
}
