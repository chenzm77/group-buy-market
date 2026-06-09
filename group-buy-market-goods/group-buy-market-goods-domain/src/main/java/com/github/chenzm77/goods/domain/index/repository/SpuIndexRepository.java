package com.github.chenzm77.goods.domain.index.repository;

import com.github.chenzm77.goods.domain.index.model.SpuIndexDoc;

import java.util.List;

public interface SpuIndexRepository {

    void save(SpuIndexDoc doc);

    void delete(Long spuId);

    SearchResult search(String keyword, String sort, Integer page, Boolean activityOnly);

    class SearchResult {
        private final List<SpuIndexDoc> items;
        private final Integer total;
        private final Integer totalPages;
        private final Integer currentPage;

        public SearchResult(List<SpuIndexDoc> items, Integer total, Integer totalPages, Integer currentPage) {
            this.items = items;
            this.total = total;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
        }

        public List<SpuIndexDoc> getItems() {
            return items;
        }

        public Integer getTotal() {
            return total;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }
    }
}
