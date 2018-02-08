package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class scramble extends Activity implements B4AActivity{
	public static scramble mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.scramble");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (scramble).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.scramble");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.scramble", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (scramble) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (scramble) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return scramble.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (scramble) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (scramble) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer2 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static anywheresoftware.b4a.obejcts.TTS _tts = null;
public static anywheresoftware.b4a.objects.Timer _timer3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview10 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview11 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview12 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview13 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview14 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview15 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public static String[] _ltr = null;
public static int _r = 0;
public static String _let = "";
public static int _flip = 0;
public static String _l1 = "";
public static String _l2 = "";
public static String _l3 = "";
public static String _l4 = "";
public static String _l5 = "";
public static String _p1 = "";
public static String _p2 = "";
public static String _p3 = "";
public static String _p4 = "";
public static String _p5 = "";
public static String _word = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public static int _z = 0;
public static String[] _wrd = null;
public static int _seconds = 0;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public static String _speed = "";
public b4a.example.main _main = null;
public b4a.example.menu _menu = null;
public b4a.example.number _number = null;
public b4a.example.number_writing _number_writing = null;
public b4a.example.alphabet _alphabet = null;
public b4a.example.cat_alphabet _cat_alphabet = null;
public b4a.example.cat_num _cat_num = null;
public b4a.example.cat_fun _cat_fun = null;
public b4a.example.writing _writing = null;
public b4a.example.memory _memory = null;
public b4a.example.kid_video _kid_video = null;
public b4a.example.ayam _ayam = null;
public b4a.example.quiz _quiz = null;
public b4a.example.q2 _q2 = null;
public b4a.example.q3 _q3 = null;
public b4a.example.q4 _q4 = null;
public b4a.example.q5 _q5 = null;
public b4a.example.finish _finish = null;
public b4a.example.pisang _pisang = null;
public b4a.example.bangau _bangau = null;
public b4a.example.intro _intro = null;
public b4a.example.categories _categories = null;
public b4a.example.home _home = null;
public b4a.example.about _about = null;
public b4a.example.video _video = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 69;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 70;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"intro2.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro2.mp3");
 //BA.debugLineNum = 71;BA.debugLine="timer3.Initialize(\"timer3\", 1000)";
_timer3.Initialize(processBA,"timer3",(long) (1000));
 //BA.debugLineNum = 72;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 74;BA.debugLine="Activity.LoadLayout(\"scramble.bal\")";
mostCurrent._activity.LoadLayout("scramble.bal",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, \"a.";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"a.png").getObject()));
 //BA.debugLineNum = 79;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"b.";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"b.png").getObject()));
 //BA.debugLineNum = 80;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, \"c.";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"c.png").getObject()));
 //BA.debugLineNum = 81;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, \"d.";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"d.png").getObject()));
 //BA.debugLineNum = 82;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, \"e.";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"e.png").getObject()));
 //BA.debugLineNum = 83;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, \"f.";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"f.png").getObject()));
 //BA.debugLineNum = 84;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, \"g.";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"g.png").getObject()));
 //BA.debugLineNum = 85;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, \"h.";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"h.png").getObject()));
 //BA.debugLineNum = 86;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, \"i.";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"i.png").getObject()));
 //BA.debugLineNum = 87;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets, \"j";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"j.png").getObject()));
 //BA.debugLineNum = 88;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets, \"k";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"k.png").getObject()));
 //BA.debugLineNum = 89;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets, \"l";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"l.png").getObject()));
 //BA.debugLineNum = 90;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets, \"m";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"m.png").getObject()));
 //BA.debugLineNum = 91;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets, \"n";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"n.png").getObject()));
 //BA.debugLineNum = 92;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets, \"o";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"o.png").getObject()));
 //BA.debugLineNum = 93;BA.debugLine="Spinner1.AddAll(Array As String(\"Slow\",\"Med\",\"Fast";
mostCurrent._spinner1.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Slow","Med","Fast"}));
 //BA.debugLineNum = 94;BA.debugLine="Spinner1.SelectedIndex=0";
