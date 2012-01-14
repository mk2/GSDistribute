package net.kuroichigo.GSDistribute.support;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DnDTransferHandler implements DropTargetListener {

  private MainController _mainCtl;

  private Logger logger;

  public DnDTransferHandler(MainController mainCtl) {
    logger = Logger.getLogger(DnDTransferHandler.class.getName());
    logger.setLevel(Level.INFO);
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
        if (trans.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
          List<File> tempLst = (List<File>) trans
              .getTransferData(DataFlavor.javaFileListFlavor);
          for (File file : tempLst) {
            fileLst.add(file);
          }
        } else if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
          String str = ((String) trans.getTransferData(DataFlavor.stringFlavor))
              .trim();
          logger.info("Drop: " + str);
          String sep = System.getProperty("line.seperator");
          if (str.contains(sep)) {
            String[] strs = str.split(sep);
            for (String filename : strs) {
              fileLst.add(new File(new URI(filename.trim())));
            }
          } else {
            fileLst.add(new File(new URI(str)));
          }
        }
      } catch (UnsupportedFlavorException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (URISyntaxException e) {
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
    if (trans.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
        || trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
      return true;
    } else {
      return false;
    }
  }

}
