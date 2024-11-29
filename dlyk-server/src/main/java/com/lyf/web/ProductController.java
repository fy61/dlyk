package com.lyf.web;

import com.lyf.model.TProduct;
import com.lyf.result.R;
import com.lyf.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 查询在售的产品
     *
     * @return
     */
    @GetMapping(value = "/api/product/sale")
    public R saleProduct() {
        List<TProduct> tProductList = productService.getAllOnSaleProduct();
        return R.OK(tProductList);
    }
}
