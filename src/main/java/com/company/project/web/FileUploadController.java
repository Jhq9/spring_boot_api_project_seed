package com.company.project.web;

import com.company.project.core.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by jinhuaquan on 2017/8/2.
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping("/file")
    public Result uploadFile(@RequestParam List<MultipartFile> multipartFile){
        System.out.println(multipartFile.get(0).getName()+","+multipartFile.get(1).getOriginalFilename());

        return new Result();
    }


    @GetMapping("/exception")
    public Result exception(){
        throw new RuntimeException();
    }
}
