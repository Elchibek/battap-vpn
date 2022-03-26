package com.battap.vpn.service;

import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
// import com.battap.vpn.service.dto.VirServerDTO;
import com.jcraft.jsch.*;

@Service
public class JSchService {

    public JSchService() {}

    // public StringBuilder listFolderStructure(VirServerDTO vi, String command) {
    //     StringBuilder builder = new StringBuilder();
    //     Session jschSession = null;

    //     try {
    //         JSch jsch = new JSch();
    //         jschSession = jsch.getSession(vi.getUserName(), vi.getRemoteHost(), vi.getRemotePort());
    //         jschSession.setPassword(vi.getPassword());
    //         jschSession.setConfig("StrictHostKeyChecking", "no");
    //         jschSession.connect(vi.getSessionTimeOut());

    //         ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
    //         channelExec.setCommand(command);

    //         ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
    //         channelExec.setOutputStream(responseStream);
    //         channelExec.connect(vi.getChanelTimeOut());
    //         while (channelExec.isConnected()) {
    //             try {
    //                 Thread.sleep(100);
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
        
    //         builder.append(new String(responseStream.toByteArray()));
    //         channelExec.disconnect();
    //     } catch (JSchException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (jschSession != null) {
    //             jschSession.disconnect();
    //         }
    //     }
    //     return builder;
    // }

}