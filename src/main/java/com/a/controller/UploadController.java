package com.a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @PostMapping(value = {"/uploadfile"})
    public String upload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) model.addAttribute("msg", "上传失败");

        String fileName = file.getOriginalFilename();
        /* String filePath = "/Users/kxw/Desktop/SpringBoot Project/fileUpload/src/main/resources/upload/";
            路径自定义，别忘修改下面一行代码,最好将路径定义到专门的文件夹里
        */
        File dest = new File(fileName);

        try {
            file.transferTo(dest);
            model.addAttribute("msg", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "上传失败");
        }

        return "editormd";
    }
}