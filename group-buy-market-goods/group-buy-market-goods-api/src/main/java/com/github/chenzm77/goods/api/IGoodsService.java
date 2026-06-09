package com.github.chenzm77.goods.api;

import com.github.chenzm77.goods.api.dto.ActivityCommandDTO;
import com.github.chenzm77.goods.api.dto.ActivityItemCommandDTO;
import com.github.chenzm77.goods.api.dto.IdCommandDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchResultDTO;
import com.github.chenzm77.goods.api.dto.SkuCommandDTO;
import com.github.chenzm77.goods.api.dto.SpuCommandDTO;
import com.github.chenzm77.goods.api.response.Response;

public interface IGoodsService {

    Response<ProductDetailDTO> queryProductDetail(ProductDetailRequestDTO request);

    Response<ProductSearchResultDTO> searchProducts(ProductSearchRequestDTO request);

    Response<Void> createSpu(SpuCommandDTO request);

    Response<Void> updateSpu(SpuCommandDTO request);

    Response<Void> deleteSpu(IdCommandDTO request);

    Response<Void> createSku(SkuCommandDTO request);

    Response<Void> updateSku(SkuCommandDTO request);

    Response<Void> deleteSku(IdCommandDTO request);

    Response<Void> createActivity(ActivityCommandDTO request);

    Response<Void> updateActivity(ActivityCommandDTO request);

    Response<Void> deleteActivity(IdCommandDTO request);

    Response<Void> createActivityItem(ActivityItemCommandDTO request);

    Response<Void> updateActivityItem(ActivityItemCommandDTO request);

    Response<Void> deleteActivityItem(IdCommandDTO request);
}
