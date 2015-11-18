package org.jhotdraw.samples.svg.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.SelectionTool;

public class ListModeller extends JPanel {

    static JList list;
    static DefaultListModel listInView;
    
    static Drawing drawing;
    static DrawingView sView;
    static List figuresInDrawing = new ArrayList();
    static List selectedFiguresInDrawing = new ArrayList();

    public ListModeller(DrawingView view) {
        setLayout(new BorderLayout());
        listInView = new DefaultListModel();
        list = new JList(listInView);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
        list.setTransferHandler(new ListHandler.ImageTransferHandler(list));

        JScrollPane pane = new JScrollPane(list);
        JButton hideButton = new JButton("Hide (add)");
        JButton showButton = new JButton("Show");
        JButton removeButton = new JButton("Remove");

        sView = view;
        drawing = sView.getDrawing();
        
        figuresInDrawing = listToArrayList(drawing.getFiguresFrontToBack());
        setListFromDrawingToView();
        
        hideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Figure sel = (Figure)list.getSelectedValue();
                //TODO: hide element
            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Show hidden element
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Figure sel = (Figure)list.getSelectedValue();
                drawing.remove(sel);
                listInView.removeElement(sel);
            }
        });

        
        //---- ACTION LISTENERS ----

        add(pane, BorderLayout.NORTH);
        //add(hideButton, BorderLayout.WEST);
        //add(showButton, BorderLayout.CENTER);
        add(removeButton, BorderLayout.EAST);
    }

    public static ArrayList listToArrayList(List originalList) {
        ArrayList newList = new ArrayList();

        for (int i = 0; i < originalList.size(); i++) {
            newList.add((Figure) originalList.get(i));
        }

        return newList;
    }
    
    public static void setListFromDrawingToView(){
        for (int i = 0; i < figuresInDrawing.size(); i++) {
            //model.addElement(figuresInDrawing.get(i).toString());
            listInView.addElement(figuresInDrawing.get(i));
            //if(selectedFigureNumbers.contains(i))
                
        }
    }
    
    public static void setListFromViewToDrawing(){
        for (int i = 0; i < listInView.size(); i++) {
            //model.addElement(figuresInDrawing.get(i).toString());
            drawing.add((Figure)listInView.get(i));
            
            //figuresInDrawing.add(listInView.get(i));
            //if(selectedFigureNumbers.contains(i))
                
        }
    }
    
    
    public static void updateSelectionToList(){ //ONLY PUTS IT IN, DOESN'T SELECT IT!!
        selectedFiguresInDrawing = Arrays.asList(sView.getSelectedFigures().toArray());
        
        System.out.println("Items in drawing, front to back");
        for (int j = 0; j < figuresInDrawing.size(); j++) {
            String selected = "-";
            if (selectedFiguresInDrawing.contains(figuresInDrawing.get(j))) {
                selected = "+";
            }
            System.out.println(selected + j + ": " + figuresInDrawing.get(j).toString());
        }
    }
    
    public static void updateSelectionFromList(){
        selectedFiguresInDrawing = Arrays.asList(list.getSelectedValues());
    }
    
    public static void selectFiguresFromList(){
        updateSelectionFromList();
        sView.clearSelection();
        sView.addToSelection(selectedFiguresInDrawing);//Collection of Figures
    }
    
    public static void applyToDrawing(){
        Object selected = list.getSelectedValue();
        for (int i = 0; i < listInView.size(); i++) {
            drawing.sendToBack((Figure)listInView.get(i));
        }
    }
}