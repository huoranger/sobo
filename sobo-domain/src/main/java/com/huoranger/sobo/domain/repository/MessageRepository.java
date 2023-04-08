package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Message;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/22
 * @desc
 **/
public interface MessageRepository {

    void save(Message message);

    Message get(Long id);

    PageResult<Message> page(PageRequest<Message> pageRequest);

    void updateToRead(Message message);

    Long countUnRead(Long receiver);

    void deleteInTypesAndTitle(List<MessageTypeEn> typeEns, String title);
}
