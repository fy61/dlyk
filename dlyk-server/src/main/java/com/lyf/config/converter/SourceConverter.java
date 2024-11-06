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
 * 线索来源的转换器
 *
 *  * Excel中的 “车展会”  ----> Java类中是 3
 *  * Excel中的 “网络广告”  ----> Java类中是 16
 */
public class SourceConverter implements Converter<Integer> {

    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的 “车展会”  ----> Java类中是 3
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    //此类不能加入到Bean中,需要将数据库的数据加载到内存中,从内存中读取到数据
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        //cellData是Excel中读取到的数据，是“车展会”、“车展会”
        String cellSourceName = cellData.getStringValue();

        List<TDicValue> tDicValueList = (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.SOURCE.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id  = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if (cellSourceName.equals(name)) {
                return id;
            }
        }
        return -1;
    }
}