mostCurrent._spinner1.setSelectedIndex((int) (0));
 //BA.debugLineNum = 96;BA.debugLine="speed=\"Slow\"";
mostCurrent._speed = "Slow";
 //BA.debugLineNum = 98;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 103;BA.debugLine="timer3.Enabled = False";
_timer3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 106;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 111;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 113;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 114;BA.debugLine="timer3.Enabled = True";
_timer3.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 116;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 520;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 524;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 525;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 526;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 527;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 528;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 529;BA.debugLine="End Sub";
return "";
}
public static String  _calcx() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub calcx";
 //BA.debugLineNum = 191;BA.debugLine="If ltr(z)=L1 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l1)) { 
 //BA.debugLineNum = 192;BA.debugLine="P1=L1";
mostCurrent._p1 = mostCurrent._l1;
 };
 //BA.debugLineNum = 195;BA.debugLine="If ltr(z)=L2 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l2)) { 
 //BA.debugLineNum = 196;BA.debugLine="P2=L2";
mostCurrent._p2 = mostCurrent._l2;
 };
 //BA.debugLineNum = 199;BA.debugLine="If ltr(z)=L3 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l3)) { 
 //BA.debugLineNum = 200;BA.debugLine="P3=L3";
mostCurrent._p3 = mostCurrent._l3;
 };
 //BA.debugLineNum = 203;BA.debugLine="If ltr(z)=L4 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l4)) { 
 //BA.debugLineNum = 204;BA.debugLine="P4=L4";
mostCurrent._p4 = mostCurrent._l4;
 };
 //BA.debugLineNum = 207;BA.debugLine="If ltr(z)=L5 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l5)) { 
 //BA.debugLineNum = 208;BA.debugLine="P5=L5";
mostCurrent._p5 = mostCurrent._l5;
 };
 //BA.debugLineNum = 211;BA.debugLine="label2.Text=\"TYPED-\"& P1&P2&P3&P4&P5";
mostCurrent._label2.setText((Object)("TYPED-"+mostCurrent._p1+mostCurrent._p2+mostCurrent._p3+mostCurrent._p4+mostCurrent._p5));
 //BA.debugLineNum = 212;BA.debugLine="If P1&P2&P3&P4&P5=word Then";
