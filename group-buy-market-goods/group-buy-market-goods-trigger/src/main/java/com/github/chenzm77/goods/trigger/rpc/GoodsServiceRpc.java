package com.github.chenzm77.goods.trigger.rpc;

import com.github.chenzm77.goods.api.IGoodsService;
import com.github.chenzm77.goods.api.dto.ProductDetailDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchResultDTO;
import com.github.chenzm77.goods.api.response.Response;
import com.github.chenzm77.goods.app.service.GoodsApplicationService;
import com.github.chenzm77.goods.types.enums.ResponseCode;
import com.github.chenzm77.goods.types.exception.AppException;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService(version = "1.0.0")
public class GoodsServiceRpc implements IGoodsService {

    @Resource
    private GoodsApplicationService goodsApplicationService;

    @Override
    public Response<ProductDetailDTO> queryProductDetail(ProductDetailRequestDTO request) {
        try {
            return Response.success(goodsApplicationService.queryProductDetail(request.getSpuId()));
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.fail(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getInfo());
        }
    }

    @Override
    public Response<ProductSearchResultDTO> searchProducts(ProductSearchRequestDTO request) {
        try {
            return Response.success(goodsApplicationService.searchProducts(request));
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.fail(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getInfo());
        }
    }
}