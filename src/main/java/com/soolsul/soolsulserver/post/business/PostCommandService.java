package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.exception.UserNotFoundException;
import com.soolsul.soolsulserver.post.domain.Post;
import com.soolsul.soolsulserver.post.domain.PostPhoto;
import com.soolsul.soolsulserver.post.domain.PostRepository;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
import com.soolsul.soolsulserver.bar.domain.Bar;
import com.soolsul.soolsulserver.bar.domain.BarRepository;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final BarRepository barRepository;

    public void create(String userId, PostCreateRequest request) {
        if (isInvalidUserId(userId)) {
            throw new UserNotFoundException();
        }

        Bar findBar = barRepository.findById(request.getBarId())
                .orElseThrow(BarNotFoundException::new);

        Post newPost = new Post(userId,
                findBar.getId(),
                request.getScore(),
                request.getPostContent());

        newPost.addPhotoList(convertPhotoList(request, findBar));

        postRepository.save(newPost);
    }

    // TODO : 이미지 URL만 전달 받게 되는데, 이걸 PostPhoto로 저장하는 것이 맞는가?
    private List<PostPhoto> convertPhotoList(PostCreateRequest request, Bar findBar) {
        return request.getImages()
                .stream()
                .map(url -> new PostPhoto(findBar.getId(), "origin", "imageUrl", "."))
                .collect(Collectors.toList());
    }

    private boolean isInvalidUserId(String userId) {
        return !StringUtils.hasText(userId);
    }
}
