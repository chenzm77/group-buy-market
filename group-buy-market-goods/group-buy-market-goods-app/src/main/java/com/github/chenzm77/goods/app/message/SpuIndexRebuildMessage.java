package com.github.chenzm77.goods.app.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuIndexRebuildMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long spuId;
    private String reason;
    private Long eventTime;
}
