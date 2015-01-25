package com.whoshuu.artbox.system;

public interface TouchListener {
    public void onDown(int xd, int yd);
    public void onUp(int xu, int yu);
    public void onMove(int x, int y);
}
