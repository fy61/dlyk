package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

@Data
public class TranRemarkQuery extends Base {

    private Integer tranId;

    private Integer noteWay;

    private String noteContent;
}
