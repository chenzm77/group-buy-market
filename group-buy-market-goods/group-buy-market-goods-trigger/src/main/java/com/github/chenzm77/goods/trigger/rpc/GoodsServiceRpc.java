package com.github.chenzm77.goods.trigger.rpc;

import com.github.chenzm77.goods.api.IGoodsService;
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

    @Override
    public Response<Void> createSpu(SpuCommandDTO request) {
        return execute(() -> goodsApplicationService.createSpu(request));
    }

    @Override
    public Response<Void> updateSpu(SpuCommandDTO request) {
        return execute(() -> goodsApplicationService.updateSpu(request));
    }

    @Override
    public Response<Void> deleteSpu(IdCommandDTO request) {
        return execute(() -> goodsApplicationService.deleteSpu(request.getId()));
    }

    @Override
    public Response<Void> createSku(SkuCommandDTO request) {
        return execute(() -> goodsApplicationService.createSku(request));
    }

    @Override
    public Response<Void> updateSku(SkuCommandDTO request) {
        return execute(() -> goodsApplicationService.updateSku(request));
    }

    @Override
    public Response<Void> deleteSku(IdCommandDTO request) {
        return execute(() -> goodsApplicationService.deleteSku(request.getId(), request.getSpuId()));
    }

    @Override
    public Response<Void> createActivity(ActivityCommandDTO request) {
        return execute(() -> goodsApplicationService.createActivity(request));
    }

    @Override
    public Response<Void> updateActivity(ActivityCommandDTO request) {
        return execute(() -> goodsApplicationService.updateActivity(request));
    }

    @Override
    public Response<Void> deleteActivity(IdCommandDTO request) {
        return execute(() -> goodsApplicationService.deleteActivity(request.getId(), request.getSpuId()));
    }

    @Override
    public Response<Void> createActivityItem(ActivityItemCommandDTO request) {
        return execute(() -> goodsApplicationService.createActivityItem(request));
    }

    @Override
    public Response<Void> updateActivityItem(ActivityItemCommandDTO request) {
        return execute(() -> goodsApplicationService.updateActivityItem(request));
    }

    @Override
    public Response<Void> deleteActivityItem(IdCommandDTO request) {
        return execute(() -> goodsApplicationService.deleteActivityItem(request.getId(), request.getSpuId()));
    }

    private Response<Void> execute(Command command) {
        try {
            command.execute();
            return Response.success(null);
        } catch (AppException e) {
            return Response.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.fail(ResponseCode.SYSTEM_ERROR.getCode(), ResponseCode.SYSTEM_ERROR.getInfo());
        }
    }

    private interface Command {
        void execute();
    }
}
