package com.yupi.yunpicture.infrastructure.api.imagesearch;

import com.yupi.yunpicture.infrastructure.api.imagesearch.model.ImageSearchResult;
import com.yupi.yunpicture.infrastructure.api.imagesearch.sub.GetImageFirstUrlApi;
import com.yupi.yunpicture.infrastructure.api.imagesearch.sub.GetImageListApi;
import com.yupi.yunpicture.infrastructure.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     *
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.uploadImageByUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
      String imageUrl = "https://cn.bing.com/th/id/OIP-C.IJZgTNx1vp9EML_1wV5p2gHaEo?w=233&h=180&c=7&r=0&o=7&dpr=1.3&pid=1.7&rm=3";
        List<ImageSearchResult> resultList = searchImage(imageUrl);
        System.out.println("结果列表" + resultList);
    }
}

