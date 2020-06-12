package com.a.controller;

import com.a.entity.User;
import com.a.repository.ResourceRepository;
import com.a.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class headImgController {
    @javax.annotation.Resource
    private ResourceLoader resourceLoader;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = {"/uploadImg"})
    public String upload(@RequestParam("file") MultipartFile file,
                         Model model, HttpSession session) throws IOException {
        if (file.isEmpty()) model.addAttribute("msg", "上传失败");

        String fileName = file.getOriginalFilename();

        File direct = new File("./src");
        String path = direct.getCanonicalPath() + "/main/resources/static/Upload/";
        File dest = new File(path + fileName);

        User user = userRepository.findUserById((Integer) session.getAttribute("userid"));
        if (!user.headImgName.equals("profile.png")) {
            File f = new File(path + user.headImgName);
            f.delete();
            System.out.println("----------file delete---" + user.headImgName);
        }
        user.headImgName = fileName;
        userRepository.save(user);

        try {
            file.transferTo(dest);

            com.a.entity.Resource resource = new com.a.entity.Resource();
            resource.title = "headImg";
            System.out.println(resource.title);
            resource.author = (String)session.getAttribute("loginuser");
            System.out.println(resource.author);
            resource.fileName = fileName;
            resource.filePath = path;
            resourceRepository.save(resource);

            model.addAttribute("msg", "上传成功");
            session.setAttribute("fileName", fileName);
            System.out.println("----------file upload---" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("msg", "上传失败");
        }

        return "userspace";
    }

    @RequestMapping(value = {"/showImg/{fileName}"})
    @ResponseBody
    public ResponseEntity<Resource> showImg(@PathVariable("fileName") String name, HttpSession session) {
        File direct = new File("./src");
        String path = direct.getPath() + "/main/resources/static/Upload/";

        return ResponseEntity.ok(resourceLoader.getResource("file:" + path + name));
    }
}
