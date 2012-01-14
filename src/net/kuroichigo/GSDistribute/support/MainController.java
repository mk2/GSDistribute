package net.kuroichigo.GSDistribute.support;

import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.kuroichigo.GSDistribute.io.DivideWriter;
import net.kuroichigo.GSDistribute.io.UniteWriter;
import net.kuroichigo.GSDistribute.ui.MainFrame;

/**
 * @author tsukuyomi
 *         MainController���饹�������Ϥʥץ����ʤΤǡ�ViewController�Ȥ�ModelController�Ȥ����Ѱդ��ʤ�
 *         ��<br>
 * 
 */
public class MainController implements UpdateProgressListener {

  public MainController(String[] argv) {
    _lgr = Logger.getLogger(this.getClass().getName());
    _lgr.setLevel(Level.INFO);
    _resMgr = ResourceManager.getResourceManager();
    _fileLst = new Vector<Vector<String>>();
    _colNam = new Vector<String>();
    _colNam.add(_resMgr.getString("table.column.0.title"));
    _colNam.add(_resMgr.getString("table.column.1.title"));
    _DnDTrh = new DnDTransferHandler(this);
    _mainFrm = new MainFrame(this, _fileLst, _colNam);
    _dropTgt = new DropTarget(_mainFrm, _DnDTrh);
    _fileSiz = Integer.parseInt(_resMgr.getString("default.filesize"));
    _threadIsRunning = false;
  }

  public int getFileSize() {
    return _fileSiz;
  }

  public void setFileSize(int fileSiz) {
    if (fileSiz > 0) {
      _fileSiz = fileSiz;
    }
  }

  public void setMode(int modeVal) {
    if (modeVal == MODE_UNITE) {
      _modeVal = MODE_UNITE;
    } else if (modeVal == MODE_DIVIDE) {
      _modeVal = MODE_DIVIDE;
    } else {
      _lgr.info("¸�ߤ��ʤ��⡼�ɤ����ꤵ��ޤ�����");
    }
  }

  @SuppressWarnings("unchecked")
  private void sortFileLst(Vector<Vector<String>> oldFileLst) {
    int max = 0;
    for (Vector<String> fileItm : oldFileLst) {
      max += Integer.parseInt(fileItm.get(1));
    }
    _mainFrm.setProgressBarMax(max);
    Vector<Vector<String>> newFileLst = (Vector<Vector<String>>) oldFileLst
        .clone();
    try {
      for (Vector<String> fileItm : oldFileLst) {
        String name = fileItm.get(0);
        int index = name.lastIndexOf(".");
        int number = Integer.parseInt(name.substring(index + 1));
        newFileLst.set(number, fileItm);
      }
      for (int i = 0; i < newFileLst.size(); i++) {
        oldFileLst.set(i, newFileLst.get(i));
      }
    } catch (NumberFormatException e) {
      updateProgressLbl("progress.label.file.sorterror");
    }
  }

  public void start() {
    if (_fileLst.size() > 0 && !_threadIsRunning) {
      if (_modeVal == MODE_UNITE) {
        updateProgressLbl("progress.label.file.start.unite");
        startUnite();
      } else if (_modeVal == MODE_DIVIDE) {
        updateProgressLbl("progress.label.file.start.divide");
        startDivide();
      }
    }
  }

  public void startDivide() {
    DivideWriter divideWrt = new DivideWriter(this, _fileLst, _fileSiz);
    divideWrt.start();
  }

  public void startUnite() {
    UniteWriter uniteWrt = new UniteWriter(this, _fileLst);
    uniteWrt.start();
  }

  public void resetFileLst() {
    if (!_threadIsRunning) {
      _fileLst.removeAllElements();
      updateProgressBar(0);
      _mainFrm.updateTbl();
    }
  }

  public void updateFileLst(Vector<File> fileLst) {
    for (File file : fileLst) {
      Vector<String> fileItm = new Vector<String>();
      fileItm.add(0, file.getName());
      fileItm.add(1, String.valueOf(file.length()));
      fileItm.add(2, file.getAbsolutePath());
      _fileLst.add(fileItm);
    }
    // �ɲä���������fileLst�Υ����Ȥ�Ԥ���fileLst�ι��ܰ���ܤ�
    // *.zip.001
    // *.zip.002
    // �Ȥ����դ��ˤʤäƤ���Τǡ���ĥ�Ҥ�����������Ȥ�Ԥ���
    sortFileLst(_fileLst);
    _mainFrm.updateTbl();
    _mainFrm.updateProgressLbl(fileLst.size() + " "
        + _resMgr.getString("progress.label.file.add"));
  }

  public void updateProgressBar(int cnt) {
    _mainFrm.updateProgressBar(cnt);
  }

  public void updateProgressLbl(String key) {
    _mainFrm.updateProgressLbl(_resMgr.getString(key));
  }

  public void setThreadIsRunning(boolean flag) {
    _threadIsRunning = flag;
  }

  private boolean _threadIsRunning;
  public static final int MODE_UNITE = 0;
  public static final int MODE_DIVIDE = 1;
  private int _modeVal;
  private int _fileSiz;
  private Logger _lgr;
  private DnDTransferHandler _DnDTrh;
  private DropTarget _dropTgt;
  private ResourceManager _resMgr;
  private MainFrame _mainFrm;

  /**
   * JTable��ɽ������ե�����ꥹ�Ȥ��������Vector<br/>
   * �����פ�Vector<String>��Vector�Ǥ��ꡢ�ҤȤĤ�Vector<String>��JTable��1Row���������롣<br/>
   * Vector<String>�ϸ���3�Ĥ����Ǥ��鹽������Ƥ��ꡢ1����ࡧ�ե�����̾ 2����ࡧ�ե����륵���� 3����ࡧ���Хѥ��ȤʤäƤ��롣<br/>
   * ���Τ���3������ܤ�JTable��ˤ������ԲĻ�Ǥ��롣
   */
  private Vector<Vector<String>> _fileLst;

  /**
   * JTable�Υإå���ʬ���������Vector ���ߤ� 1����ࡧ�ե�����̾ 2����ࡧ�ե����륵����
   */
  private Vector<String> _colNam;

}
