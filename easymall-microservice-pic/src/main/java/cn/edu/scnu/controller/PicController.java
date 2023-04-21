/**
 * @Author: Neo
 * @Date: 2023/04/10 星期一 19:48:08 下午
 * @Project: easymall-project
 * @IDE: IntelliJ IDEA
 **/

package cn.edu.scnu.controller;

import cn.edu.scnu.service.PicService;
import com.easymall.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pic")
public class PicController {

    @Autowired
    private PicService picService;

    @RequestMapping("/upload")
    public PicUploadResult picUpload(MultipartFile pic) {
        PicUploadResult result = picService.picUpload(pic);
        return result;

    }
}