if ((mostCurrent._p1+mostCurrent._p2+mostCurrent._p3+mostCurrent._p4+mostCurrent._p5).equals(mostCurrent._word)) { 
 //BA.debugLineNum = 213;BA.debugLine="Msgbox(\"You won!\",\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox("You won!","",mostCurrent.activityBA);
 //BA.debugLineNum = 214;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim ImageView3 As ImageView";
mostCurrent._imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim ImageView4 As ImageView";
mostCurrent._imageview4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim ImageView5 As ImageView";
mostCurrent._imageview5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ImageView6 As ImageView";
mostCurrent._imageview6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim ImageView7 As ImageView";
mostCurrent._imageview7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim ImageView8 As ImageView";
mostCurrent._imageview8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim ImageView9 As ImageView";
mostCurrent._imageview9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ImageView10 As ImageView";
mostCurrent._imageview10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim ImageView11 As ImageView";
mostCurrent._imageview11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim ImageView12 As ImageView";
mostCurrent._imageview12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim ImageView13 As ImageView";
mostCurrent._imageview13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim ImageView14 As ImageView";
mostCurrent._imageview14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim ImageView15 As ImageView";
mostCurrent._imageview15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim ltr(15) As String";
mostCurrent._ltr = new String[(int) (15)];
java.util.Arrays.fill(mostCurrent._ltr,"");
 //BA.debugLineNum = 38;BA.debugLine="Dim r As Int";
_r = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim let As String";
mostCurrent._let = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim flip As Int";
_flip = 0;
 //BA.debugLineNum = 42;BA.debugLine="Dim L1 As String";
mostCurrent._l1 = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim L2 As String";
mostCurrent._l2 = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim L3 As String";
mostCurrent._l3 = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim L4 As String";
mostCurrent._l4 = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim L5 As String";
mostCurrent._l5 = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim P1 As String";
mostCurrent._p1 = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim P2 As String";
mostCurrent._p2 = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim P3 As String";
mostCurrent._p3 = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim P4 As String";
mostCurrent._p4 = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim P5 As String";
mostCurrent._p5 = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim word As String";
mostCurrent._word = "";
 //BA.debugLineNum = 55;BA.debugLine="Dim label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim z As Int";
_z = 0;
 //BA.debugLineNum = 60;BA.debugLine="Dim wrd(20) As String";
mostCurrent._wrd = new String[(int) (20)];
java.util.Arrays.fill(mostCurrent._wrd,"");
 //BA.debugLineNum = 61;BA.debugLine="Dim seconds As Int";
_seconds = 0;
 //BA.debugLineNum = 62;BA.debugLine="Dim Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim speed As String";
mostCurrent._speed = "";
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Sub ImageView1_Click";
 //BA.debugLineNum = 221;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 222;BA.debugLine="z=0";
_z = (int) (0);
 //BA.debugLineNum = 223;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _imageview10_click() throws Exception{
 //BA.debugLineNum = 265;BA.debugLine="Sub ImageView10_Click";
 //BA.debugLineNum = 266;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 267;BA.debugLine="z=9";
_z = (int) (9);
 //BA.debugLineNum = 268;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _imageview11_click() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub ImageView11_Click";
 //BA.debugLineNum = 271;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 272;BA.debugLine="z=10";
_z = (int) (10);
 //BA.debugLineNum = 273;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _imageview12_click() throws Exception{
 //BA.debugLineNum = 275;BA.debugLine="Sub ImageView12_Click";
 //BA.debugLineNum = 276;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 277;BA.debugLine="z=11";
_z = (int) (11);
 //BA.debugLineNum = 278;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _imageview13_click() throws Exception{
 //BA.debugLineNum = 280;BA.debugLine="Sub ImageView13_Click";
 //BA.debugLineNum = 281;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 282;BA.debugLine="z=12";
_z = (int) (12);
 //BA.debugLineNum = 283;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _imageview14_click() throws Exception{
 //BA.debugLineNum = 285;BA.debugLine="Sub ImageView14_Click";
 //BA.debugLineNum = 286;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 287;BA.debugLine="z=13";
_z = (int) (13);
 //BA.debugLineNum = 288;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _imageview15_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub ImageView15_Click";
 //BA.debugLineNum = 291;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 292;BA.debugLine="z=14";
_z = (int) (14);
 //BA.debugLineNum = 293;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _imageview2_click() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Sub ImageView2_Click";
 //BA.debugLineNum = 226;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 227;BA.debugLine="z=1";
_z = (int) (1);
 //BA.debugLineNum = 228;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _imageview3_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub ImageView3_Click";
 //BA.debugLineNum = 231;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 232;BA.debugLine="z=2";
_z = (int) (2);
 //BA.debugLineNum = 233;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _imageview4_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Sub ImageView4_Click";
 //BA.debugLineNum = 236;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 237;BA.debugLine="z=3";
_z = (int) (3);
 //BA.debugLineNum = 238;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _imageview5_click() throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Sub ImageView5_Click";
 //BA.debugLineNum = 241;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 242;BA.debugLine="z=4";
_z = (int) (4);
 //BA.debugLineNum = 243;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _imageview6_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub ImageView6_Click";
 //BA.debugLineNum = 246;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 247;BA.debugLine="z=5";
_z = (int) (5);
 //BA.debugLineNum = 248;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _imageview7_click() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub ImageView7_Click";
 //BA.debugLineNum = 251;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 252;BA.debugLine="z=6";
_z = (int) (6);
 //BA.debugLineNum = 253;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _imageview8_click() throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub ImageView8_Click";
 //BA.debugLineNum = 256;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 257;BA.debugLine="z=7";
_z = (int) (7);
 //BA.debugLineNum = 258;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _imageview9_click() throws Exception{
 //BA.debugLineNum = 260;BA.debugLine="Sub ImageView9_Click";
 //BA.debugLineNum = 261;BA.debugLine="Main.clicksound.play";
mostCurrent._main._clicksound.Play();
 //BA.debugLineNum = 262;BA.debugLine="z=8";
_z = (int) (8);
 //BA.debugLineNum = 263;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim Timer2 As Timer";
_timer2 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="Dim MediaPlayer1 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 14;BA.debugLine="Dim timer3 As Timer";
_timer3 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _rand() throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub rand";
 //BA.debugLineNum = 300;BA.debugLine="r=Rnd(1,25)";
_r = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (25));
 //BA.debugLineNum = 302;BA.debugLine="If r=1 Then";
