package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_song{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("button1").vw.setTop((int)((25d / 100 * height) - (views.get("button1").vw.getHeight())));
views.get("button1").vw.setLeft((int)((15d / 100 * height)));
views.get("btnpisang").vw.setLeft((int)((40d / 100 * height)));
views.get("btnpisang").vw.setTop((int)((60d / 100 * height) - (views.get("btnpisang").vw.getHeight())));
views.get("btnayam").vw.setLeft((int)((70d / 100 * height)));
views.get("btnayam").vw.setTop((int)((60d / 100 * height) - (views.get("btnayam").vw.getHeight())));
views.get("btnquiz").vw.setLeft((int)((100d / 100 * height)));
views.get("btnquiz").vw.setTop((int)((60d / 100 * height) - (views.get("btnquiz").vw.getHeight())));
views.get("label1").vw.setLeft((int)((50d / 100 * height)));
views.get("label1").vw.setTop((int)((30d / 100 * height) - (views.get("label1").vw.getHeight())));

}
}