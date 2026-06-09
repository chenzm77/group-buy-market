package com.github.chenzm77.goods.domain.index.service.impl;

import com.github.chenzm77.goods.domain.index.repository.SpuIndexRepository;
import com.github.chenzm77.goods.domain.index.service.SpuIndexDomainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpuIndexDomainServiceImpl implements SpuIndexDomainService {

    @Resource
    private SpuIndexRepository spuIndexRepository;

    @Override
    public SpuIndexRepository.SearchResult search(String keyword, String sort, Integer page, Boolean activityOnly) {
        return spuIndexRepository.search(keyword, sort, page, activityOnly);
    }
}
