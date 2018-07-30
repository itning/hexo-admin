package top.itning.hexoadmin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hexoadmin.entity.UserProps;
import top.itning.hexoadmin.service.FileService;
import top.itning.hexoadmin.util.ExecCommand;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HexoAdminApplicationTests {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserProps userProps;

    @Autowired
    private ExecCommand execCommand;

    @Test
    public void contextLoads() throws IOException, InterruptedException {
        execCommand.execute(userProps.getHexoCmdPath() + " server", userProps.getWorkingDir());
    }
}
