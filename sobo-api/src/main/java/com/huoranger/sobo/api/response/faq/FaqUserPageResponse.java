package com.huoranger.sobo.api.response.faq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.api.vo.SolutionVO;
import com.huoranger.sobo.api.vo.TagVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqUserPageResponse implements Serializable {

    private Long id;

    private String category;

    private String auditState;

    private String categoryDesc;

    private String title;

    private String introduction;

    private Long authorId;

    private String authorNickname;

    private String authorAvatar;

    private Date createAt;

    private Date updateAt;

    private Long views;

    private Long approvals;

    private Long comments;

    private List<TagVO> tags;

    private SolutionVO solution;

    private String solutionDesc;

}
