package top.itning.hexoadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.itning.hexoadmin.entity.MarkDownFile;
import top.itning.hexoadmin.service.FileService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 路由
 *
 * @author wangn
 */
@Controller
public class FrameController {
    private final FileService fileService;

    @Autowired
    public FrameController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fileList", fileService.getAllFileList());
        return "index";
    }

    @GetMapping("/editor/{location}")
    public String editor(@PathVariable("location") String location, Model model) {
        model.addAttribute("file", fileService.getMarkDownFileByFileName(location));
        return "editor";
    }

    @GetMapping("/newPost")
    public String newPost(Model model) {
        String date = new SimpleDateFormat("yyyy-MM-dd-").format(new Date());
        MarkDownFile markDownFile = new MarkDownFile();
        markDownFile.setContent("---\n" +
                "layout: post\n" +
                "title: '  '\n" +
                "comments: true\n" +
                "date: " + date + "\n" +
                "updated: " + date + "\n" +
                "urlname: \n" +
                "categories: \n" +
                "tags: \n" +
                "---");
        model.addAttribute("file", markDownFile);
        return "editor";
    }

    @PostMapping("/save")
    public String save(MarkDownFile markDownFile) {
        fileService.saveMarkDownFile(markDownFile);
        return "redirect:/";
    }
}
