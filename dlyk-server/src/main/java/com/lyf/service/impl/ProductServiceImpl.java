package com.lyf.service.impl;

import com.lyf.mapper.TProductMapper;
import com.lyf.model.TProduct;
import com.lyf.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private TProductMapper tProductMapper;
    @Override
    public List<TProduct> getAllOnSaleProduct() {
        return tProductMapper.selectAllOnSaleProduct();
    }
}
