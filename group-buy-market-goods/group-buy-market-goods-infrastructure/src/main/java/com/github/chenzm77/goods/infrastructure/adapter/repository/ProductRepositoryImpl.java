package com.github.chenzm77.goods.infrastructure.adapter.repository;

import com.alibaba.fastjson.JSON;
import com.github.chenzm77.goods.domain.product.model.Sku;
import com.github.chenzm77.goods.domain.product.model.Spu;
import com.github.chenzm77.goods.domain.product.repository.ProductRepository;
import com.github.chenzm77.goods.infrastructure.dao.SkuMapper;
import com.github.chenzm77.goods.infrastructure.dao.SpuMapper;
import com.github.chenzm77.goods.infrastructure.dao.po.SkuPO;
import com.github.chenzm77.goods.infrastructure.dao.po.SpuPO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    public Spu findById(Long spuId) {
        SpuPO spuPO = spuMapper.selectById(spuId);
        if (spuPO == null) {
            return null;
        }
        List<SkuPO> skuPOS = skuMapper.selectBySpuId(spuId);
        return toEntity(spuPO, skuPOS);
    }

    @Override
    public void save(Spu spu) {
        spuMapper.insert(toPO(spu));
        if (spu.getSkus() != null) {
            spu.getSkus().forEach(sku -> skuMapper.insert(toPO(sku)));
        }
    }

    @Override
    public void update(Spu spu) {
        spuMapper.update(toPO(spu));
    }

    @Override
    public void delete(Long spuId) {
        spuMapper.deleteById(spuId);
    }

    private Spu toEntity(SpuPO po, List<SkuPO> skuPOS) {
        return Spu.builder()
                .id(po.getId())
                .name(po.getName())
                .description(po.getDescription())
                .mainImage(po.getMainImage())
                .images(parseList(po.getImages()))
                .tags(parseList(po.getTags()))
                .sales(po.getSales())
                .status(po.getStatus())
                .createTime(po.getCreateTime())
                .updateTime(po.getUpdateTime())
                .skus(skuPOS == null ? Collections.emptyList() : skuPOS.stream().map(this::toEntity).collect(Collectors.toList()))
                .build();
    }

    private Sku toEntity(SkuPO po) {
        return Sku.builder()
                .id(po.getId())
                .spuId(po.getSpuId())
                .specJson(po.getSpecJson())
                .price(po.getPrice())
                .image(po.getImage())
                .status(po.getStatus())
                .build();
    }

    private SpuPO toPO(Spu spu) {
        SpuPO po = new SpuPO();
        po.setId(spu.getId());
        po.setName(spu.getName());
        po.setDescription(spu.getDescription());
        po.setMainImage(spu.getMainImage());
        po.setImages(JSON.toJSONString(spu.getImages()));
        po.setTags(JSON.toJSONString(spu.getTags()));
        po.setSales(spu.getSales());
        po.setStatus(spu.getStatus());
        po.setCreateTime(spu.getCreateTime());
        po.setUpdateTime(spu.getUpdateTime());
        return po;
    }

    private SkuPO toPO(Sku sku) {
        SkuPO po = new SkuPO();
        po.setId(sku.getId());
        po.setSpuId(sku.getSpuId());
        po.setSpecJson(sku.getSpecJson());
        po.setPrice(sku.getPrice());
        po.setImage(sku.getImage());
        po.setStatus(sku.getStatus());
        return po;
    }

    private List<String> parseList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return JSON.parseArray(json, String.class);
    }
}
