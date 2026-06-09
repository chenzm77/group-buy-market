package com.github.chenzm77.goods.infrastructure.adapter.index;

import com.github.chenzm77.goods.domain.index.model.SpuIndexDoc;
import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SpuIndexRepositoryImpl implements SpuIndexRepository {

    @Override
    public void save(SpuIndexDoc doc) {
    }

    @Override
    public void delete(Long spuId) {
    }

    @Override
    public SearchResult search(String keyword, String sort, Integer page, Boolean activityOnly) {
        int currentPage = page == null || page < 1 ? 1 : page;
        List<SpuIndexDoc> items = new ArrayList<>();
        return new SearchResult(Collections.unmodifiableList(items), 0, 0, currentPage);
    }
}
