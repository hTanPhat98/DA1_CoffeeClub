 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Help;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Patnight
 */
public class MouseMoveButton {

    List<ForButton> list = null;

    private String kindSelected = "";
    private String textString = "";

    public void setEvent(List<ForButton> list) {
        this.list = list;
        for (ForButton item : list) {
            item.getBtn().addMouseListener(new ButtonEvent(item.getBtn(), item.getBtn().getText()));
        }
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    class ButtonEvent implements MouseListener {

        private final JButton btn;
        private final String kind;

        public ButtonEvent(JButton btn, String kind) {
            this.btn = btn;
            this.kind = kind;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            setBorderButton(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            btn.setBackground(Color.YELLOW);
            kindSelected = kind;
            textString = btn.getToolTipText();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(Color.YELLOW);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equals(kind)) {
                btn.setBackground(Color.GREEN);
            } else {
                btn.setBackground(Color.YELLOW);
            }
        }
    }

    private void setBorderButton(String kind) {
        for (ForButton item : list) {
            if (item.getBtn().getText().equalsIgnoreCase(kind)) {
                item.getBtn().setBackground(Color.red);
            } else {
                item.getBtn().setBackground(Color.GREEN);
            }

        }
    }

}