if (_r==1) { 
 //BA.debugLineNum = 303;BA.debugLine="let=\"a\"";
mostCurrent._let = "a";
 };
 //BA.debugLineNum = 306;BA.debugLine="If r=2 Then";
if (_r==2) { 
 //BA.debugLineNum = 307;BA.debugLine="let=\"b\"";
mostCurrent._let = "b";
 };
 //BA.debugLineNum = 310;BA.debugLine="If r=3 Then";
if (_r==3) { 
 //BA.debugLineNum = 311;BA.debugLine="let=\"c\"";
mostCurrent._let = "c";
 };
 //BA.debugLineNum = 314;BA.debugLine="If r=4 Then";
if (_r==4) { 
 //BA.debugLineNum = 315;BA.debugLine="let=\"d\"";
mostCurrent._let = "d";
 };
 //BA.debugLineNum = 318;BA.debugLine="If r=5 Then";
if (_r==5) { 
 //BA.debugLineNum = 319;BA.debugLine="let=\"e\"";
mostCurrent._let = "e";
 };
 //BA.debugLineNum = 322;BA.debugLine="If r=6 Then";
if (_r==6) { 
 //BA.debugLineNum = 323;BA.debugLine="let=\"f\"";
mostCurrent._let = "f";
 };
 //BA.debugLineNum = 326;BA.debugLine="If r=7 Then";
if (_r==7) { 
 //BA.debugLineNum = 327;BA.debugLine="let=\"g\"";
mostCurrent._let = "g";
 };
 //BA.debugLineNum = 330;BA.debugLine="If r=8 Then";
if (_r==8) { 
 //BA.debugLineNum = 331;BA.debugLine="let=\"h\"";
mostCurrent._let = "h";
 };
 //BA.debugLineNum = 334;BA.debugLine="If r=9 Then";
if (_r==9) { 
 //BA.debugLineNum = 335;BA.debugLine="let=\"i\"";
mostCurrent._let = "i";
 };
 //BA.debugLineNum = 338;BA.debugLine="If r=10 Then";
if (_r==10) { 
 //BA.debugLineNum = 339;BA.debugLine="let=\"j\"";
mostCurrent._let = "j";
 };
 //BA.debugLineNum = 342;BA.debugLine="If r=11 Then";
if (_r==11) { 
 //BA.debugLineNum = 343;BA.debugLine="let=\"k\"";
mostCurrent._let = "k";
 };
 //BA.debugLineNum = 346;BA.debugLine="If r=12 Then";
if (_r==12) { 
 //BA.debugLineNum = 347;BA.debugLine="let=\"l\"";
mostCurrent._let = "l";
 };
 //BA.debugLineNum = 350;BA.debugLine="If r=13 Then";
if (_r==13) { 
 //BA.debugLineNum = 351;BA.debugLine="let=\"m\"";
mostCurrent._let = "m";
 };
 //BA.debugLineNum = 354;BA.debugLine="If r=14 Then";
if (_r==14) { 
 //BA.debugLineNum = 355;BA.debugLine="let=\"n\"";
mostCurrent._let = "n";
 };
 //BA.debugLineNum = 358;BA.debugLine="If r=15 Then";
