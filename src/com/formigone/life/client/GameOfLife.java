package com.formigone.life.client;

import java.util.Date;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.animation.client.AnimationScheduler.AnimationHandle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class GameOfLife implements EntryPoint {

	private Grid grid;
	private Label label;
	private AnimationHandle raf;
	private Date updateTime;
	private Date now;
	private long fps;
	
	@Override
	public void onModuleLoad() {
		
		grid = new Grid(40, 40, 10, 10);
		grid.seedGrid();
		
		label = new Label();
		label.getElement().setInnerHTML(grid.getGrid());
		
		updateTime = new Date();
		fps = 1000 / 5;

		RootPanel.get().add(label);
		
		raf = AnimationScheduler.get().requestAnimationFrame(new AnimationCallback() {
			
			@Override
			public void execute(double timestamp) {
				now = new Date();

				if (now.getTime() - updateTime.getTime() > fps) {
					updateGeneration();
					updateTime = now;
				}

				AnimationScheduler.get().requestAnimationFrame(this);
			}
		});
	}

	public void updateGeneration() {
		grid.nextGeneration();
		label.getElement().setInnerHTML(grid.getGrid());
	}
}
