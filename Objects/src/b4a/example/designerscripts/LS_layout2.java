package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layout2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btnalphabet").vw.setTop((int)((97d / 100 * height) - (views.get("btnalphabet").vw.getHeight())));
views.get("btnfun").vw.setTop((int)((97d / 100 * height) - (views.get("btnfun").vw.getHeight())));
views.get("btnnumber").vw.setTop((int)((97d / 100 * height) - (views.get("btnnumber").vw.getHeight())));
views.get("btnabout").vw.setLeft((int)((5d / 100 * height)));
views.get("imagecatogaries").vw.setTop((int)((90d / 100 * height) - (views.get("imagecatogaries").vw.getHeight())));
views.get("btnalphabet").vw.setLeft((int)((views.get("btnfun").vw.getLeft())-(5d * scale) - (views.get("btnalphabet").vw.getWidth())));
views.get("btnfun").vw.setLeft((int)((views.get("btnnumber").vw.getLeft())-(15d * scale) - (views.get("btnfun").vw.getWidth())));
views.get("ireatbeans1").vw.setTop((int)((15d / 100 * height) - (views.get("ireatbeans1").vw.getHeight())));
views.get("ireatbeans1").vw.setLeft((int)((55d / 100 * height)));

}
}