package top.itning.hexoadmin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.itning.hexoadmin.config.CommandWebSocket;
import top.itning.hexoadmin.entity.MarkDownFile;
import top.itning.hexoadmin.entity.UserProps;
import top.itning.hexoadmin.service.FileService;
import top.itning.hexoadmin.util.ExecCommand;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 路由
 *
 * @author wangn
 */
@Controller
public class FrameController {
    private static final Logger logger = LoggerFactory.getLogger(FrameController.class);

    private final FileService fileService;

    private final ExecCommand execCommand;

    private final UserProps userProps;

    private final CommandWebSocket commandWebSocket;

    @Autowired
    public FrameController(FileService fileService, ExecCommand execCommand, UserProps userProps, CommandWebSocket commandWebSocket) {
        this.fileService = fileService;
        this.execCommand = execCommand;
        this.userProps = userProps;
        this.commandWebSocket = commandWebSocket;
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

    @GetMapping("/message")
    public String message() {
        return "message";
    }

    @GetMapping("/sendMsg")
    @ResponseBody
    public void send(String msg) throws IOException {
        switch (msg) {
            case "server": {
                try {
                    commandWebSocket.sendMessage("run [hexo server]");
                    execCommand.execute(userProps.getHexoCmdPath() + " server", userProps.getWorkingDir());
                } catch (InterruptedException e) {
                    logger.error("server start error ", e);
                    commandWebSocket.sendMessage(e.toString());
                }
                break;
            }
            case "close_now": {
                execCommand.shutdownNow();
                commandWebSocket.sendMessage("shutdownNow");
                break;
            }
            default:
        }
    }
}
