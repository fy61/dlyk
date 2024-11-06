package com.lyf.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.lyf.DlykServerApplication;
import com.lyf.model.TDicValue;
import com.lyf.model.TProduct;
import com.lyf.result.DicEnum;

import java.util.List;
/**
 * 意向产品的转换器
 *
 * Excel中的比亚迪e2   -->  java中的 2
 * 秦PLUS EV  -->  7
 */
public class IntentionProductConverter implements Converter<Integer> {
    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的 “比亚迪e2”  ----> Java类中是 2
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    //此类不能加入到Bean中,需要将数据库的数据加载到内存中,从内存中读取到数据
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //cellData是Excel中读取到的数据，是“比亚迪e2”、“秦PLUS EV”
        String cellIntentionProductName = cellData.getStringValue();

        List<TProduct> tDicValueList = (List<TProduct>) DlykServerApplication.cacheMap.get(DicEnum.PRODUCT.getCode());
        for (TProduct tProduct : tDicValueList) {
            Integer id  = tProduct.getId();
            String name = tProduct.getName();

            if (cellIntentionProductName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
