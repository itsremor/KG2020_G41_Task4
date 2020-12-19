/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.cs.vsu.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import functions.CosinusFunction;
import functions.DefaultFunction;
import functions.SinusFunction;
import models.*;
import ru.cs.vsu.CameraController;
import ru.cs.vsu.draw.IDrawer;
import ru.cs.vsu.draw.SimpleEdgeDrawer;
import ru.cs.vsu.math.Vector3;
import ru.cs.vsu.screen.ScreenConverter;
import ru.cs.vsu.third.Camera;
import ru.cs.vsu.third.Scene;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    private HelixUpgradedFunc helix;

    public void setHelix(HelixUpgradedFunc helix) {
        scene.getModelsList().clear();
        scene.getModelsList().add(helix);
    }

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

        helix = new HelixUpgradedFunc(3,6,1f,0.1f, 0.5f, 6, false,
                new DefaultFunction(), new DefaultFunction());


        scene.getModelsList().add(helix);

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
