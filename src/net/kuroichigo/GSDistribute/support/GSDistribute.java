package net.kuroichigo.GSDistribute.support;

/**
 * @author tsukuyomi
 */
public class GSDistribute {

  public GSDistribute(String[] args) {
    _argv = args;
    init();
  }

  private void init() {
    _mainCtl = new MainController(_argv);
  }

  private String[] _argv;
  private MainController _mainCtl;

}
