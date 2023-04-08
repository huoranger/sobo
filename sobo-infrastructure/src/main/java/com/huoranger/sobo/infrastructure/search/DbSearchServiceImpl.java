package com.huoranger.sobo.infrastructure.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.enums.SearchTypeEn;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.Search;
import com.huoranger.sobo.domain.service.SearchService;
import com.huoranger.sobo.infrastructure.AbstractPostsRepository;
import com.huoranger.sobo.infrastructure.dal.dao.SearchDAO;
import com.huoranger.sobo.infrastructure.dal.dataobject.SearchDO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Component
public class DbSearchServiceImpl extends AbstractPostsRepository implements SearchService {

    @Resource
    private SearchDAO searchDAO;

    @Override
    public PageResult<Posts> pagePosts(PageRequest<String> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        List<SearchDO> searchDOS = searchDAO.query(SearchDO.builder()
                .content(pageRequest.getFilter())
                .title(pageRequest.getFilter())
                .type(SearchTypeEn.POSTS.getValue())
                .build());

        PageInfo<SearchDO> pageInfo = new PageInfo<>(searchDOS);

        if (ObjectUtils.isEmpty(searchDOS)) {
            return PageResult.build(pageInfo.getTotal(), pageInfo.getSize(), new ArrayList<>());
        }

        List<Long> postsIds = new ArrayList<>();
        searchDOS.forEach(searchDO -> postsIds.add(searchDO.getEntityId()));

        return basePagePosts(postsIds, pageInfo, null);
    }

    @Override
    public void deleteByPostsId(Long postsId) {
        searchDAO.delete(SearchTypeEn.POSTS.getValue(), postsId);
    }

    @Override
    public void save(Search search) {
        SearchDO searchDO = SearchDO.builder()
                .type(search.getType().getValue())
                .title(search.getTitle())
                .content(search.getContent())
                .entityId(search.getEntityId())
                .build();
        searchDO.initBase();

        searchDAO.insert(searchDO);
    }
}
