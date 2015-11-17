/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.util.Locale;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author johnny
 */
public class SprayCanAction extends AbstractSelectedAction{
    private ResourceBundleUtil rbUtil = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.labels", Locale.getDefault());
    
    public SprayCanAction(DrawingEditor editor){
        super(editor);
        rbUtil.configureAction(this, "edit.sprayCan");
        setEnabled(true);
        
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("SprayCan button pressed");
        setEnabled(true);
        
    }
//    
//    @Override
//    public void mousePressed(MouseEvent evt) {
//
//        if (DEBUG) {
//            System.out.println("BezierTool.mousePressed " + evt);
//        }
//        if (mouseLocation != null) {
//            Rectangle r = new Rectangle(mouseLocation);
//            r.add(evt.getPoint());
//            r.grow(1, 1);
//            fireAreaInvalidated(r);
//        }
//        mouseLocation = evt.getPoint();
//        super.mousePressed(evt);
//
//
//        if (createdFigure != null && creationView != getView()) {
//            finishCreation(createdFigure, creationView);
//            createdFigure = null;
//        }
//
//
//        if (createdFigure == null) {
//            creationView = getView();
//            creationView.clearSelection();
//            finishWhenMouseReleased = null;
//
//            createdFigure = createFigure();
//            createdFigure.addNode(new BezierPath.Node(
//                    creationView.getConstrainer().constrainPoint(
//                    creationView.viewToDrawing(anchor))));
//            getDrawing().add(createdFigure);
//        } else {
//            if (evt.getClickCount() == 1) {
//                addPointToFigure(creationView.getConstrainer().constrainPoint(
//                        creationView.viewToDrawing(anchor)));
//            }
//        }
//        nodeCountBeforeDrag = createdFigure.getNodeCount();
//    }
//    
//        protected Figure getCreatedFigure() {
//        return createdFigure;
//    }
//
//    protected Figure getAddedFigure() {
//        return createdFigure;
//    }
//    
//    @Override
//    public void mouseClicked(MouseEvent evt) {
//        if (createdFigure != null) {
//            switch (evt.getClickCount()) {
//                case 1:
//                    if (createdFigure.getNodeCount() > 2) {
//                        Rectangle r = new Rectangle(getView().drawingToView(createdFigure.getStartPoint()));
//                        r.grow(2, 2);
//                        if (r.contains(evt.getX(), evt.getY())) {
//                            createdFigure.setClosed(true);
//
//                            finishCreation(createdFigure, creationView);
//                            createdFigure = null;
//                            if (isToolDoneAfterCreation) {
//                                fireToolDone();
//                            }
//                        }
//                    }
//                    break;
//                case 2:
//                    finishWhenMouseReleased = null;
//
//                    finishCreation(createdFigure, creationView);
//                    createdFigure = null;
//                    break;
//            }
//        }
//    }
//     public void mouseReleased(MouseEvent evt) {
//        if (DEBUG) {
//            System.out.println("BezierTool.mouseReleased " + evt);
//        }
//        isWorking = false;
//        if (createdFigure.getNodeCount() > nodeCountBeforeDrag + 1) {
//            createdFigure.willChange();
//            BezierPath figurePath = createdFigure.getBezierPath();
//            BezierPath digitizedPath = new BezierPath();
//            for (int i = nodeCountBeforeDrag - 1, n = figurePath.size(); i < n; i++) {
//                digitizedPath.add(figurePath.get(nodeCountBeforeDrag - 1));
//                figurePath.remove(nodeCountBeforeDrag - 1);
//            }
//            BezierPath fittedPath = calculateFittedCurve(digitizedPath);
//            //figurePath.addAll(digitizedPath);
//            figurePath.addAll(fittedPath);
//            createdFigure.setBezierPath(figurePath);
//            createdFigure.changed();
//            nodeCountBeforeDrag = createdFigure.getNodeCount();
//        }
//
//        if (finishWhenMouseReleased == Boolean.TRUE) {
//            if (createdFigure.getNodeCount() > 1) {
//                finishCreation(createdFigure, creationView);
//                createdFigure = null;
//                finishWhenMouseReleased = null;
//                return;
//            }
//        } else if (finishWhenMouseReleased == null) {
//            finishWhenMouseReleased = Boolean.FALSE;
//        }
//
//        // repaint dotted line
//        Rectangle r = new Rectangle(anchor);
//        r.add(mouseLocation);
//        r.add(evt.getPoint());
//        r.grow(1, 1);
//        fireAreaInvalidated(r);
//        anchor.x = evt.getX();
//        anchor.y = evt.getY();
//        mouseLocation = evt.getPoint();
//    }
//       public void mouseDragged(MouseEvent evt) {
//        if (finishWhenMouseReleased == null) {
//            finishWhenMouseReleased = Boolean.TRUE;
//        }
//        int x = evt.getX();
//        int y = evt.getY();
//        addPointToFigure(getView().viewToDrawing(new Point(x, y)));
//    }
//
//    @Override
//    public void draw(Graphics2D g) {
//        if (createdFigure != null && //
//                anchor != null && //
//                mouseLocation != null &&//
//                getView() == creationView) {
//            g.setColor(Color.BLACK);
//            g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[]{1f, 5f}, 0f));
//            g.drawLine(anchor.x, anchor.y, mouseLocation.x, mouseLocation.y);
//            if (!isWorking && createdFigure.isClosed() && createdFigure.getNodeCount() > 1) {
//                Point p = creationView.drawingToView(createdFigure.getStartPoint());
//                g.drawLine(mouseLocation.x, mouseLocation.y, p.x, p.y);
//            }
//        }
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent evt) {
//        if (createdFigure != null && anchor != null && mouseLocation != null) {
//            if (evt.getSource() == creationView) {
//                Rectangle r = new Rectangle(anchor);
//                r.add(mouseLocation);
//                r.add(evt.getPoint());
//                if (createdFigure.isClosed() && createdFigure.getNodeCount() > 0) {
//                    r.add(creationView.drawingToView(createdFigure.getStartPoint()));
//                }
//                r.grow(1, 1);
//                fireAreaInvalidated(r);
//                mouseLocation = evt.getPoint();
//            }
//        }
//    }

    
}
