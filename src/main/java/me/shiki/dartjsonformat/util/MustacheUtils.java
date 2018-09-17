package me.shiki.dartjsonformat.util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import me.shiki.dartjsonformat.model.MustacheEntity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shiki
 */
public final class MustacheUtils {

    public final static void genDartFile(MustacheEntity mustacheEntity, GenDartFileListener genDartFileListener) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("dart/model.mustache");
        Mustache gMustache = mf.compile("dart/model.g.mustache");
        String path = mustacheEntity.getDir() + "/" + mustacheEntity.getFileName();
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        try {
            file = new File(path + ".dart");
            mustache.execute(new PrintWriter(file), mustacheEntity).flush();

            file = new File(path + ".g.dart");
            gMustache.execute(new PrintWriter(file), mustacheEntity).flush();
            if (genDartFileListener != null) {
                genDartFileListener.genDartFileResult(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (genDartFileListener != null) {
                genDartFileListener.genDartFileResult(false);
            }
        }
    }

    public interface GenDartFileListener {
        /**
         * 监听生成代码结果
         *
         * @param isSuccess
         */
        void genDartFileResult(boolean isSuccess);
    }
}
