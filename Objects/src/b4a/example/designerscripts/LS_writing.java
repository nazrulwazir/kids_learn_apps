package b4a.example.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_writing{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("button1").vw.setTop((int)((15d / 100 * height) - (views.get("button1").vw.getHeight())));
views.get("button1").vw.setLeft((int)((5d / 100 * height)));
views.get("fv1").vw.setTop((int)((48d / 100 * height) - (views.get("fv1").vw.getHeight())));
views.get("fv1").vw.setLeft((int)((115d / 100 * height)));
views.get("fv1").vw.setWidth((int)((50d / 100 * height)));
views.get("fv1").vw.setHeight((int)((60d / 100 * height)));
views.get("btn_clear").vw.setTop((int)((35d / 100 * height) - (views.get("btn_clear").vw.getHeight())));
views.get("btn_clear").vw.setLeft((int)((100d / 100 * height)));
views.get("b3").vw.setTop((int)((22d / 100 * height) - (views.get("b3").vw.getHeight())));
views.get("b3").vw.setLeft((int)((15d / 100 * height)));
views.get("b3").vw.setWidth((int)((20d / 100 * height)));
views.get("b3").vw.setHeight((int)((20d / 100 * height)));
views.get("btnb").vw.setTop((int)((22d / 100 * height) - (views.get("btnb").vw.getHeight())));
views.get("btnb").vw.setLeft((int)((28d / 100 * height)));
views.get("btnb").vw.setWidth((int)((20d / 100 * height)));
views.get("btnb").vw.setHeight((int)((20d / 100 * height)));
views.get("btnc").vw.setTop((int)((22d / 100 * height) - (views.get("btnc").vw.getHeight())));
views.get("btnc").vw.setLeft((int)((41d / 100 * height)));
views.get("btnc").vw.setWidth((int)((20d / 100 * height)));
views.get("btnc").vw.setHeight((int)((20d / 100 * height)));
views.get("btnd").vw.setTop((int)((22d / 100 * height) - (views.get("btnd").vw.getHeight())));
views.get("btnd").vw.setLeft((int)((54d / 100 * height)));
views.get("btnd").vw.setWidth((int)((20d / 100 * height)));
views.get("btnd").vw.setHeight((int)((20d / 100 * height)));
views.get("btne").vw.setTop((int)((22d / 100 * height) - (views.get("btne").vw.getHeight())));
views.get("btne").vw.setLeft((int)((67d / 100 * height)));
views.get("btne").vw.setWidth((int)((20d / 100 * height)));
views.get("btne").vw.setHeight((int)((20d / 100 * height)));
views.get("btnf").vw.setTop((int)((22d / 100 * height) - (views.get("btnf").vw.getHeight())));
views.get("btnf").vw.setLeft((int)((80d / 100 * height)));
views.get("btnf").vw.setWidth((int)((20d / 100 * height)));
views.get("btnf").vw.setHeight((int)((20d / 100 * height)));
views.get("btng").vw.setTop((int)((38d / 100 * height) - (views.get("btng").vw.getHeight())));
views.get("btng").vw.setLeft((int)((15d / 100 * height)));
views.get("btng").vw.setWidth((int)((20d / 100 * height)));
views.get("btng").vw.setHeight((int)((20d / 100 * height)));
views.get("btnh").vw.setTop((int)((38d / 100 * height) - (views.get("btnh").vw.getHeight())));
views.get("btnh").vw.setLeft((int)((28d / 100 * height)));
views.get("btnh").vw.setWidth((int)((20d / 100 * height)));
views.get("btnh").vw.setHeight((int)((20d / 100 * height)));
views.get("btni").vw.setTop((int)((38d / 100 * height) - (views.get("btni").vw.getHeight())));
views.get("btni").vw.setLeft((int)((41d / 100 * height)));
views.get("btni").vw.setWidth((int)((20d / 100 * height)));
views.get("btni").vw.setHeight((int)((20d / 100 * height)));
views.get("btnj").vw.setTop((int)((38d / 100 * height) - (views.get("btnj").vw.getHeight())));
views.get("btnj").vw.setLeft((int)((54d / 100 * height)));
views.get("btnj").vw.setWidth((int)((20d / 100 * height)));
views.get("btnj").vw.setHeight((int)((20d / 100 * height)));
views.get("btnk").vw.setTop((int)((38d / 100 * height) - (views.get("btnk").vw.getHeight())));
views.get("btnk").vw.setLeft((int)((67d / 100 * height)));
views.get("btnk").vw.setWidth((int)((20d / 100 * height)));
views.get("btnk").vw.setHeight((int)((20d / 100 * height)));
views.get("btnl").vw.setTop((int)((38d / 100 * height) - (views.get("btnl").vw.getHeight())));
views.get("btnl").vw.setLeft((int)((80d / 100 * height)));
views.get("btnl").vw.setWidth((int)((20d / 100 * height)));
views.get("btnl").vw.setHeight((int)((20d / 100 * height)));
views.get("btnm").vw.setTop((int)((54d / 100 * height) - (views.get("btnm").vw.getHeight())));
views.get("btnm").vw.setLeft((int)((15d / 100 * height)));
views.get("btnm").vw.setWidth((int)((20d / 100 * height)));
views.get("btnm").vw.setHeight((int)((20d / 100 * height)));
views.get("btnn").vw.setTop((int)((54d / 100 * height) - (views.get("btnn").vw.getHeight())));
views.get("btnn").vw.setLeft((int)((28d / 100 * height)));
views.get("btnn").vw.setWidth((int)((20d / 100 * height)));
views.get("btnn").vw.setHeight((int)((20d / 100 * height)));
views.get("btno").vw.setTop((int)((54d / 100 * height) - (views.get("btno").vw.getHeight())));
views.get("btno").vw.setLeft((int)((41d / 100 * height)));
views.get("btno").vw.setWidth((int)((20d / 100 * height)));
views.get("btno").vw.setHeight((int)((20d / 100 * height)));
views.get("btnp").vw.setTop((int)((54d / 100 * height) - (views.get("btnp").vw.getHeight())));
views.get("btnp").vw.setLeft((int)((54d / 100 * height)));
views.get("btnp").vw.setWidth((int)((20d / 100 * height)));
views.get("btnp").vw.setHeight((int)((20d / 100 * height)));
views.get("btnq").vw.setTop((int)((54d / 100 * height) - (views.get("btnq").vw.getHeight())));
views.get("btnq").vw.setLeft((int)((67d / 100 * height)));
views.get("btnq").vw.setWidth((int)((20d / 100 * height)));
views.get("btnq").vw.setHeight((int)((20d / 100 * height)));
views.get("btnr").vw.setTop((int)((54d / 100 * height) - (views.get("btnr").vw.getHeight())));
views.get("btnr").vw.setLeft((int)((80d / 100 * height)));
views.get("btnr").vw.setWidth((int)((20d / 100 * height)));
views.get("btnr").vw.setHeight((int)((20d / 100 * height)));
views.get("btns").vw.setTop((int)((70d / 100 * height) - (views.get("btns").vw.getHeight())));
views.get("btns").vw.setLeft((int)((15d / 100 * height)));
views.get("btns").vw.setWidth((int)((20d / 100 * height)));
views.get("btns").vw.setHeight((int)((20d / 100 * height)));
views.get("btnt").vw.setTop((int)((70d / 100 * height) - (views.get("btnt").vw.getHeight())));
views.get("btnt").vw.setLeft((int)((28d / 100 * height)));
views.get("btnt").vw.setWidth((int)((20d / 100 * height)));
views.get("btnt").vw.setHeight((int)((20d / 100 * height)));
views.get("btnu").vw.setTop((int)((70d / 100 * height) - (views.get("btnu").vw.getHeight())));
views.get("btnu").vw.setLeft((int)((41d / 100 * height)));
views.get("btnu").vw.setWidth((int)((20d / 100 * height)));
views.get("btnu").vw.setHeight((int)((20d / 100 * height)));
views.get("btnv").vw.setTop((int)((70d / 100 * height) - (views.get("btnv").vw.getHeight())));
views.get("btnv").vw.setLeft((int)((54d / 100 * height)));
views.get("btnv").vw.setWidth((int)((20d / 100 * height)));
views.get("btnv").vw.setHeight((int)((20d / 100 * height)));
views.get("btnw").vw.setTop((int)((70d / 100 * height) - (views.get("btnw").vw.getHeight())));
views.get("btnw").vw.setLeft((int)((67d / 100 * height)));
views.get("btnw").vw.setWidth((int)((20d / 100 * height)));
views.get("btnw").vw.setHeight((int)((20d / 100 * height)));
views.get("btnx").vw.setTop((int)((70d / 100 * height) - (views.get("btnx").vw.getHeight())));
views.get("btnx").vw.setLeft((int)((80d / 100 * height)));
views.get("btnx").vw.setWidth((int)((20d / 100 * height)));
views.get("btnx").vw.setHeight((int)((20d / 100 * height)));
views.get("btny").vw.setTop((int)((86d / 100 * height) - (views.get("btny").vw.getHeight())));
views.get("btny").vw.setLeft((int)((41d / 100 * height)));
views.get("btny").vw.setWidth((int)((20d / 100 * height)));
views.get("btny").vw.setHeight((int)((20d / 100 * height)));
views.get("btnz").vw.setTop((int)((86d / 100 * height) - (views.get("btnz").vw.getHeight())));
views.get("btnz").vw.setLeft((int)((54d / 100 * height)));
views.get("btnz").vw.setWidth((int)((20d / 100 * height)));
views.get("btnz").vw.setHeight((int)((20d / 100 * height)));
views.get("i26").vw.setTop((int)((55d / 100 * height) - (views.get("i26").vw.getHeight())));
views.get("i26").vw.setLeft((int)((115d / 100 * height)));
views.get("i26").vw.setWidth((int)((50d / 100 * height)));
views.get("i26").vw.setHeight((int)((50d / 100 * height)));
views.get("i25").vw.setTop((int)((55d / 100 * height) - (views.get("i25").vw.getHeight())));
views.get("i25").vw.setLeft((int)((115d / 100 * height)));
views.get("i25").vw.setWidth((int)((50d / 100 * height)));
views.get("i25").vw.setHeight((int)((50d / 100 * height)));
views.get("i24").vw.setTop((int)((55d / 100 * height) - (views.get("i24").vw.getHeight())));
views.get("i24").vw.setLeft((int)((115d / 100 * height)));
views.get("i24").vw.setWidth((int)((50d / 100 * height)));
views.get("i24").vw.setHeight((int)((50d / 100 * height)));
views.get("i23").vw.setTop((int)((55d / 100 * height) - (views.get("i23").vw.getHeight())));
views.get("i23").vw.setLeft((int)((115d / 100 * height)));
views.get("i23").vw.setWidth((int)((50d / 100 * height)));
views.get("i23").vw.setHeight((int)((50d / 100 * height)));
views.get("i22").vw.setTop((int)((55d / 100 * height) - (views.get("i22").vw.getHeight())));
views.get("i22").vw.setLeft((int)((115d / 100 * height)));
views.get("i22").vw.setWidth((int)((50d / 100 * height)));
views.get("i22").vw.setHeight((int)((50d / 100 * height)));
views.get("i21").vw.setTop((int)((55d / 100 * height) - (views.get("i21").vw.getHeight())));
views.get("i21").vw.setLeft((int)((115d / 100 * height)));
views.get("i21").vw.setWidth((int)((50d / 100 * height)));
views.get("i21").vw.setHeight((int)((50d / 100 * height)));
views.get("i20").vw.setTop((int)((55d / 100 * height) - (views.get("i20").vw.getHeight())));
//BA.debugLineNum = 192;BA.debugLine="i20.Left=115%y"[writing/General script]
views.get("i20").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 193;BA.debugLine="i20.Width=50%y"[writing/General script]
views.get("i20").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 194;BA.debugLine="i20.Height=50%y"[writing/General script]
views.get("i20").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 197;BA.debugLine="i19.Bottom=55%y"[writing/General script]
views.get("i19").vw.setTop((int)((55d / 100 * height) - (views.get("i19").vw.getHeight())));
//BA.debugLineNum = 198;BA.debugLine="i19.Left=115%y"[writing/General script]
views.get("i19").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 199;BA.debugLine="i19.Width=50%y"[writing/General script]
views.get("i19").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 200;BA.debugLine="i19.Height=50%y"[writing/General script]
views.get("i19").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 203;BA.debugLine="i18.Bottom=55%y"[writing/General script]
views.get("i18").vw.setTop((int)((55d / 100 * height) - (views.get("i18").vw.getHeight())));
//BA.debugLineNum = 204;BA.debugLine="i18.Left=115%y"[writing/General script]
views.get("i18").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 205;BA.debugLine="i18.Width=50%y"[writing/General script]
views.get("i18").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 206;BA.debugLine="i18.Height=50%y"[writing/General script]
views.get("i18").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 208;BA.debugLine="i17.Bottom=55%y"[writing/General script]
views.get("i17").vw.setTop((int)((55d / 100 * height) - (views.get("i17").vw.getHeight())));
//BA.debugLineNum = 209;BA.debugLine="i17.Left=115%y"[writing/General script]
views.get("i17").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 210;BA.debugLine="i17.Width=50%y"[writing/General script]
views.get("i17").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 211;BA.debugLine="i17.Height=50%y"[writing/General script]
views.get("i17").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 213;BA.debugLine="i16.Bottom=55%y"[writing/General script]
views.get("i16").vw.setTop((int)((55d / 100 * height) - (views.get("i16").vw.getHeight())));
//BA.debugLineNum = 214;BA.debugLine="i16.Left=115%y"[writing/General script]
views.get("i16").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 215;BA.debugLine="i16.Width=50%y"[writing/General script]
views.get("i16").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 216;BA.debugLine="i16.Height=50%y"[writing/General script]
views.get("i16").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 218;BA.debugLine="i15.Bottom=55%y"[writing/General script]
views.get("i15").vw.setTop((int)((55d / 100 * height) - (views.get("i15").vw.getHeight())));
//BA.debugLineNum = 219;BA.debugLine="i15.Left=115%y"[writing/General script]
views.get("i15").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 220;BA.debugLine="i15.Width=50%y"[writing/General script]
views.get("i15").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 221;BA.debugLine="i15.Height=50%y"[writing/General script]
views.get("i15").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 223;BA.debugLine="i14.Bottom=55%y"[writing/General script]
views.get("i14").vw.setTop((int)((55d / 100 * height) - (views.get("i14").vw.getHeight())));
//BA.debugLineNum = 224;BA.debugLine="i14.Left=115%y"[writing/General script]
views.get("i14").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 225;BA.debugLine="i14.Width=50%y"[writing/General script]
views.get("i14").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 226;BA.debugLine="i14.Height=50%y"[writing/General script]
views.get("i14").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 228;BA.debugLine="i13.Bottom=55%y"[writing/General script]
views.get("i13").vw.setTop((int)((55d / 100 * height) - (views.get("i13").vw.getHeight())));
//BA.debugLineNum = 229;BA.debugLine="i13.Left=115%y"[writing/General script]
views.get("i13").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 230;BA.debugLine="i13.Width=50%y"[writing/General script]
views.get("i13").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 231;BA.debugLine="i13.Height=50%y"[writing/General script]
views.get("i13").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 234;BA.debugLine="i12.Bottom=55%y"[writing/General script]
views.get("i12").vw.setTop((int)((55d / 100 * height) - (views.get("i12").vw.getHeight())));
//BA.debugLineNum = 235;BA.debugLine="i12.Left=115%y"[writing/General script]
views.get("i12").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 236;BA.debugLine="i12.Width=50%y"[writing/General script]
views.get("i12").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 237;BA.debugLine="i12.Height=50%y"[writing/General script]
views.get("i12").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 239;BA.debugLine="i11.Bottom=55%y"[writing/General script]
views.get("i11").vw.setTop((int)((55d / 100 * height) - (views.get("i11").vw.getHeight())));
//BA.debugLineNum = 240;BA.debugLine="i11.Left=115%y"[writing/General script]
views.get("i11").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 241;BA.debugLine="i11.Width=50%y"[writing/General script]
views.get("i11").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 242;BA.debugLine="i11.Height=50%y"[writing/General script]
views.get("i11").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 245;BA.debugLine="i10.Bottom=55%y"[writing/General script]
views.get("i10").vw.setTop((int)((55d / 100 * height) - (views.get("i10").vw.getHeight())));
//BA.debugLineNum = 246;BA.debugLine="i10.Left=115%y"[writing/General script]
views.get("i10").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 247;BA.debugLine="i10.Width=50%y"[writing/General script]
views.get("i10").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 248;BA.debugLine="i10.Height=50%y"[writing/General script]
views.get("i10").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 251;BA.debugLine="i9.Bottom=55%y"[writing/General script]
views.get("i9").vw.setTop((int)((55d / 100 * height) - (views.get("i9").vw.getHeight())));
//BA.debugLineNum = 252;BA.debugLine="i9.Left=115%y"[writing/General script]
views.get("i9").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 253;BA.debugLine="i9.Width=50%y"[writing/General script]
views.get("i9").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 254;BA.debugLine="i9.Height=50%y"[writing/General script]
views.get("i9").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 257;BA.debugLine="i8.Bottom=55%y"[writing/General script]
views.get("i8").vw.setTop((int)((55d / 100 * height) - (views.get("i8").vw.getHeight())));
//BA.debugLineNum = 258;BA.debugLine="i8.Left=115%y"[writing/General script]
views.get("i8").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 259;BA.debugLine="i8.Width=50%y"[writing/General script]
views.get("i8").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 260;BA.debugLine="i8.Height=50%y"[writing/General script]
views.get("i8").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 263;BA.debugLine="i7.Bottom=55%y"[writing/General script]
views.get("i7").vw.setTop((int)((55d / 100 * height) - (views.get("i7").vw.getHeight())));
//BA.debugLineNum = 264;BA.debugLine="i7.Left=115%y"[writing/General script]
views.get("i7").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 265;BA.debugLine="i7.Width=50%y"[writing/General script]
views.get("i7").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 266;BA.debugLine="i7.Height=50%y"[writing/General script]
views.get("i7").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 269;BA.debugLine="i6.Bottom=55%y"[writing/General script]
views.get("i6").vw.setTop((int)((55d / 100 * height) - (views.get("i6").vw.getHeight())));
//BA.debugLineNum = 270;BA.debugLine="i6.Left=115%y"[writing/General script]
views.get("i6").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 271;BA.debugLine="i6.Width=50%y"[writing/General script]
views.get("i6").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 272;BA.debugLine="i6.Height=50%y"[writing/General script]
views.get("i6").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 275;BA.debugLine="i5.Bottom=55%y"[writing/General script]
views.get("i5").vw.setTop((int)((55d / 100 * height) - (views.get("i5").vw.getHeight())));
//BA.debugLineNum = 276;BA.debugLine="i5.Left=115%y"[writing/General script]
views.get("i5").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 277;BA.debugLine="i5.Width=50%y"[writing/General script]
views.get("i5").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 278;BA.debugLine="i5.Height=50%y"[writing/General script]
views.get("i5").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 281;BA.debugLine="i4.Bottom=55%y"[writing/General script]
views.get("i4").vw.setTop((int)((55d / 100 * height) - (views.get("i4").vw.getHeight())));
//BA.debugLineNum = 282;BA.debugLine="i4.Left=115%y"[writing/General script]
views.get("i4").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 283;BA.debugLine="i4.Width=50%y"[writing/General script]
views.get("i4").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 284;BA.debugLine="i4.Height=50%y"[writing/General script]
views.get("i4").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 287;BA.debugLine="i3.Bottom=55%y"[writing/General script]
views.get("i3").vw.setTop((int)((55d / 100 * height) - (views.get("i3").vw.getHeight())));
//BA.debugLineNum = 288;BA.debugLine="i3.Left=115%y"[writing/General script]
views.get("i3").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 289;BA.debugLine="i3.Width=50%y"[writing/General script]
views.get("i3").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 290;BA.debugLine="i3.Height=50%y"[writing/General script]
views.get("i3").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 293;BA.debugLine="i2.Bottom=55%y"[writing/General script]
views.get("i2").vw.setTop((int)((55d / 100 * height) - (views.get("i2").vw.getHeight())));
//BA.debugLineNum = 294;BA.debugLine="i2.Left=115%y"[writing/General script]
views.get("i2").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 295;BA.debugLine="i2.Width=50%y"[writing/General script]
views.get("i2").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 296;BA.debugLine="i2.Height=50%y"[writing/General script]
views.get("i2").vw.setHeight((int)((50d / 100 * height)));
//BA.debugLineNum = 299;BA.debugLine="i1.Bottom=55%y"[writing/General script]
views.get("i1").vw.setTop((int)((55d / 100 * height) - (views.get("i1").vw.getHeight())));
//BA.debugLineNum = 300;BA.debugLine="i1.Left=115%y"[writing/General script]
views.get("i1").vw.setLeft((int)((115d / 100 * height)));
//BA.debugLineNum = 301;BA.debugLine="i1.Width=50%y"[writing/General script]
views.get("i1").vw.setWidth((int)((50d / 100 * height)));
//BA.debugLineNum = 302;BA.debugLine="i1.Height=50%y"[writing/General script]
views.get("i1").vw.setHeight((int)((50d / 100 * height)));

}
}