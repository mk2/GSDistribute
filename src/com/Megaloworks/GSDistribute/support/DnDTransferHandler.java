package com.Megaloworks.GSDistribute.support;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class DnDTransferHandler implements DropTargetListener {

	public DnDTransferHandler(MainController mainCtl) {
		_mainCtl = mainCtl;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent dtde) {
		Transferable trans = dtde.getTransferable();
		if (isDnD(trans)) {
			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			Vector<File> fileLst = new Vector<File>();
			try {
				List<File> tempLst = (List<File>) trans
						.getTransferData(DataFlavor.javaFileListFlavor);
				for (File file : tempLst) {
					fileLst.add(file);
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			_mainCtl.updateFileLst(fileLst);
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	private boolean isDnD(Transferable trans) {
		if (trans.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return true;
		} else {
			return false;
		}
	}

	private MainController _mainCtl;

}
