package top.itning.hexoadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.hexoadmin.entity.MarkDownFile;
import top.itning.hexoadmin.service.FileService;

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
        model.addAttribute("file", new MarkDownFile());
        return "editor";
    }
}
