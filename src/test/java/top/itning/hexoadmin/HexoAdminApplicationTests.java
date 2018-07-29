package top.itning.hexoadmin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hexoadmin.service.FileService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HexoAdminApplicationTests {
    @Autowired
    private FileService fileService;

    @Test
    public void contextLoads() {

    }
}
