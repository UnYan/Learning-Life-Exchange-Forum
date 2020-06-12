package com.a.controller;

import com.a.entity.Resource;
import com.a.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class UploadController {

    @Autowired
    ResourceRepository resourceRepository;

    @PostMapping(value = {"/uploadfile"})
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("title") String title,
                         Model model, HttpSession session) throws IOException {
        if (file.isEmpty()) model.addAttribute("msg", "上传失败");

        String fileName = file.getOriginalFilename();

        File direct = new File("./src");
        String path = direct.getCanonicalPath() + "/main/resources/static/Upload/";
        File dest = new File(path + fileName);

        try {
            file.transferTo(dest);

            Resource resource = new Resource();
            resource.title = title;
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

        return "resource";
    }

    @RequestMapping("/downloadfile/{id}")
    public String downLoad(@PathVariable("id") Integer id, HttpServletResponse response, Model model) throws IOException {
        Resource resource = resourceRepository.findResourceById(id);
        String name = resource.fileName;
        String path = resource.filePath;

        File file = new File(path + name);
        if(file.exists()){ // 判断文件父目录是否存在
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流

            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    os.flush();
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("----------file download---" + name);
            model.addAttribute("res", "下载成功");

            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
         }

        return "resource";
    }

    @PostMapping(value = {"/delReSrc/{id}"})
    public String delResource(@PathVariable("id") Integer id, Model model) throws IOException {
        Resource resource = resourceRepository.findResourceById(id);
        File file = new File(resource.filePath + resource.fileName);

        if (file.exists()) file.delete();
        resourceRepository.delete(resource);

        System.out.println("----------file download---" + resource.fileName);
        model.addAttribute("res", "删除成功");

        return "resource";
    }
}