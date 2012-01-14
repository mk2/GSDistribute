package net.kuroichigo.GSDistribute.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.kuroichigo.GSDistribute.support.MainController;
import net.kuroichigo.GSDistribute.support.ResourceManager;

/**
 * @author tsukuyomi メインウィンドウ。ドラッグアンドドロップに対応している。
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

  /**
   * @param mainCtl
   */
  public MainFrame(MainController mainCtl, Vector<Vector<String>> fileLst,
      Vector<String> colNam) {
    _mainCtl = mainCtl;
    _resMgr = ResourceManager.getResourceManager();
    _fileLst = fileLst;
    _colNam = colNam;
    setupComponents();
    buildUI();
    setVisible(true);
    _mainFrm = this;
  }

  private void buildUI() {
    setTitle(_resMgr.getString("window.title"));
    setupComponents();
    setJMenuBar(_mainMenuBar);
    Container cntnr = getContentPane();
    cntnr.add(_mainPnl, BorderLayout.CENTER);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setBounds(Integer.parseInt(_resMgr.getString("window.position.x")),
        Integer.parseInt(_resMgr.getString("window.position.y")),
        Integer.parseInt(_resMgr.getString("window.size.width")),
        Integer.parseInt(_resMgr.getString("window.size.height")));
  }

  public void setProgressBarMax(int max) {
    _progressBar.setMaximum(max);
    _progressBar.setMinimum(0);
  }

  private void setupComponents() {
    // メインメニュー
    _mainMenuBar = new JMenuBar();
    setupMenus();
    // メインパネル
    _mainPnl = new JPanel(new BorderLayout());
    // ファイルを表示するテーブル
    _fileListTbl = new JTable(_fileLst, _colNam);
    _fileListTbl.setEnabled(false);
    _scrollPan = new JScrollPane(_fileListTbl);
    _scrollPan.setPreferredSize(_fileListTbl.getSize());
    _mainPnl.add(_scrollPan, BorderLayout.CENTER);
    // コントロールボタンやプログレスバーなどを表示するパネル
    _controlPnl = new JPanel(new BorderLayout());
    // プログレスラベルとバー
    _progressLbl = new JLabel(_resMgr.getString("progress.label.default"));
    _progressBar = new JProgressBar();
    _progressBar.setStringPainted(true);
    // ボタン
    _startBtn = new JButton(_resMgr.getString("button.start"));
    _resetBtn = new JButton(_resMgr.getString("button.reset"));
    // ActionListener
    _startBtn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        _mainCtl.start();
      }
    });
    _resetBtn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        _mainCtl.resetFileLst();
      }
    });
    _controlPnl.add(_progressBar, BorderLayout.CENTER);
    _controlPnl.add(_startBtn, BorderLayout.WEST);
    _controlPnl.add(_resetBtn, BorderLayout.EAST);
    _controlPnl.add(_progressLbl, BorderLayout.SOUTH);
    _mainPnl.add(_controlPnl, BorderLayout.SOUTH);
  }

  private void setupMenus() {

    _modeBtnGrp = new ButtonGroup();

    // ファイルメニュー
    JMenu fileMenu = new JMenu(_resMgr.getString("menu.file"));
    JMenuItem fileMenuOpen = new JMenuItem(_resMgr.getString("menu.file.open"));
    JMenuItem fileMenuExit = new JMenuItem(_resMgr.getString("menu.file.exit"));
    // ActionListener
    fileMenuOpen.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        FileDialog fileDlg = new FileDialog(MainFrame.this, _resMgr
            .getString("filedialog.title"), FileDialog.LOAD);
        fileDlg.setVisible(true);
        String name = fileDlg.getFile();
        String dir = fileDlg.getDirectory();
        if ((name != null) && (dir != null)) {
          Vector<File> tempVec = new Vector<File>();
          tempVec.addElement(new File(dir + name));
          _mainCtl.updateFileLst(tempVec);
        }
      }
    });
    fileMenuExit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(ABORT);
      }
    });
    fileMenu.add(fileMenuOpen);
    fileMenu.add(fileMenuExit);
    _mainMenuBar.add(fileMenu);

    // モードメニュー
    JMenu modeMenu = new JMenu(_resMgr.getString("menu.mode"));
    JRadioButtonMenuItem modeMenuUnite = new JRadioButtonMenuItem(
        _resMgr.getString("menu.mode.unite"), true);
    JRadioButtonMenuItem modeMenuDivide = new JRadioButtonMenuItem(
        _resMgr.getString("menu.mode.divide"), false);
    // ActionListener
    modeMenuUnite.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        _mainCtl.setMode(MainController.MODE_UNITE);
      }
    });
    modeMenuDivide.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        _mainCtl.setMode(MainController.MODE_DIVIDE);
      }
    });
    modeMenu.add(modeMenuUnite);
    modeMenu.add(modeMenuDivide);
    _modeBtnGrp.add(modeMenuUnite);
    _modeBtnGrp.add(modeMenuDivide);
    _mainMenuBar.add(modeMenu);

    // オプションメニュー
    JMenu optionMenu = new JMenu(_resMgr.getString("menu.option"));
    JMenuItem optionMenuSettings = new JMenuItem(
        _resMgr.getString("menu.option.settings"));
    // ActionListener
    optionMenuSettings.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        OptionDialog optDlg = new OptionDialog(_mainCtl, _mainFrm);
      }
    });
    optionMenu.add(optionMenuSettings);
    _mainMenuBar.add(optionMenu);
  }

  public void updateProgressBar(int cnt) {
    _progressBar.setValue(cnt);
  }

  public void updateProgressLbl(String str) {
    _progressLbl.setText(str);
  }

  public void updateTbl() {
    _fileListTbl.updateUI();
  }

  private ButtonGroup _modeBtnGrp;
  private Vector<Vector<String>> _fileLst;
  private Vector<String> _colNam;
  private ResourceManager _resMgr;
  private MainController _mainCtl;
  private MainFrame _mainFrm;
  private JMenuBar _mainMenuBar;
  private JPanel _mainPnl;
  private JTable _fileListTbl;
  private JScrollPane _scrollPan;
  private JPanel _controlPnl;
  private JLabel _progressLbl;
  private JProgressBar _progressBar;
  private JButton _startBtn;
  private JButton _resetBtn;

}
