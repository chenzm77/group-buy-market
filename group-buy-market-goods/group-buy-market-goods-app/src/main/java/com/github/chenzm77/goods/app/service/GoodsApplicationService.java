package com.github.chenzm77.goods.app.service;

import com.github.chenzm77.goods.api.dto.ActivityCommandDTO;
import com.github.chenzm77.goods.api.dto.ActivityDTO;
import com.github.chenzm77.goods.api.dto.ActivityItemCommandDTO;
import com.github.chenzm77.goods.api.dto.ActivityItemDTO;
import com.github.chenzm77.goods.api.dto.ProductDetailDTO;
import com.github.chenzm77.goods.api.dto.ProductItemDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchRequestDTO;
import com.github.chenzm77.goods.api.dto.ProductSearchResultDTO;
import com.github.chenzm77.goods.api.dto.SkuCommandDTO;
import com.github.chenzm77.goods.api.dto.SkuDTO;
import com.github.chenzm77.goods.api.dto.SpuCommandDTO;
import com.github.chenzm77.goods.domain.activity.model.aggregate.Activity;
import com.github.chenzm77.goods.domain.activity.model.entity.ActivityItem;
import com.github.chenzm77.goods.domain.activity.service.ActivityDomainService;
import com.github.chenzm77.goods.domain.event.DomainEventPublisher;
import com.github.chenzm77.goods.domain.event.SpuIndexRebuildEvent;
import com.github.chenzm77.goods.domain.index.model.aggregate.SpuIndexDoc;
import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import com.github.chenzm77.goods.domain.index.service.SpuIndexDomainService;
import com.github.chenzm77.goods.domain.product.model.aggregate.Spu;
import com.github.chenzm77.goods.domain.product.model.entity.Sku;
import com.github.chenzm77.goods.domain.product.service.ProductDomainService;
import org.apache.dubbo.rpc.RpcContext;
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

    @Resource
    private DomainEventPublisher domainEventPublisher;

    public ProductDetailDTO queryProductDetail(Long spuId) {

        Spu spu = productDomainService.getProductDetail(spuId);
        Activity activity = activityDomainService.getCurrentActivity(spuId);
        return toDetailDTO(spu, activity);
    }

    public ProductSearchResultDTO searchProducts(ProductSearchRequestDTO request) {
        SpuIndexRepository.SearchResult result = spuIndexDomainService.search(
                request == null ? null : request.getKeyword(),
                request == null ? null : request.getQueryType(),
                request == null ? null : request.getPage());
        return ProductSearchResultDTO.builder()
                .items(toProductItems(result.getItems()))
                .total(result.getTotal())
                .totalPages(result.getTotalPages())
                .currentPage(result.getCurrentPage())
                .build();
    }

    @Transactional
    public void createSpu(SpuCommandDTO request) {
        productDomainService.createSpu(toSpu(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "SPU_CREATE");
    }

    @Transactional
    public void updateSpu(SpuCommandDTO request) {
        productDomainService.updateSpu(toSpu(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "SPU_UPDATE");
    }

    @Transactional
    public void deleteSpu(Long spuId) {
        productDomainService.deleteSpu(spuId);
        publishSpuIndexRebuildEvent(spuId, "SPU_DELETE");
    }

    @Transactional
    public void createSku(SkuCommandDTO request) {
        productDomainService.createSku(toSku(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "SKU_CREATE");
    }

    @Transactional
    public void updateSku(SkuCommandDTO request) {
        productDomainService.updateSku(toSku(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "SKU_UPDATE");
    }

    @Transactional
    public void deleteSku(Long skuId, Long spuId) {
        productDomainService.deleteSku(skuId);
        publishSpuIndexRebuildEvent(spuId, "SKU_DELETE");
    }

    @Transactional
    public void createActivity(ActivityCommandDTO request) {
        activityDomainService.createActivity(toActivity(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "ACTIVITY_CREATE");
    }

    @Transactional
    public void updateActivity(ActivityCommandDTO request) {
        activityDomainService.updateActivity(toActivity(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "ACTIVITY_UPDATE");
    }

    @Transactional
    public void deleteActivity(Long activityId, Long spuId) {
        activityDomainService.deleteActivity(activityId);
        publishSpuIndexRebuildEvent(spuId, "ACTIVITY_DELETE");
    }

    @Transactional
    public void createActivityItem(ActivityItemCommandDTO request) {
        activityDomainService.createActivityItem(toActivityItem(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "ACTIVITY_ITEM_CREATE");
    }

    @Transactional
    public void updateActivityItem(ActivityItemCommandDTO request) {
        activityDomainService.updateActivityItem(toActivityItem(request));
        publishSpuIndexRebuildEvent(request.getSpuId(), "ACTIVITY_ITEM_UPDATE");
    }

    @Transactional
    public void deleteActivityItem(Long activityItemId, Long spuId) {
        activityDomainService.deleteActivityItem(activityItemId);
        publishSpuIndexRebuildEvent(spuId, "ACTIVITY_ITEM_DELETE");
    }

    @Transactional
    public void rebuildProductIndex(Long spuId) {
        productDomainService.rebuildProductIndex(spuId);
    }

    private void publishSpuIndexRebuildEvent(Long spuId, String reason) {
        domainEventPublisher.publish(new SpuIndexRebuildEvent(spuId, reason));
    }

    private Spu toSpu(SpuCommandDTO request) {
        return Spu.builder()
                .id(request.getSpuId())
                .name(request.getSpuName())
                .description(request.getDescription())
                .mainImage(request.getMainImage())
                .images(request.getImages())
                .tags(request.getTags())
                .sales(request.getSales())
                .status(request.getStatus())
                .build();
    }

    private Sku toSku(SkuCommandDTO request) {
        return Sku.builder()
                .id(request.getSkuId())
                .spuId(request.getSpuId())
                .specJson(request.getSpecJson())
                .price(request.getPrice())
                .image(request.getImage())
                .status(request.getStatus())
                .build();
    }

    private Activity toActivity(ActivityCommandDTO request) {
        return Activity.builder()
                .id(request.getActivityId())
                .spuId(request.getSpuId())
                .name(request.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .groupSize(request.getGroupSize())
                .status(request.getStatus())
                .items(toActivityItems(request.getItems()))
                .build();
    }

    private List<ActivityItem> toActivityItems(List<ActivityItemCommandDTO> items) {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream().map(this::toActivityItem).collect(Collectors.toList());
    }

    private ActivityItem toActivityItem(ActivityItemCommandDTO request) {
        return ActivityItem.builder()
                .id(request.getActivityItemId())
                .activityId(request.getActivityId())
                .skuId(request.getSkuId())
                .seckillPrice(request.getSeckillPrice())
                .perUserLimit(request.getPerUserLimit())
                .build();
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
                        .sortPrice(doc.getSortPrice())
                        .sales(doc.getSales())
                        .activityStatus(doc.getActivityStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