if (_r==15) { 
 //BA.debugLineNum = 359;BA.debugLine="let=\"o\"";
mostCurrent._let = "o";
 };
 //BA.debugLineNum = 362;BA.debugLine="If r=16 Then";
if (_r==16) { 
 //BA.debugLineNum = 363;BA.debugLine="let=\"p\"";
mostCurrent._let = "p";
 };
 //BA.debugLineNum = 366;BA.debugLine="If r=17 Then";
if (_r==17) { 
 //BA.debugLineNum = 367;BA.debugLine="let=\"q\"";
mostCurrent._let = "q";
 };
 //BA.debugLineNum = 370;BA.debugLine="If r=18 Then";
if (_r==18) { 
 //BA.debugLineNum = 371;BA.debugLine="let=\"r\"";
mostCurrent._let = "r";
 };
 //BA.debugLineNum = 374;BA.debugLine="If r=19 Then";
if (_r==19) { 
 //BA.debugLineNum = 375;BA.debugLine="let=\"s\"";
mostCurrent._let = "s";
 };
 //BA.debugLineNum = 378;BA.debugLine="If r=20 Then";
if (_r==20) { 
 //BA.debugLineNum = 379;BA.debugLine="let=\"t\"";
mostCurrent._let = "t";
 };
 //BA.debugLineNum = 382;BA.debugLine="If r=21 Then";
if (_r==21) { 
 //BA.debugLineNum = 383;BA.debugLine="let=\"u\"";
mostCurrent._let = "u";
 };
 //BA.debugLineNum = 386;BA.debugLine="If r=22 Then";
if (_r==22) { 
 //BA.debugLineNum = 387;BA.debugLine="let=\"v\"";
mostCurrent._let = "v";
 };
 //BA.debugLineNum = 390;BA.debugLine="If r=23 Then";
if (_r==23) { 
 //BA.debugLineNum = 391;BA.debugLine="let=\"w\"";
mostCurrent._let = "w";
 };
 //BA.debugLineNum = 394;BA.debugLine="If r=24 Then";
if (_r==24) { 
 //BA.debugLineNum = 395;BA.debugLine="let=\"x\"";
mostCurrent._let = "x";
 };
 //BA.debugLineNum = 398;BA.debugLine="If r=25 Then";
if (_r==25) { 
 //BA.debugLineNum = 399;BA.debugLine="let=\"y\"";
mostCurrent._let = "y";
 };
 //BA.debugLineNum = 402;BA.debugLine="If r=26 Then";
if (_r==26) { 
 //BA.debugLineNum = 403;BA.debugLine="let=\"z\"";
mostCurrent._let = "z";
 };
 //BA.debugLineNum = 406;BA.debugLine="End Sub";
return "";
}
public static String  _restart() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub restart";
 //BA.debugLineNum = 131;BA.debugLine="seconds=0";
_seconds = (int) (0);
 //BA.debugLineNum = 132;BA.debugLine="label3.text=\"TIME   -\"&seconds";
mostCurrent._label3.setText((Object)("TIME   -"+BA.NumberToString(_seconds)));
 //BA.debugLineNum = 134;BA.debugLine="If speed=\"Slow\" Then";
if ((mostCurrent._speed).equals("Slow")) { 
 //BA.debugLineNum = 135;BA.debugLine="Timer1.Initialize(\"Timer1\",6000)";
_timer1.Initialize(processBA,"Timer1",(long) (6000));
 };
 //BA.debugLineNum = 138;BA.debugLine="If speed=\"Med\" Then";
if ((mostCurrent._speed).equals("Med")) { 
 //BA.debugLineNum = 139;BA.debugLine="Timer1.Initialize(\"Timer1\",1500)";
_timer1.Initialize(processBA,"Timer1",(long) (1500));
 };
 //BA.debugLineNum = 142;BA.debugLine="If speed=\"Fast\" Then";
