package com.Megaloworks.GSDistribute.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Megaloworks.GSDistribute.support.MainController;
import com.Megaloworks.GSDistribute.support.ResourceManager;

/**
 * @author tsukuyomi オプション設定用のダイアログ。現在は分割する際のファイルサイズのみを指定出来る。<br/>
 *         モーダルダイアログ。
 */
@SuppressWarnings("serial")
public class OptionDialog extends JDialog {

	public OptionDialog(MainController mainCtl, MainFrame mainFrm) {
		super(mainFrm, true);
		_resMgr = ResourceManager.getResourceManager();
		_mainCtl = mainCtl;
		_mainFrm = mainFrm;
		buildUI();
		setTitle(_resMgr.getString("optiondialog.title"));
		setVisible(true);
	}

	private void buildUI() {
		setupComponents();
		add(_textLbl, BorderLayout.WEST);
		add(_textFld, BorderLayout.CENTER);
		JPanel btnPnl = new JPanel(new BorderLayout());
		add(btnPnl, BorderLayout.SOUTH);
		btnPnl.add(_okBtn, BorderLayout.CENTER);
		btnPnl.add(_cancelBtn, BorderLayout.EAST);
		_okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int size = 0;
				try {
					size = Integer.parseInt(_textFld.getText());
					_mainCtl.setFileSize(size);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				OptionDialog.this.dispose();
			}
		});
		_cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OptionDialog.this.dispose();
			}
		});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(_mainFrm.getBounds().x + 10, _mainFrm.getBounds().y + 10,
				Integer.parseInt(_resMgr.getString("optiondialog.size.width")),
				Integer.parseInt(_resMgr.getString("optiondialog.size.height")));
	}

	private void setupComponents() {
		_textLbl = new JLabel(_resMgr.getString("optiondialog.text.label"));
		_textFld = new JTextField(String.valueOf(_mainCtl.getFileSize()));
		_okBtn = new JButton(_resMgr.getString("button.ok"));
		_cancelBtn = new JButton(_resMgr.getString("button.cancel"));
	}

	private MainController _mainCtl;
	private MainFrame _mainFrm;
	private ResourceManager _resMgr;
	private JLabel _textLbl;
	private JTextField _textFld;
	private JButton _okBtn;
	private JButton _cancelBtn;

}
