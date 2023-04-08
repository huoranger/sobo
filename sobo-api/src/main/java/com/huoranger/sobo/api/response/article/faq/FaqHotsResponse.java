package com.huoranger.sobo.api.response.article.faq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huoranger
 * @create 2020/11/17
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqHotsResponse implements Serializable {

    private Long id;

    private String title;

    private Date createAt;
}