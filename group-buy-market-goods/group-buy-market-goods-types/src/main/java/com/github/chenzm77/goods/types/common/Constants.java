package com.github.chenzm77.goods.types.common;

public class Constants {

    public static class RedisKey {
        public static final String SPU_PREFIX = "goods:spu:";
        public static final String SKU_PREFIX = "goods:sku:";
        public static final String SPU_SALES_PREFIX = "goods:spu:sales:";
    }

    public static class EsIndex {
        public static final String SPU_INDEX = "spu_index";
    }

    public static class MqTopic {
        public static final String SPU_INDEX_REBUILD = "goods.spu.index.rebuild";
        public static final String GOODS_CACHE_INVALIDATE = "goods.cache.invalidate";
    }
}
