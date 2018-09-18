package me.shiki.dartjsonformat.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import me.shiki.dartjsonformat.Constants;
import me.shiki.dartjsonformat.action.DataWriter;
import me.shiki.dartjsonformat.model.MustacheEntity;
import me.shiki.dartjsonformat.util.ClsUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

/**
 * @author shiki
 */
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
        editorPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hideTip();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hideTip();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hideTip();
            }
        });
    }

    private void hideTip() {
        if (this.tip.isVisible()) {
            this.tip.setVisible(false);
        }
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
                        showTip("Json text format error");
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
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                String jsonText = editorPane.getText().trim();
                MustacheEntity mustacheEntity = clsUtils.jsonElementToMustacheEntity(file.getName(), jsonText);
                DataWriter dataWriter = new DataWriter(project, clsUtils, mustacheEntity, file);
                dataWriter.start();
                dispose();
            } catch (Exception exception) {
                showTip("Json text format error");
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private void showTip(String tip) {
        if (!this.tip.isVisible()) {
            this.tip.setVisible(true);
        }
        this.tip.setText(tip);
    }

}
