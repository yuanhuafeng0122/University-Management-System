package com.yuanhuafeng.employment.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yuanhuafeng.employment.config.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String ip;

//    private static final String ip = "http://localhost";

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    //文件上传
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        if (file.isEmpty()){
           return Result.fail(100010,"文件上传失败");
        }
        String originalFilename = file.getOriginalFilename();
        //定义文件的唯一表示(随机数与文件绑定)
        String flag = IdUtil.fastUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag +"_" + originalFilename;
        System.out.println(rootFilePath);
        FileUtil.writeBytes(file.getBytes() , rootFilePath);
        return Result.success("http://"+ ip + ":" + port + "/files/" + flag );
    }


    /**
     * 富文本上传接口
     * @param file
     * @return
     * @throws IOException
     */
    //文件上传
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //定义文件的唯一表示(随机数与文件绑定)
        String flag = IdUtil.fastUUID();
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" + flag +"_" + originalFilename;
        FileUtil.writeBytes(file.getBytes() , rootFilePath);
        String url =  ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", url);
        json.set("data", arr);

        return json;
    }


    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
