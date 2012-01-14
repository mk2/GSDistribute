package net.kuroichigo.GSDistribute.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import net.kuroichigo.GSDistribute.support.MainController;

public class UniteWriter extends Thread {

  @SuppressWarnings("unchecked")
  public UniteWriter(MainController mainCtl, Vector<Vector<String>> fileLst) {
    _lgr = Logger.getLogger(this.getClass().getName());
    _mainCtl = mainCtl;
    _fileLst = (Vector<Vector<String>>) fileLst.clone();
    // オリジナルのファイル名を取り出す
    String name = _fileLst.get(0).get(2);
    int index = name.lastIndexOf(".");
    _originalNam = name.substring(0, index);
  }

  @Override
  public void run() {
    int byteCnt = 0;
    _mainCtl.updateProgressLbl("progress.label.file.processing.unite");
    _mainCtl.setThreadIsRunning(true);
    try {
      FileOutputStream fos = new FileOutputStream(_originalNam);
      for (Vector<String> fileItm : _fileLst) {
        FileInputStream fis = new FileInputStream(new File(fileItm.get(2)));
        int fileSiz = Integer.parseInt(fileItm.get(1));
        byte[] readBuf = new byte[fileSiz];
        fis.read(readBuf);
        fos.write(readBuf);
        fos.flush();
        fis.close();
        byteCnt += fileSiz;
        _mainCtl.updateProgressBar(byteCnt);
      }
      fos.close();
      _mainCtl.updateProgressLbl("progress.label.file.finish.unite");
    } catch (IOException e) {
      e.printStackTrace();
    }
    _mainCtl.setThreadIsRunning(false);
  }

  private MainController _mainCtl;
  private Logger _lgr;
  private String _originalNam;
  private Vector<Vector<String>> _fileLst;

}
