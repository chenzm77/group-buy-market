package com.github.chenzm77.goods.app.service;

import com.github.chenzm77.goods.api.dto.ActivityDTO;
import com.github.chenzm77.goods.api.dto.ActivityItemDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailDTO;
import com.github.chenzm77.goods.api.dto.ProductItemDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchResultDTO;
import com.github.chenzm77.goods.api.dto.SkuDTO;
import com.github.chenzm77.goods.domain.activity.model.Activity;
import com.github.chenzm77.goods.domain.activity.model.ActivityItem;
import com.github.chenzm77.goods.domain.activity.service.ActivityDomainService;
import com.github.chenzm77.goods.domain.index.model.SpuIndexDoc;
import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import com.github.chenzm77.goods.domain.index.service.SpuIndexDomainService;
import com.github.chenzm77.goods.domain.product.model.Sku;
import com.github.chenzm77.goods.domain.product.model.Spu;
import com.github.chenzm77.goods.domain.product.service.ProductDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsApplicationService {

    @Resource
    private ProductDomainService productDomainService;

    @Resource
    private ActivityDomainService activityDomainService;

    @Resource
    private SpuIndexDomainService spuIndexDomainService;

    public ProductDetailDTO queryProductDetail(Long spuId) {
        Spu spu = productDomainService.getProductDetail(spuId);
        Activity activity = activityDomainService.getCurrentActivity(spuId);
        return toDetailDTO(spu, activity);
    }

    public ProductSearchResultDTO searchProducts(ProductSearchRequestDTO request) {
        SpuIndexRepository.SearchResult result = spuIndexDomainService.search(
                request == null ? null : request.getKeyword(),
                request == null ? null : request.getSort(),
                request == null ? null : request.getPage(),
                request == null ? null : request.getActivityOnly());
        return ProductSearchResultDTO.builder()
                .items(toProductItems(result.getItems()))
                .total(result.getTotal())
                .totalPages(result.getTotalPages())
                .currentPage(result.getCurrentPage())
                .build();
    }

    @Transactional
    public void rebuildProductIndex(Long spuId) {
        productDomainService.rebuildProductIndex(spuId);
    }

    private ProductDetailDTO toDetailDTO(Spu spu, Activity activity) {
        return ProductDetailDTO.builder()
                .spuId(spu.getId())
                .spuName(spu.getName())
                .description(spu.getDescription())
                .mainImage(spu.getMainImage())
                .images(spu.getImages())
                .tags(spu.getTags())
                .sales(spu.getSales())
                .status(spu.getStatus())
                .skus(toSkuDTOs(spu.getSkus()))
                .activity(toActivityDTO(activity))
                .build();
    }

    private List<SkuDTO> toSkuDTOs(List<Sku> skus) {
        if (skus == null) {
            return Collections.emptyList();
        }
        return skus.stream()
                .map(sku -> SkuDTO.builder()
                        .skuId(sku.getId())
                        .specJson(sku.getSpecJson())
                        .price(sku.getPrice())
                        .image(sku.getImage())
                        .status(sku.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    private ActivityDTO toActivityDTO(Activity activity) {
        if (activity == null) {
            return null;
        }
        return ActivityDTO.builder()
                .activityId(activity.getId())
                .name(activity.getName())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .groupSize(activity.getGroupSize())
                .status(activity.getStatus())
                .items(toActivityItemDTOs(activity.getItems()))
                .build();
    }

    private List<ActivityItemDTO> toActivityItemDTOs(List<ActivityItem> items) {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(item -> ActivityItemDTO.builder()
                        .activityItemId(item.getId())
                        .skuId(item.getSkuId())
                        .seckillPrice(item.getSeckillPrice())
                        .perUserLimit(item.getPerUserLimit())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ProductItemDTO> toProductItems(List<SpuIndexDoc> docs) {
        if (docs == null) {
            return Collections.emptyList();
        }
        return docs.stream()
                .map(doc -> ProductItemDTO.builder()
                        .spuId(doc.getSpuId())
                        .spuName(doc.getSpuName())
                        .mainImage(doc.getMainImage())
                        .minPrice(doc.getMinPrice())
                        .activityMinPrice(doc.getActivityMinPrice())
                        .sales(doc.getSales())
                        .activityStatus(doc.getActivityStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