if ((mostCurrent._speed).equals("Fast")) { 
 //BA.debugLineNum = 143;BA.debugLine="Timer1.Initialize(\"Timer1\",1000)";
_timer1.Initialize(processBA,"Timer1",(long) (1000));
 };
 //BA.debugLineNum = 146;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="wrd(0)=\"hello\"";
mostCurrent._wrd[(int) (0)] = "hello";
 //BA.debugLineNum = 148;BA.debugLine="wrd(1)=\"tried\"";
mostCurrent._wrd[(int) (1)] = "tried";
 //BA.debugLineNum = 149;BA.debugLine="wrd(2)=\"looks\"";
mostCurrent._wrd[(int) (2)] = "looks";
 //BA.debugLineNum = 150;BA.debugLine="wrd(3)=\"fried\"";
mostCurrent._wrd[(int) (3)] = "fried";
 //BA.debugLineNum = 151;BA.debugLine="wrd(4)=\"hosts\"";
mostCurrent._wrd[(int) (4)] = "hosts";
 //BA.debugLineNum = 152;BA.debugLine="wrd(5)=\"jerks\"";
mostCurrent._wrd[(int) (5)] = "jerks";
 //BA.debugLineNum = 153;BA.debugLine="wrd(6)=\"spoon\"";
mostCurrent._wrd[(int) (6)] = "spoon";
 //BA.debugLineNum = 154;BA.debugLine="wrd(7)=\"dread\"";
mostCurrent._wrd[(int) (7)] = "dread";
 //BA.debugLineNum = 155;BA.debugLine="wrd(8)=\"reads\"";
mostCurrent._wrd[(int) (8)] = "reads";
 //BA.debugLineNum = 156;BA.debugLine="wrd(9)=\"timed\"";
mostCurrent._wrd[(int) (9)] = "timed";
 //BA.debugLineNum = 157;BA.debugLine="wrd(10)=\"bring\"";
mostCurrent._wrd[(int) (10)] = "bring";
 //BA.debugLineNum = 158;BA.debugLine="wrd(11)=\"sling\"";
mostCurrent._wrd[(int) (11)] = "sling";
 //BA.debugLineNum = 159;BA.debugLine="wrd(12)=\"wrong\"";
mostCurrent._wrd[(int) (12)] = "wrong";
 //BA.debugLineNum = 160;BA.debugLine="wrd(13)=\"right\"";
mostCurrent._wrd[(int) (13)] = "right";
 //BA.debugLineNum = 161;BA.debugLine="wrd(14)=\"hello\"";
mostCurrent._wrd[(int) (14)] = "hello";
 //BA.debugLineNum = 162;BA.debugLine="wrd(15)=\"trend\"";
mostCurrent._wrd[(int) (15)] = "trend";
 //BA.debugLineNum = 163;BA.debugLine="wrd(16)=\"traps\"";
mostCurrent._wrd[(int) (16)] = "traps";
 //BA.debugLineNum = 164;BA.debugLine="wrd(17)=\"paint\"";
mostCurrent._wrd[(int) (17)] = "paint";
 //BA.debugLineNum = 165;BA.debugLine="wrd(18)=\"untie\"";
mostCurrent._wrd[(int) (18)] = "untie";
 //BA.debugLineNum = 166;BA.debugLine="wrd(19)=\"trust\"";
mostCurrent._wrd[(int) (19)] = "trust";
 //BA.debugLineNum = 168;BA.debugLine="Dim z As Int";
_z = 0;
 //BA.debugLineNum = 169;BA.debugLine="z=Rnd(0,20)";
_z = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (20));
 //BA.debugLineNum = 170;BA.debugLine="word=wrd(z)";
mostCurrent._word = mostCurrent._wrd[_z];
 //BA.debugLineNum = 172;BA.debugLine="P1=\"_\"";
mostCurrent._p1 = "_";
 //BA.debugLineNum = 173;BA.debugLine="P2=\"_\"";
