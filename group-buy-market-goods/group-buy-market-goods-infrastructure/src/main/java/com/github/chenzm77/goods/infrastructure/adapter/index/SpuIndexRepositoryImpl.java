package com.github.chenzm77.goods.infrastructure.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.github.chenzm77.goods.domain.index.model.aggregate.SpuIndexDoc;
import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import com.github.chenzm77.goods.types.common.Constants;
import com.github.chenzm77.goods.types.enums.ProductQueryType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SpuIndexRepositoryImpl implements SpuIndexRepository {

    private static final int PAGE_SIZE = 20;

    @Resource
    private ElasticsearchClient goodsElasticsearchClient;

    @Override
    public void save(SpuIndexDoc doc) {
        try {
            goodsElasticsearchClient.index(request -> request
                    .index(Constants.EsIndex.SPU_INDEX)
                    .id(String.valueOf(doc.getSpuId()))
                    .document(doc));
        } catch (IOException e) {
            throw new RuntimeException("同步SPU索引失败", e);
        }
    }

    @Override
    public void delete(Long spuId) {
        try {
            goodsElasticsearchClient.delete(request -> request
                    .index(Constants.EsIndex.SPU_INDEX)
                    .id(String.valueOf(spuId)));
        } catch (IOException e) {
            throw new RuntimeException("删除SPU索引失败", e);
        }
    }

    @Override
    public SearchResult search(String keyword, String queryType, Integer page) {
        int currentPage = page == null || page < 1 ? 1 : page;
        ProductQueryType type = ProductQueryType.of(queryType);
        SearchRequest request = buildSearchRequest(keyword, type, currentPage);
        try {
            SearchResponse<SpuIndexDoc> response = goodsElasticsearchClient.search(request, SpuIndexDoc.class);
            List<SpuIndexDoc> items = response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());
            int total = response.hits().total() == null ? items.size() : Math.toIntExact(response.hits().total().value());
            int totalPages = total == 0 ? 0 : (total + PAGE_SIZE - 1) / PAGE_SIZE;
            return new SearchResult(items, total, totalPages, currentPage);
        } catch (IOException e) {
            throw new RuntimeException("查询SPU索引失败", e);
        }
    }

    private SearchRequest buildSearchRequest(String keyword, ProductQueryType type, int currentPage) {
        SearchRequest.Builder builder = new SearchRequest.Builder()
                .index(Constants.EsIndex.SPU_INDEX)
                .from((currentPage - 1) * PAGE_SIZE)
                .size(PAGE_SIZE);

        if (type == ProductQueryType.HOME) {
            builder.query(matchAllQuery())
                    .sort(sort -> sort.field(field -> field.field("activityStatus").order(SortOrder.Desc)))
                    .sort(sort -> sort.field(field -> field.field("sales").order(SortOrder.Desc)));
            return builder.build();
        }

        Query keywordQuery = buildKeywordQuery(keyword);
        if (type == ProductQueryType.ACTIVITY_ONLY) {
            builder.query(query -> query.bool(bool -> bool
                    .must(keywordQuery)
                    .filter(filter -> filter.term(term -> term.field("activityStatus").value(FieldValue.of(1))))));
            return builder.build();
        }

        builder.query(keywordQuery);
        if (type == ProductQueryType.SALES_DESC) {
            builder.sort(sort -> sort.field(field -> field.field("sales").order(SortOrder.Desc)));
        } else if (type == ProductQueryType.PRICE_DESC) {
            builder.sort(sort -> sort.field(field -> field.field("sortPrice").order(SortOrder.Desc)));
        } else if (type == ProductQueryType.PRICE_ASC) {
            builder.sort(sort -> sort.field(field -> field.field("sortPrice").order(SortOrder.Asc)));
        }
        return builder.build();
    }

    private Query buildKeywordQuery(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return matchAllQuery();
        }
        return Query.of(query -> query.multiMatch(multiMatch -> multiMatch
                .query(keyword)
                .fields("spuName", "tags")));
    }

    private Query matchAllQuery() {
        return Query.of(query -> query.matchAll(matchAll -> matchAll));
    }
}
