package me.shiki.dartjsonformat;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.actions.BaseCodeInsightAction;
import com.intellij.codeInsight.actions.CodeInsightAction;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import me.shiki.dartjsonformat.ui.JsonDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author shiki
 */
public class MainAction extends BaseCodeInsightAction {


    @Override
    public void actionPerformed(AnActionEvent event) {

        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        PsiFile mFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (mFile.getName().endsWith(Constants.DART_TYPE_NAME)) {
            JsonDialog jsonDialog = new JsonDialog(project, mFile);
            jsonDialog.setVisible(true);
        }
    }

    @NotNull
    @Override
    protected CodeInsightActionHandler getHandler() {
        return null;
    }

}
