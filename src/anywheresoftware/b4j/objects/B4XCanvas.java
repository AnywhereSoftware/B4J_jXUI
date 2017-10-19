package anywheresoftware.b4j.objects;


import java.lang.reflect.Field;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4j.objects.B4XViewWrapper.B4XBitmapWrapper;
import anywheresoftware.b4j.objects.B4XViewWrapper.B4XFont;
import anywheresoftware.b4j.objects.JFX.Colors;

import com.sun.javafx.geom.Arc2D;
import com.sun.javafx.geom.Ellipse2D;
import com.sun.javafx.geom.Path2D;

/**
 * A cross platform canvas.
 */
@ShortName("B4XCanvas")
public class B4XCanvas {
	@Hide
	public CanvasWrapper cvs;
	private B4XRect targetRect;
	/**
	 * Initializes the canvas.
	 *In B4A and B4i the canvas will draw on the passed view.
	 *In B4J the canvas which is a view by itself is added to the passed pane as the first element. 
	 */
	public void Initialize(BA ba, B4XViewWrapper Pane) {
		cvs = new CanvasWrapper();
		cvs.Initialize(ba, "");
		Pane.AddView(cvs.getObject(), 0, 0, Pane.getWidth(), Pane.getHeight());
		cvs.getObject().toBack();
		targetRect = new B4XRect();
		targetRect.Initialize(0, 0, (float)cvs.getWidth(), (float)cvs.getHeight());
	}
	/**
	 * Resizes the canvas.
	 */
	public void Resize(double Width, double Height) {
		cvs.SetSize(Width, Height);
		targetRect.right = (float)Width;
		targetRect.bottom = (float)Height;
	}
	/**
	 * Returns a B4XRect with the same dimensions as the target view.
	 */
	public B4XRect getTargetRect() {
		return targetRect;
	}
	/**
	 * Returns the target view.
	 */
	public B4XViewWrapper getTargetView() {
		return (B4XViewWrapper)AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper(), cvs.getObject().getParent());
	}
	/**
	 * Commits the drawings. Must be called for the drawings to be updated.
	 */
	public void Invalidate() {
		
	}
	/**
	 * Draws a line between x1,y1 to x2,y2.
	 */
	public void DrawLine(float x1, float y1, float x2, float y2, int Color, float StrokeWidth) {
		cvs.DrawLine(x1, y1, x2, y2, Colors.From32Bit(Color), StrokeWidth);
	}
	/**
	 * Returns a copy of the canvas bitmap. In B4A it returns the canvas bitmap itself (not a copy).
	 */
	public B4XBitmapWrapper CreateBitmap() {
		return (B4XBitmapWrapper)AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), cvs.Snapshot2(Colors.Transparent).getObject());
	}
	/**
	 * Draws a rectangle.
	 */
	public void DrawRect(B4XRect Rect, int Color, boolean Filled, float StrokeWidth) {
		cvs.DrawRect(Rect.left, Rect.top, Rect.getWidth(), Rect.getHeight(), Colors.From32Bit(Color), Filled, StrokeWidth);
	}
	/**
	 * Draws a circle.
	 */
	public void DrawCircle(float x, float y, float Radius, int Color, boolean Filled, float StrokeWidth) {
		cvs.DrawCircle(x, y, Radius, Colors.From32Bit(Color), Filled, StrokeWidth);
	}
	/**
	 * Draws a bitmap in the given destination. Use B4XBitmap.Crop to draw part of a bitmap.
	 */
	public void DrawBitmap (Image Bitmap, B4XRect Destination) {
		cvs.DrawImage(Bitmap, Destination.left, Destination.top, Destination.getWidth(), Destination.getHeight());
	}
	/**
	 * Similar to DrawBitmap. Draws a rotated bitmap.
	 */
	public void DrawBitmapRotated(Image Bitmap, B4XRect Destination, float Degrees) {
		cvs.DrawImageRotated(Bitmap, Destination.left, Destination.top, Destination.getWidth(), Destination.getHeight(), Degrees);
	}
	/**
	 * Clips the drawings to a closed path.
	 */
	public void ClipPath(B4XPath Path) throws Exception{
		GraphicsContext gc = cvs.getObject().getGraphicsContext2D();
		gc.save();
		gc.beginPath();
		Field pf = GraphicsContext.class.getDeclaredField("path");
		pf.setAccessible(true);
		Path2D p = (Path2D) pf.get(gc);
		p.setTo(Path.getObject());
		gc.closePath();
		gc.clip();
	}
	/**
	 * Removes a previously set clip region.
	 */
	public void RemoveClip() {
		cvs.RemoveClip();
	}
	/**
	 * Clears the given rectangle. Does not work in B4J with clipped paths.
	 */
	public void ClearRect(B4XRect Rect) {
		cvs.ClearRect(Rect.left, Rect.top, Rect.getWidth(), Rect.getHeight());
	}
	/**
	 * Draws the text.
	 *Text - The text that will be drawn.
	 *x - The origin X coordinate.
	 *y - The origin Y coordinate.
	 *Font - The text font.
	 *Color - Drawing color.
	 *Alignment - Sets the alignment relative to the origin. One of the following values: LEFT, CENTER, RIGHT. 
	 */
	public void DrawText(String Text, double x, double y, B4XFont Font, int Color, TextAlignment Alignment)  {
		cvs.DrawText(Text, x, y, Font.getObject(), Colors.From32Bit(Color), Alignment);
	}
	/**
	 * Similar to DrawText. Rotates the text before it is drawn.
	 */
	public void DrawTextRotated(String Text, double x, double y, B4XFont Font, int Color, TextAlignment Alignment, float Degree)  {
		cvs.DrawTextRotated(Text, x, y, Font.getObject(), Colors.From32Bit(Color), Alignment, Degree);
	}
	
	@ShortName("B4XRect")
	public static class B4XRect {
		private float left, top, right, bottom;
		public void Initialize(float Left, float Top, float Right, float Bottom) {
			left = Left;
			top = Top;
			right = Right;
			bottom = Bottom;
		}
		
		
		public float getLeft() {
			return left;
		}


		public void setLeft(float left) {
			this.left = left;
		}


		public float getTop() {
			return top;
		}


		public void setTop(float top) {
			this.top = top;
		}


		public float getRight() {
			return right;
		}


		public void setRight(float right) {
			this.right = right;
		}


		public float getBottom() {
			return bottom;
		}


		public void setBottom(float bottom) {
			this.bottom = bottom;
		}

		/**
		 * Gets or sets the rectangle width.
		 */
		public float getWidth() {
			return right - left;
		}
		public void setWidth(int w) {
			right = left + w;
		}
		/**
		 * Gets or sets the rectangle height.
		 */
		public float getHeight() {
			return bottom - top;
		}
		public void setHeight(float h) {
			bottom = top + h;
		}
		/**
		 * Returns the horizontal center.
		 */
		public float getCenterX() {return (left + right) * 0.5f;};
		/**
		 * Returns the vertical center.
		 */
		public float getCenterY() {return (top + bottom) * 0.5f;};
	
	}
	@ShortName("B4XPath")
	public static class B4XPath extends AbsObjectWrapper<Path2D> {
		/**
		 * Initializes the path and sets the value of the first point.
		 */
		public B4XPath Initialize(float x, float y) {
			Path2D p = new Path2D();
			p.moveTo(x, y);
			setObject(p);
			return this;
		}
		/**
		 * Initializes the path and sets the current path shape to an oval.
		 *Rect - The oval framing rectangle.
		 */
		public B4XPath InitializeOval(B4XRect Rect) {
			Path2D p = new Path2D();
			p.append(new Ellipse2D(Rect.left, Rect.top, Rect.getWidth(), Rect.getHeight()), true);
			setObject(p);
			return this;
		}
		/**
		 * Initializes the path and sets the current path shape to an arc. 
		 * x / y - Arc center.
		 * Radius - Arc radius.
		 * StartingAngle - The starting angle. 0 equals to hour 3.
		 * SweepAngle - Sweep angle. Positive = clockwise.
		 */
		public B4XPath InitializeArc(float x, float y, float Radius, float StartingAngle, float SweepAngle) {
			Path2D p = new Path2D();
			p.append(new Arc2D(x - Radius, y - Radius, 2 * Radius, 2 * Radius, -StartingAngle, -SweepAngle, Arc2D.PIE), true);
			setObject(p);
			return this;
		}
		
		/**
		 * Adds a line from the last point to the specified point.
		 */
		public B4XPath LineTo(float x, float y) {
			getObject().lineTo(x, y);
			return this;
		}
	}
}
