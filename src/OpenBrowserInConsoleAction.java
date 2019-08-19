import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2019/8/19.
 */
public class OpenBrowserInConsoleAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        SelectionModel model = editor.getSelectionModel();
        String selectedText = model.getSelectedText();

        if(StringUtils.isBlank(selectedText))
        {
            return;
        }
        String url = "http://www.baidu.com/s?wd="+selectedText;
        String myOS = System.getProperty("os.name").toLowerCase();
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                Messages.showErrorDialog("错误", ExceptionUtils.getRootCauseMessage(e));
            }
        }else { // Definitely Non-windows
            Runtime runtime = Runtime.getRuntime();
            if(myOS.contains("mac")) { // Apples
                try {
                    runtime.exec("open " + url);
                } catch (IOException e) {
                    Messages.showErrorDialog("错误", ExceptionUtils.getRootCauseMessage(e));
                }
            }
            else if(myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {

                    Messages.showErrorDialog("错误", ExceptionUtils.getRootCauseMessage(e));

                }
            }

        }
    }
}
