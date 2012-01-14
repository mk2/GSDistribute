package net.kuroichigo.GSDistribute.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import net.kuroichigo.GSDistribute.support.MainController;

public class DivideWriter extends Thread {

  @SuppressWarnings("unchecked")
  public DivideWriter(MainController mainCtl, Vector<Vector<String>> fileLst,
      int fileSiz) {
    _lgr = Logger.getLogger(this.getClass().getName());
    _mainCtl = mainCtl;
    _fileLst = (Vector<Vector<String>>) fileLst.clone();
    _fileSiz = fileSiz;
  }

  @Override
  public void run() {
    int byteCnt = 0;
    _mainCtl.updateProgressLbl("progress.label.file.processing.divide");
    _mainCtl.setThreadIsRunning(true);
    try {
      for (Vector<String> fileItm : _fileLst) {
        FileInputStream fis = new FileInputStream(new File(fileItm.get(2)));
        int totalFileSiz = Integer.parseInt(fileItm.get(1));
        int _totalFileSiz = totalFileSiz;
        String baseNam = fileItm.get(2);
        for (int i = 0; totalFileSiz > 0; i++) {
          FileOutputStream fos = new FileOutputStream(new File(baseNam
              + String.format(".%03d", i)));
          byte[] readBuf = null;
          int incByteCnt = 0;
          if (totalFileSiz > _fileSiz) {
            readBuf = new byte[_fileSiz];
            incByteCnt = _fileSiz;
          } else {
            readBuf = new byte[totalFileSiz];
            incByteCnt = totalFileSiz;
          }
          fis.read(readBuf);
          if (readBuf != null)
            fos.write(readBuf);
          fos.flush();
          fos.close();
          totalFileSiz -= _fileSiz;
          byteCnt += incByteCnt;
          _mainCtl.updateProgressBar(byteCnt);
        }
        _mainCtl.updateProgressBar(byteCnt);
        fis.close();
        _mainCtl.updateProgressLbl("progress.label.file.finish.divide");
      }
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    _mainCtl.setThreadIsRunning(false);
  }

  private Logger _lgr;
  private MainController _mainCtl;
  private Vector<Vector<String>> _fileLst;
  private int _fileSiz;

}
