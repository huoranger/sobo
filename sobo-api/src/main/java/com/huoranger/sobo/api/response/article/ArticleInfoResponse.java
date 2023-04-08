package com.huoranger.sobo.api.response.article;

import com.huoranger.sobo.api.vo.TagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/3
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoResponse implements Serializable {

    private Long id;

    private Long typeId;

    private String auditState;

    private Boolean official;

    private Boolean top;

    private Boolean marrow;

    private String title;

    private String htmlContent;

    private String markdownContent;

    private String headImg;

    private Long authorId;

    private String authorNickname;

    private String authorAvatar;

    private Date createAt;

    private Date updateAt;

    private Long views;

    private Long approvals;

    private Long comments;

    private List<TagVO> tags;
}
