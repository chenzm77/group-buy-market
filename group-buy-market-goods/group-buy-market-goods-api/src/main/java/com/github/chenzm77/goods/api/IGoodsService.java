package com.github.chenzm77.goods.api;

import com.github.chenzm77.goods.api.dto.ProductDetailDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchResultDTO;
import com.github.chenzm77.goods.api.response.Response;

/**
 *
 */
public interface IGoodsService {

    Response<ProductDetailDTO> queryProductDetail(ProductDetailRequestDTO request);

    Response<ProductSearchResultDTO> searchProducts(ProductSearchRequestDTO request);
}
