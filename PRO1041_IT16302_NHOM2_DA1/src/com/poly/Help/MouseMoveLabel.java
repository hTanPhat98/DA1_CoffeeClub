/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.Help;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author Patnight
 */
public class MouseMoveLabel {
    List<ForLabel> list = null;

    public void setEvent(List<ForLabel> list) {
        this.list = list;
        for (ForLabel item : list) {
            item.getLbl().addMouseListener(new LabelEvent(item.getLbl()));
        }
    }

    class LabelEvent implements MouseListener {

        private JLabel lbl;

        public LabelEvent(JLabel lbl) {
            this.lbl = lbl;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
}
