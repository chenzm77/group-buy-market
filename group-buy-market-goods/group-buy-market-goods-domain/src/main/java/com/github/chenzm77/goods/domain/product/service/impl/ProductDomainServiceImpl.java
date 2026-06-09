package com.github.chenzm77.goods.domain.product.service.impl;

import com.github.chenzm77.goods.domain.activity.model.Activity;
import com.github.chenzm77.goods.domain.activity.model.ActivityItem;
import com.github.chenzm77.goods.domain.activity.repository.ActivityRepository;
import com.github.chenzm77.goods.domain.index.model.SpuIndexDoc;
import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import com.github.chenzm77.goods.domain.product.model.Sku;
import com.github.chenzm77.goods.domain.product.model.Spu;
import com.github.chenzm77.goods.domain.product.repository.ProductRepository;
import com.github.chenzm77.goods.domain.product.service.ProductDomainService;
import com.github.chenzm77.goods.types.enums.ResponseCode;
import com.github.chenzm77.goods.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductDomainServiceImpl implements ProductDomainService {

    @Resource
    private ProductRepository productRepository;

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private SpuIndexRepository spuIndexRepository;

    @Override
    public Spu getProductDetail(Long spuId) {
        Spu spu = productRepository.findById(spuId);
        if (spu == null) {
            throw new AppException(ResponseCode.SPU_NOT_FOUND.getCode(), ResponseCode.SPU_NOT_FOUND.getInfo());
        }
        return spu;
    }

    @Override
    public void rebuildProductIndex(Long spuId) {
        Spu spu = productRepository.findById(spuId);
        if (spu == null || !spu.isSearchable()) {
            spuIndexRepository.delete(spuId);
            return;
        }
        Activity activity = activityRepository.findCurrentBySpuId(spuId);
        SpuIndexDoc doc = SpuIndexDoc.builder()
                .spuId(spu.getId())
                .spuName(spu.getName())
                .tags(spu.getTags())
                .mainImage(spu.getMainImage())
                .minPrice(calculateMinPrice(spu.getSkus()))
                .activityMinPrice(calculateActivityMinPrice(activity))
                .sales(spu.getSales())
                .activityStatus(activity != null && activity.isEffectiveNow() ? 1 : 0)
                .createTime(spu.getCreateTime())
                .build();
        spuIndexRepository.save(doc);
    }

    private Long calculateMinPrice(List<Sku> skus) {
        if (skus == null || skus.isEmpty()) {
            return null;
        }
        return skus.stream()
                .filter(Sku::isEnabled)
                .map(Sku::getPrice)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Long calculateActivityMinPrice(Activity activity) {
        if (activity == null || !activity.isEffectiveNow() || activity.getItems() == null || activity.getItems().isEmpty()) {
            return null;
        }
        return activity.getItems().stream()
                .map(ActivityItem::getSeckillPrice)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }
}
