package com.github.chenzm77.goods.trigger.mq;

import com.github.chenzm77.goods.app.service.GoodsApplicationService;
import com.github.chenzm77.goods.domain.event.SpuIndexRebuildEvent;
import com.github.chenzm77.goods.types.common.Constants;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(topic = Constants.MqTopic.SPU_INDEX_REBUILD, consumerGroup = "goods-spu-index-rebuild-consumer")
public class SpuIndexRebuildConsumer implements RocketMQListener<SpuIndexRebuildEvent> {

    @Resource
    private GoodsApplicationService goodsApplicationService;

    @Override
    public void onMessage(SpuIndexRebuildEvent event) {
        goodsApplicationService.rebuildProductIndex(event.getSpuId());
    }
}
