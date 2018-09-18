package me.shiki.dartjsonformat.action;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import me.shiki.dartjsonformat.Constants;
import me.shiki.dartjsonformat.model.MustacheEntity;
import me.shiki.dartjsonformat.util.ClsUtils;
import me.shiki.dartjsonformat.util.MustacheUtils;
import org.jetbrains.annotations.NotNull;


/**
 * @author shiki
 */
public class DataWriter extends WriteCommandAction.Simple {
    private Project project;
    private MustacheEntity mustacheEntity;
    private ClsUtils clsUtils;
    private MustacheUtils.GenDartFileListener genDartFileListener;
    private PsiFile file;

    public DataWriter(Project project, ClsUtils clsUtils, MustacheEntity mustacheEntity, PsiFile file) {
        super(project, file);
        this.project = project;
        this.mustacheEntity = mustacheEntity;
        this.clsUtils = clsUtils;
        this.file = file;
    }

    public void start() {
        ProgressManager.getInstance().run(new Task.Backgroundable(project, Constants.PLUGIN_NAME) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    progressIndicator.setIndeterminate(true);
                    execute();
                    progressIndicator.setIndeterminate(false);
                    progressIndicator.setFraction(1.0);
                } catch (Exception e) {
                    progressIndicator.setIndeterminate(false);
                    progressIndicator.setFraction(1.0);
                }
            }
        });
    }

    @Override
    protected void run() throws Throwable {
        mustacheEntity.setDir(clsUtils.getClsDir(file));
        MustacheUtils.genDartFile(mustacheEntity, genDartFileListener);
        file.getParent().getVirtualFile().refresh(true, true);
    }

    public MustacheUtils.GenDartFileListener getGenDartFileListener() {
        return genDartFileListener;
    }

    public void setGenDartFileListener(MustacheUtils.GenDartFileListener genDartFileListener) {
        this.genDartFileListener = genDartFileListener;
    }
}
