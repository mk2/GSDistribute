package com.Megaloworks.GSDistribute.support;

/**
 * @author tsukuyomi 起動クラス。
 */
public class GSDistribute {

	public GSDistribute(String[] args) {
		_argv = args;
	}

	public static void main(String[] args) {
		GSDistribute gsd = new GSDistribute(args);
		gsd.init();
	}

	private void init() {
		_mainCtl = new MainController(_argv);
	}

	private String[] _argv;
	private MainController _mainCtl;

}
