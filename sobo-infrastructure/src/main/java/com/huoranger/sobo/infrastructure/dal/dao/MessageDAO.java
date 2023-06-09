package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.MessageDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
public interface MessageDAO {

    void insert(MessageDO messageDO);

    MessageDO get(Long id);

    List<MessageDO> query(MessageDO messageDO);

    void updateToRead(MessageDO messageDO);

    Long countUnRead(@Param("receiver") String receiver, @Param("read") String read);

    void deleteInTypesAndTitle(@Param("types") List<String> types, @Param("title") String title);
}
