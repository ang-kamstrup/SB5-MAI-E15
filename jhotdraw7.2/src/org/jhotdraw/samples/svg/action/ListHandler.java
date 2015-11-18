package org.jhotdraw.samples.svg.action;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.jhotdraw.draw.Figure;

public class ListHandler {
    public static DefaultListModel createModel() {
        DefaultListModel model = new DefaultListModel();
        return model;
    }

    static class ImageTransferHandler extends TransferHandler {

        private static final DataFlavor DATA_FLAVOUR = new DataFlavor(Object.class, "Objects");

        private final JList previewList;
        private boolean inDrag;

        ImageTransferHandler(JList previewList) {
            //Clicked outside dialog
            this.previewList = previewList;
        }

        @Override
        public int getSourceActions(JComponent c) {
            //clicked or dragged an item....
            ListModeller.selectFiguresFromList();
            return TransferHandler.MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            inDrag = true;
            return new Transferable() {
                @Override
                public DataFlavor[] getTransferDataFlavors() {
                    return new DataFlavor[] {DATA_FLAVOUR};
                }

                @Override
                public boolean isDataFlavorSupported(DataFlavor flavor) {
                    return flavor.equals(DATA_FLAVOUR);
                }

                @Override
                public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                    return previewList.getSelectedValue();
                }
            };
        }

        @Override
        public boolean canImport(TransferSupport support) {
            if (!inDrag || !support.isDataFlavorSupported(DATA_FLAVOUR)) {
                return false;
            }

            JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
            if (dl.getIndex() == -1) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            Transferable transferable = support.getTransferable();
            try {
                Object draggedImage = transferable.getTransferData(DATA_FLAVOUR);

                JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
                DefaultListModel model = (DefaultListModel)previewList.getModel();
                int dropIndex = dl.getIndex();
                if (model.indexOf(draggedImage) < dropIndex) {
                    dropIndex--;
                }
                model.removeElement(draggedImage);
                model.add(dropIndex, draggedImage);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            ListModeller.applyToDrawing();
            super.exportDone(source, data, action);
            inDrag = false;
        }
    }
}