/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.cs.vsu.draw;

import java.util.Collection;
import ru.cs.vsu.third.PolyLine3D;

/**
 * Интерфейс, описывающий в общем виде процесс рисования полилинии
 * @author Alexey
 */
public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    public void clear(int color);
    
    /**
     * Рисует все полилинии
     * @param polyline набор рисуемых полилиний.
     */
    public void draw(Collection<PolyLine3D> polyline);
}