mostCurrent._p2 = "_";
 //BA.debugLineNum = 174;BA.debugLine="P3=\"_\"";
mostCurrent._p3 = "_";
 //BA.debugLineNum = 175;BA.debugLine="P4=\"_\"";
mostCurrent._p4 = "_";
 //BA.debugLineNum = 176;BA.debugLine="P5=\"_\"";
mostCurrent._p5 = "_";
 //BA.debugLineNum = 178;BA.debugLine="label2.Text=\"TYPED-\"& P1&P2&P3&P4&P5";
mostCurrent._label2.setText((Object)("TYPED-"+mostCurrent._p1+mostCurrent._p2+mostCurrent._p3+mostCurrent._p4+mostCurrent._p5));
 //BA.debugLineNum = 180;BA.debugLine="L1=word.SubString2(0,1)";
mostCurrent._l1 = mostCurrent._word.substring((int) (0),(int) (1));
 //BA.debugLineNum = 181;BA.debugLine="L2=word.SubString2(1,2)";
mostCurrent._l2 = mostCurrent._word.substring((int) (1),(int) (2));
 //BA.debugLineNum = 182;BA.debugLine="L3=word.SubString2(2,3)";
mostCurrent._l3 = mostCurrent._word.substring((int) (2),(int) (3));
 //BA.debugLineNum = 183;BA.debugLine="L4=word.SubString2(3,4)";
mostCurrent._l4 = mostCurrent._word.substring((int) (3),(int) (4));
 //BA.debugLineNum = 184;BA.debugLine="L5=word.SubString2(4,5)";
mostCurrent._l5 = mostCurrent._word.substring((int) (4),(int) (5));
 //BA.debugLineNum = 186;BA.debugLine="label1.Text=\"WORD-\"&word";
mostCurrent._label1.setText((Object)("WORD-"+mostCurrent._word));
 //BA.debugLineNum = 187;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 531;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 532;BA.debugLine="speed=Value";
mostCurrent._speed = BA.ObjectToString(_value);
 //BA.debugLineNum = 537;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
int _x = 0;
 //BA.debugLineNum = 409;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 410;BA.debugLine="seconds=seconds+1";
_seconds = (int) (_seconds+1);
 //BA.debugLineNum = 412;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 414;BA.debugLine="flip=flip+1";
_flip = (int) (_flip+1);
 //BA.debugLineNum = 416;BA.debugLine="If flip>2 Then";
