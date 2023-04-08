package com.huoranger.sobo.domain.entity;

import com.huoranger.sobo.domain.entity.value.IdValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.common.enums.MessageChannelEn;
import com.huoranger.sobo.common.enums.MessageContentTypeEn;
import com.huoranger.sobo.common.enums.MessageReadEn;
import com.huoranger.sobo.common.enums.MessageTypeEn;

/**
 * @author huoranger
 * @create 2020/7/30
 * @desc 消息
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {

    /**
     * 消息发送渠道
     */
    private MessageChannelEn channel;

    /**
     * 消息类型
     */
    private MessageTypeEn type;

    /**
     * 是否已读
     */
    private MessageReadEn read;

    /**
     * 触发人
     */
    private IdValue sender;

    /**
     * 收件人
     */
    private IdValue receiver;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容类型
     */
    private MessageContentTypeEn contentType;

    /**
     * 内容
     */
    private String content;

}
