package net.kuroichigo.GSDistribute.support;

import java.util.ResourceBundle;

/**
 * @author tsukuyomi リソースマネージャークラス。面倒なのでシングルトンにする。
 */
public class ResourceManager {

	private ResourceManager() {
		_resBdl = ResourceBundle.getBundle("resource.resource");
	}

	public static ResourceManager getResourceManager() {
		if (_resMgr == null) {
			_resMgr = new ResourceManager();
		}
		return _resMgr;
	}

	public String getString(String key) {
		String str;
		try {
			str = _resBdl.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			str = key;
		}
		return str;
	}

	private static ResourceManager _resMgr;
	private ResourceBundle _resBdl;

}