if (_flip>2) { 
 //BA.debugLineNum = 417;BA.debugLine="flip=1";
_flip = (int) (1);
 };
 //BA.debugLineNum = 420;BA.debugLine="For x=1 To 15";
{
final int step7 = 1;
final int limit7 = (int) (15);
for (_x = (int) (1) ; (step7 > 0 && _x <= limit7) || (step7 < 0 && _x >= limit7); _x = ((int)(0 + _x + step7)) ) {
 //BA.debugLineNum = 422;BA.debugLine="If x=1 Then";
if (_x==1) { 
 //BA.debugLineNum = 423;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 424;BA.debugLine="ltr(0)=let";
mostCurrent._ltr[(int) (0)] = mostCurrent._let;
 //BA.debugLineNum = 425;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 428;BA.debugLine="If x=2 Then";
if (_x==2) { 
 //BA.debugLineNum = 429;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 430;BA.debugLine="ltr(1)=let";
mostCurrent._ltr[(int) (1)] = mostCurrent._let;
 //BA.debugLineNum = 431;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 434;BA.debugLine="If x=3 Then";
if (_x==3) { 
 //BA.debugLineNum = 435;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 436;BA.debugLine="ltr(2)=let";
mostCurrent._ltr[(int) (2)] = mostCurrent._let;
 //BA.debugLineNum = 437;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 440;BA.debugLine="If x=4 Then";
if (_x==4) { 
 //BA.debugLineNum = 441;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 442;BA.debugLine="ltr(3)=let";
mostCurrent._ltr[(int) (3)] = mostCurrent._let;
 //BA.debugLineNum = 443;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 446;BA.debugLine="If x=5 Then";
if (_x==5) { 
 //BA.debugLineNum = 447;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 448;BA.debugLine="ltr(4)=let";
mostCurrent._ltr[(int) (4)] = mostCurrent._let;
 //BA.debugLineNum = 449;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 452;BA.debugLine="If x=6 Then";
if (_x==6) { 
 //BA.debugLineNum = 453;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 454;BA.debugLine="ltr(5)=let";
mostCurrent._ltr[(int) (5)] = mostCurrent._let;
 //BA.debugLineNum = 455;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 458;BA.debugLine="If x=7 Then";
if (_x==7) { 
 //BA.debugLineNum = 459;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 460;BA.debugLine="ltr(6)=let";
mostCurrent._ltr[(int) (6)] = mostCurrent._let;
 //BA.debugLineNum = 461;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 464;BA.debugLine="If x=8 Then";
if (_x==8) { 
 //BA.debugLineNum = 465;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 466;BA.debugLine="ltr(7)=let";
mostCurrent._ltr[(int) (7)] = mostCurrent._let;
 //BA.debugLineNum = 467;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 470;BA.debugLine="If x=9 Then";
if (_x==9) { 
 //BA.debugLineNum = 471;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 472;BA.debugLine="ltr(8)=let";
mostCurrent._ltr[(int) (8)] = mostCurrent._let;
 //BA.debugLineNum = 473;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, let";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 476;BA.debugLine="If x=10 Then";
if (_x==10) { 
 //BA.debugLineNum = 477;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 478;BA.debugLine="ltr(9)=let";
mostCurrent._ltr[(int) (9)] = mostCurrent._let;
 //BA.debugLineNum = 479;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 482;BA.debugLine="If x=11 Then";
if (_x==11) { 
 //BA.debugLineNum = 483;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 484;BA.debugLine="ltr(10)=let";
mostCurrent._ltr[(int) (10)] = mostCurrent._let;
 //BA.debugLineNum = 485;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 488;BA.debugLine="If x=12 Then";
if (_x==12) { 
 //BA.debugLineNum = 489;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 490;BA.debugLine="ltr(11)=let";
mostCurrent._ltr[(int) (11)] = mostCurrent._let;
 //BA.debugLineNum = 491;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 494;BA.debugLine="If x=13 Then";
if (_x==13) { 
 //BA.debugLineNum = 495;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 496;BA.debugLine="ltr(12)=let";
mostCurrent._ltr[(int) (12)] = mostCurrent._let;
 //BA.debugLineNum = 497;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 500;BA.debugLine="If x=14 Then";
if (_x==14) { 
 //BA.debugLineNum = 501;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 502;BA.debugLine="ltr(13)=let";
mostCurrent._ltr[(int) (13)] = mostCurrent._let;
 //BA.debugLineNum = 503;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 //BA.debugLineNum = 506;BA.debugLine="If x=15 Then";
if (_x==15) { 
 //BA.debugLineNum = 507;BA.debugLine="rand";
_rand();
 //BA.debugLineNum = 508;BA.debugLine="ltr(14)=let";
mostCurrent._ltr[(int) (14)] = mostCurrent._let;
 //BA.debugLineNum = 509;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets, le";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._let+".png").getObject()));
 };
 }
};
 //BA.debugLineNum = 512;BA.debugLine="label3.text=\"TIME   -\"&seconds";
mostCurrent._label3.setText((Object)("TIME   -"+BA.NumberToString(_seconds)));
 //BA.debugLineNum = 514;BA.debugLine="If seconds=30 Then";
if (_seconds==30) { 
 //BA.debugLineNum = 515;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 516;BA.debugLine="Msgbox(\"You lost!\",\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox("You lost!","",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
return "";
}
public static String  _timer3_tick() throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub timer3_Tick";
 //BA.debugLineNum = 123;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 128;BA.debugLine="End Sub";
return "";
}
}
