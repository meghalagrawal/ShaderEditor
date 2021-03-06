package de.markusfisch.android.shadereditor.widget;

import de.markusfisch.android.shadereditor.view.SystemBarMetrics;
import de.markusfisch.android.shadereditor.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

public class CropImageView extends ScalingImageView {
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final int padding;
	private final int toolAndStatusbarHeight;

	public CropImageView(Context context, AttributeSet attr) {
		super(context, attr);

		paint.setColor(ContextCompat.getColor(context, R.color.crop_bound));
		paint.setStyle(Paint.Style.STROKE);

		padding = Math.round(
				context.getResources().getDisplayMetrics().density * 24f);

		toolAndStatusbarHeight =
				SystemBarMetrics.getStatusAndToolBarHeight(context);

		setScaleType(ScalingImageView.ScaleType.CENTER_CROP);
	}

	@Override
	protected void onLayout(
			boolean changed,
			int left,
			int top,
			int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (right - left > bottom - top) {
			top += toolAndStatusbarHeight;
		}

		int width = right - left;
		int height = bottom - top;
		int size = width < height ?
				width - padding * 2 :
				height - padding * 2;
		int hpad = (width - size) / 2;
		int vpad = (height - size) / 2;

		setBounds(
				(float) left + hpad,
				(float) top + vpad,
				(float) right - hpad,
				(float) bottom - vpad);

		center(getBounds());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(getBounds(), paint);
	}
}
