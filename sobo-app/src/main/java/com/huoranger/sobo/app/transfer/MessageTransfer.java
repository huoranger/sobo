package com.huoranger.sobo.app.transfer;

import com.huoranger.sobo.api.response.message.MessagePageResponse;
import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
public class MessageTransfer {

    public static List<MessagePageResponse> toMessagePageResponses(List<Message> messages, List<User> users, List<BasePosts> postsList, User loginUser) {
        List<MessagePageResponse> res = new ArrayList<>();

        SafesUtil.ofList(messages).forEach(message -> {
            MessagePageResponse messagePageResponse = MessagePageResponse.builder()
                    .id(message.getId())
                    .read(message.getRead().getValue())
                    .sender(message.getSender().getId())
                    .typeDesc(message.getType().getDesc())
                    .createAt(message.getCreateAt())
                    .build();

            SafesUtil.ofList(users).forEach(user -> {
                if (user.getId().toString().equals(message.getSender().getId())) {
                    messagePageResponse.setSenderName(user.getNickname());
                    messagePageResponse.setSenderAvatar(user.getAvatar());
                }
            });

            if (MessageTypeEn.APPROVAL_ARTICLE.equals(message.getType())
                    || MessageTypeEn.APPROVAL_FAQ.equals(message.getType())
                    || MessageTypeEn.COMMENT_ARTICLE.equals(message.getType())
                    || MessageTypeEn.COMMENT_FAQ.equals(message.getType())) {
                SafesUtil.ofList(postsList).forEach(posts -> {
                    if (posts.getId().equals(Long.valueOf(message.getTitle()))) {
                        messagePageResponse.setTitle(posts.getTitle());
                        messagePageResponse.setInfoId(posts.getId().toString());
                    }
                });
            }
            if (MessageTypeEn.FOLLOW_USER.equals(message.getType())) {
                messagePageResponse.setTitle("关注了你");
                messagePageResponse.setInfoId(loginUser.getId().toString());
            }

            res.add(messagePageResponse);
        });

        return res;
    }
}
