package com.lyf.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.lyf.DlykServerApplication;
import com.lyf.model.TDicValue;
import com.lyf.result.DicEnum;

import java.util.List;

/**
 * 是否贷款的转换器
 *
 *  * Excel中的 “需要”  ----> Java类中是 49
 *  * Excel中的 “不需要”  ----> Java类中是 50
 */
public class NeedLoanConverter implements Converter<Integer> {
    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的 “需要”  ----> Java类中是 49
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    //此类不能加入到Bean中,需要将数据库的数据加载到内存中,从内存中读取到数据
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //cellData是Excel中读取到的数据，是“先生”、“女士”
        String cellNeedName = cellData.getStringValue();

        List<TDicValue> tDicValueList = (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.NEEDLOAN.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id  = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if (cellNeedName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
