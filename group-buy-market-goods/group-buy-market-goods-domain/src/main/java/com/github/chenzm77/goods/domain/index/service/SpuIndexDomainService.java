package com.github.chenzm77.goods.domain.index.service;

import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;

public interface SpuIndexDomainService {

    SpuIndexRepository.SearchResult search(String keyword, String sort, Integer page, Boolean activityOnly);
}
