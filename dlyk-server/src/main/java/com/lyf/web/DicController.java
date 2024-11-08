package com.lyf.web;

import com.lyf.DlykServerApplication;
import com.lyf.model.TActivity;
import com.lyf.model.TDicType;
import com.lyf.model.TProduct;
import com.lyf.result.DicEnum;
import com.lyf.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicController {

    @GetMapping("/api/dicvalue/{typeCode}")
    public R dicData(@PathVariable(value = "typeCode") String typeCode){
        if(DlykServerApplication.cacheMap.get(typeCode).equals(DicEnum.ACTIVITY.getCode())){
            List<TActivity> tActivityList = (List<TActivity>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        }else if(DlykServerApplication.cacheMap.get(typeCode).equals(DicEnum.PRODUCT.getCode())){
            List<TProduct> tProductList = (List<TProduct>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tProductList);
        }else {
            List<TActivity> tActivityList = (List<TActivity>) DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        }

    }
}
