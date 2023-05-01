package com.shopping.mall.main.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@RestController
@ResponseBody
public class MainController {

    @Autowired
    private Environment env;

    @GetMapping("/manager/image/{filename:.+}")
    public void getImage(@PathVariable String filename, HttpServletResponse response) {

        String uploadDir = env.getProperty("shared.image.upload-dir");
        Path path = Paths.get(uploadDir + "/" + filename);

        try {
            InputStream inputStream = Files.newInputStream(path);
            if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                response.setContentType("image/jpeg");
            } else if (filename.endsWith(".png")) {
                response.setContentType("image/png");
            } else if (filename.endsWith(".gif")) {
                response.setContentType("image/gif");
            } else if (filename.endsWith(".bmp")) {
                response.setContentType("image/bmp");
            } else {
                response.setContentType("application/octet-stream");
            }
            IOUtils.copy(inputStream, response.getOutputStream()); // 이미지 파일 전송
        } catch (IOException e) {
            // 예외 처리
        }
    }

}
