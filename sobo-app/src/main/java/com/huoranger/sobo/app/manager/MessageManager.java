package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.app.support.PageUtil;
import com.huoranger.sobo.app.transfer.MessageTransfer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.request.message.MessagePageRequest;
import com.huoranger.sobo.api.response.message.MessagePageResponse;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.enums.MessageChannelEn;
import com.huoranger.sobo.common.enums.MessageReadEn;
import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.entity.User;
import com.huoranger.sobo.domain.entity.value.IdValue;
import com.huoranger.sobo.domain.repository.MessageRepository;
import com.huoranger.sobo.domain.repository.PostsRepository;
import com.huoranger.sobo.domain.repository.UserRepository;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@Component
public class MessageManager {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PostsRepository postsRepository;

    @IsLogin
    public PageResponseModel<MessagePageResponse> page(PageRequestModel<MessagePageRequest> pageRequestModel) {
        MessagePageRequest pageRequest = pageRequestModel.getFilter();
        Message message = Message.builder()
                .channel(MessageChannelEn.STATION_LETTER)
                .receiver(IdValue.builder()
                        .id(LoginUserContext.getUser().getId().toString())
                        .build())
                .type(MessageTypeEn.getEntityByDesc(pageRequest.getTypeDesc()))
                .build();
        PageResult<Message> pageResult = messageRepository.page(PageUtil.buildPageRequest(pageRequestModel, message));
        if (ObjectUtils.isEmpty(pageResult.getList())) {
            return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), new ArrayList<>());
        }

        Set<Long> userIds = new HashSet<>();
        Set<Long> postsIds = new HashSet<>();
        SafesUtil.ofList(pageResult.getList()).forEach(message1 -> {
            userIds.add(Long.valueOf(message1.getSender().getId()));

            if (MessageTypeEn.APPROVAL_ARTICLE.equals(message.getType())
                    || MessageTypeEn.APPROVAL_FAQ.equals(message.getType())
                    || MessageTypeEn.COMMENT_FAQ.equals(message.getType())
                    || MessageTypeEn.COMMENT_ARTICLE.equals(message.getType())) {
                postsIds.add(Long.valueOf(message1.getTitle()));
            }
        });
        List<User> users = userRepository.queryByIds(new ArrayList<>(userIds));

        List<BasePosts> postsList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(postsIds)) {
            postsList = postsRepository.queryInIds(postsIds);
        }

        List<MessagePageResponse> responses = MessageTransfer.toMessagePageResponses(pageResult.getList(), users, postsList, LoginUserContext.getUser());
        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), responses);
    }

    @IsLogin
    public void markIsRead(Long messageId) {
        Message message = messageRepository.get(messageId);
        CheckUtil.isEmpty(message, ErrorCodeEn.MESSAGE_NOT_EXIST);
        CheckUtil.isFalse(LoginUserContext.getUser().getId().toString().equals(message.getReceiver().getId()), ErrorCodeEn.MESSAGE_NOT_EXIST);

        message.setRead(MessageReadEn.YES);
        messageRepository.updateToRead(message);
    }

    @IsLogin
    public Long countUnRead() {
        return messageRepository.countUnRead(LoginUserContext.getUser().getId());
    }
}
