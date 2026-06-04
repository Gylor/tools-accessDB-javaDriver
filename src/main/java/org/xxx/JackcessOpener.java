package org.xxx;

import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import net.ucanaccess.jdbc.JackcessOpenerInterface;

import java.io.File;
import java.io.IOException;

public class JackcessOpener implements JackcessOpenerInterface {
    @Override
    public Database open(File fl, String pwd) throws IOException {
        DatabaseBuilder dbb = new DatabaseBuilder(fl);
        // 关键点：自动识别并注入密码处理器
        dbb.setCodecProvider(new CryptCodecProvider(pwd));
        return dbb.open();
    }
}
