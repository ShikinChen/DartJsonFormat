package me.shiki.dartjsonformat.ui;

import com.google.gson.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import me.shiki.dartjsonformat.Constants;
import me.shiki.dartjsonformat.model.MustacheEntity;
import me.shiki.dartjsonformat.util.ClsUtils;
import me.shiki.dartjsonformat.util.MustacheUtils;

import javax.swing.*;
import java.awt.event.*;

public class JsonDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton formatButton;
    private JEditorPane editorPane;
    private JLabel fileName;
    private JLabel tip;

    private AlertDialog alertDialog;

    private PsiFile file;
    private Project project;
    private ClsUtils clsUtils;

    public JsonDialog(Project project, PsiFile file) {
        this.project = project;
        this.file = file;
        initView();
        clsUtils = new ClsUtils();
        initListener();
    }

    private void initView() {
        tip.setText("Generating code");
        tip.setVisible(false);
        fileName.setText(file.getName());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(Constants.PLUGIN_NAME);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void initListener() {
        formatButton.addActionListener(
                e -> {
                    String json = editorPane.getText().trim();
                    try {
                        JsonElement jsonElement = clsUtils.tojsonElement(json);
                        if (jsonElement.isJsonObject()) {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            editorPane.setText(clsUtils.toJsonText(jsonObject));
                        } else if (jsonElement.isJsonArray()) {
                            JsonArray jsonArray = jsonElement.getAsJsonArray();
                            editorPane.setText(clsUtils.toJsonText(jsonArray));
                        }
                    } catch (Exception exception) {
                        showAlertDialog("Json text format error");
                    }
                }
        );

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            editorPane.setEditable(false);
            tip.setVisible(true);
            String jsonText = editorPane.getText().trim();
            MustacheEntity mustacheEntity = clsUtils.jsonElementToClsEntityList(file.getName(), jsonText);
            mustacheEntity.setDir(clsUtils.getClsDir(file));
            MustacheUtils.genDartFile(mustacheEntity, isSuccess -> {
                editorPane.setEditable(true);
                tip.setVisible(false);
                if (isSuccess) {
                    dispose();
                }
            });
        } catch (Exception exception) {
            editorPane.setEditable(true);
            showAlertDialog("Json text format error");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private void showAlertDialog(String tip) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog();
        }
        alertDialog.setTip(tip);
        alertDialog.setVisible(true);
    }

}
