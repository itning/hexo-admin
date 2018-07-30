package top.itning.hexoadmin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.itning.hexoadmin.service.impl.FileServiceImpl;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangn
 */
@ServerEndpoint("/command")
@Component
public class CommandWebSocket {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static int count = 0;
    private Session session;

    private static List<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sessionList.add(session);
        count++;
        logger.info("onOpen " + count);
    }

    @OnClose
    public void onClose() {
        sessionList.remove(session);
        count--;
        logger.info("onClose " + count);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info("来自客户端的消息:" + message);
        session.getBasicRemote().sendText(getDateTime() + "服务器连接成功 " + count);
    }

    public void sendMessage(String message) {
        sessionList.stream().filter(Session::isOpen).forEach(session1 -> {
            session1.getAsyncRemote().sendText(getDateTime() + message);
        });
    }

    private String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date());
    }
}
