package com.bjpowernode.domain.query;

import com.bjpowernode.domain.Base;
import lombok.Data;

@Data
public class CustomerRemarkQuery extends Base {

    private Integer customerId;

    private Integer noteWay;

    private String noteContent;
}
