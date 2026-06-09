package com.github.chenzm77.goods.trigger.mq;

import com.github.chenzm77.goods.app.message.SpuIndexRebuildMessage;
import com.github.chenzm77.goods.app.service.GoodsApplicationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SpuIndexRebuildConsumer {

    @Resource
    private GoodsApplicationService goodsApplicationService;

    public void consume(SpuIndexRebuildMessage message) {
        goodsApplicationService.rebuildProductIndex(message.getSpuId());
    }
}
