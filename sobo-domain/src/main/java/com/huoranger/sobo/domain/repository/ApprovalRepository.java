package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.domain.entity.Approval;

/**
 * @author huoranger
 * @create 2020/12/1
 * @desc
 **/
public interface ApprovalRepository {

    void save(Approval approval);

    void delete(Long approvalId);

    Approval get(Long postsId, Long userId);

}
