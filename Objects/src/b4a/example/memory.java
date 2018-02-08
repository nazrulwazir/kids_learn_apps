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

public class memory extends Activity implements B4AActivity{
	public static memory mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.memory");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (memory).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.memory");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.memory", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (memory) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (memory) Resume **");
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
		return memory.class;
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
        BA.LogInfo("** Activity (memory) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (memory) Resume **");
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
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button10 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button11 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button12 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button13 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button14 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button15 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button16 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button17 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button18 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button19 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button20 = null;
public static int _clicks = 0;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label11 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label14 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label15 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label16 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label17 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label18 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label19 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label20 = null;
public static int[] _loc = null;
public static int[] _sel = null;
public static int _x = 0;
public static int _y = 0;
public static int _z = 0;
public static int _select1 = 0;
public static int _select2 = 0;
public static int _loc1 = 0;
public static int _loc2 = 0;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
public static String _busy = "";
public static int _bt = 0;
public static int _lc = 0;
public static int _match = 0;
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
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview16 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview17 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview18 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview19 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview20 = null;
public static int _one = 0;
public static int _two = 0;
public b4a.example.main _main = null;
public b4a.example.menu _menu = null;
public b4a.example.number _number = null;
public b4a.example.number_writing _number_writing = null;
public b4a.example.alphabet _alphabet = null;
public b4a.example.cat_alphabet _cat_alphabet = null;
public b4a.example.cat_num _cat_num = null;
public b4a.example.cat_fun _cat_fun = null;
public b4a.example.scramble _scramble = null;
public b4a.example.writing _writing = null;
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
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 103;BA.debugLine="Timer1.Initialize(\"Timer1\",1000)";
_timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 104;BA.debugLine="Timer2.Initialize(\"Timer1\",4000)";
_timer2.Initialize(processBA,"Timer1",(long) (4000));
 };
 //BA.debugLineNum = 110;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 111;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"intro2.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro2.mp3");
 //BA.debugLineNum = 112;BA.debugLine="timer3.Initialize(\"timer3\", 1000)";
_timer3.Initialize(processBA,"timer3",(long) (1000));
 //BA.debugLineNum = 113;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 117;BA.debugLine="Activity.LoadLayout(\"match.bal\")";
mostCurrent._activity.LoadLayout("match.bal",mostCurrent.activityBA);
 //BA.debugLineNum = 119;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 124;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 125;BA.debugLine="timer3.Enabled = False";
_timer3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 127;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 136;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 137;BA.debugLine="timer3.Enabled = True";
_timer3.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 138;BA.debugLine="timer3_Tick 'don't wait one second for the UI to";
_timer3_tick();
 //BA.debugLineNum = 139;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 140;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 309;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 314;BA.debugLine="lc=loc(0)";
_lc = _loc[(int) (0)];
 //BA.debugLineNum = 315;BA.debugLine="bt=1";
_bt = (int) (1);
 //BA.debugLineNum = 316;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public static String  _button10_click() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub Button10_Click";
 //BA.debugLineNum = 361;BA.debugLine="lc=loc(9)";
_lc = _loc[(int) (9)];
 //BA.debugLineNum = 362;BA.debugLine="bt=10";
_bt = (int) (10);
 //BA.debugLineNum = 363;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 364;BA.debugLine="End Sub";
return "";
}
public static String  _button11_click() throws Exception{
 //BA.debugLineNum = 365;BA.debugLine="Sub Button11_Click";
 //BA.debugLineNum = 366;BA.debugLine="lc=loc(10)";
_lc = _loc[(int) (10)];
 //BA.debugLineNum = 367;BA.debugLine="bt=11";
_bt = (int) (11);
 //BA.debugLineNum = 368;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 369;BA.debugLine="End Sub";
return "";
}
public static String  _button12_click() throws Exception{
 //BA.debugLineNum = 370;BA.debugLine="Sub Button12_Click";
 //BA.debugLineNum = 371;BA.debugLine="lc=loc(11)";
_lc = _loc[(int) (11)];
 //BA.debugLineNum = 372;BA.debugLine="bt=12";
_bt = (int) (12);
 //BA.debugLineNum = 373;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 374;BA.debugLine="End Sub";
return "";
}
public static String  _button13_click() throws Exception{
 //BA.debugLineNum = 375;BA.debugLine="Sub Button13_Click";
 //BA.debugLineNum = 376;BA.debugLine="lc=loc(12)";
_lc = _loc[(int) (12)];
 //BA.debugLineNum = 377;BA.debugLine="bt=13";
_bt = (int) (13);
 //BA.debugLineNum = 378;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 379;BA.debugLine="End Sub";
return "";
}
public static String  _button14_click() throws Exception{
 //BA.debugLineNum = 380;BA.debugLine="Sub Button14_Click";
 //BA.debugLineNum = 381;BA.debugLine="lc=loc(13)";
_lc = _loc[(int) (13)];
 //BA.debugLineNum = 382;BA.debugLine="bt=14";
_bt = (int) (14);
 //BA.debugLineNum = 383;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return "";
}
public static String  _button15_click() throws Exception{
 //BA.debugLineNum = 385;BA.debugLine="Sub Button15_Click";
 //BA.debugLineNum = 386;BA.debugLine="lc=loc(14)";
_lc = _loc[(int) (14)];
 //BA.debugLineNum = 387;BA.debugLine="bt=15";
_bt = (int) (15);
 //BA.debugLineNum = 388;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 389;BA.debugLine="End Sub";
return "";
}
public static String  _button16_click() throws Exception{
 //BA.debugLineNum = 390;BA.debugLine="Sub Button16_Click";
 //BA.debugLineNum = 391;BA.debugLine="lc=loc(15)";
_lc = _loc[(int) (15)];
 //BA.debugLineNum = 392;BA.debugLine="bt=16";
_bt = (int) (16);
 //BA.debugLineNum = 393;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _button17_click() throws Exception{
 //BA.debugLineNum = 395;BA.debugLine="Sub Button17_Click";
 //BA.debugLineNum = 396;BA.debugLine="lc=loc(16)";
_lc = _loc[(int) (16)];
 //BA.debugLineNum = 397;BA.debugLine="bt=17";
_bt = (int) (17);
 //BA.debugLineNum = 398;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return "";
}
public static String  _button18_click() throws Exception{
 //BA.debugLineNum = 400;BA.debugLine="Sub Button18_Click";
 //BA.debugLineNum = 401;BA.debugLine="lc=loc(17)";
_lc = _loc[(int) (17)];
 //BA.debugLineNum = 402;BA.debugLine="bt=18";
_bt = (int) (18);
 //BA.debugLineNum = 403;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _button19_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub Button19_Click";
 //BA.debugLineNum = 406;BA.debugLine="lc=loc(18)";
_lc = _loc[(int) (18)];
 //BA.debugLineNum = 407;BA.debugLine="bt=19";
_bt = (int) (19);
 //BA.debugLineNum = 408;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 318;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 319;BA.debugLine="lc=loc(1)";
_lc = _loc[(int) (1)];
 //BA.debugLineNum = 320;BA.debugLine="bt=2";
_bt = (int) (2);
 //BA.debugLineNum = 321;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 322;BA.debugLine="End Sub";
return "";
}
public static String  _button20_click() throws Exception{
 //BA.debugLineNum = 410;BA.debugLine="Sub Button20_Click";
 //BA.debugLineNum = 411;BA.debugLine="lc=loc(19)";
_lc = _loc[(int) (19)];
 //BA.debugLineNum = 412;BA.debugLine="bt=20";
_bt = (int) (20);
 //BA.debugLineNum = 413;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 414;BA.debugLine="End Sub";
return "";
}
public static String  _button21_click() throws Exception{
 //BA.debugLineNum = 418;BA.debugLine="Sub Button21_Click";
 //BA.debugLineNum = 419;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _button22_click() throws Exception{
 //BA.debugLineNum = 423;BA.debugLine="Sub Button22_Click";
 //BA.debugLineNum = 425;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 323;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 324;BA.debugLine="lc=loc(2)";
_lc = _loc[(int) (2)];
 //BA.debugLineNum = 325;BA.debugLine="bt=3";
_bt = (int) (3);
 //BA.debugLineNum = 326;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 329;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 330;BA.debugLine="lc=loc(3)";
_lc = _loc[(int) (3)];
 //BA.debugLineNum = 331;BA.debugLine="bt=4";
_bt = (int) (4);
 //BA.debugLineNum = 332;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 335;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 336;BA.debugLine="lc=loc(4)";
_lc = _loc[(int) (4)];
 //BA.debugLineNum = 337;BA.debugLine="bt=5";
_bt = (int) (5);
 //BA.debugLineNum = 338;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return "";
}
public static String  _button6_click() throws Exception{
 //BA.debugLineNum = 340;BA.debugLine="Sub Button6_Click";
 //BA.debugLineNum = 341;BA.debugLine="lc=loc(5)";
_lc = _loc[(int) (5)];
 //BA.debugLineNum = 342;BA.debugLine="bt=6";
_bt = (int) (6);
 //BA.debugLineNum = 343;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _button7_click() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub Button7_Click";
 //BA.debugLineNum = 346;BA.debugLine="lc=loc(6)";
_lc = _loc[(int) (6)];
 //BA.debugLineNum = 347;BA.debugLine="bt=7";
_bt = (int) (7);
 //BA.debugLineNum = 348;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _button8_click() throws Exception{
 //BA.debugLineNum = 350;BA.debugLine="Sub Button8_Click";
 //BA.debugLineNum = 351;BA.debugLine="lc=loc(7)";
_lc = _loc[(int) (7)];
 //BA.debugLineNum = 352;BA.debugLine="bt=8";
_bt = (int) (8);
 //BA.debugLineNum = 353;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _button9_click() throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Sub Button9_Click";
 //BA.debugLineNum = 356;BA.debugLine="lc=loc(8)";
_lc = _loc[(int) (8)];
 //BA.debugLineNum = 357;BA.debugLine="bt=9";
_bt = (int) (9);
 //BA.debugLineNum = 358;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 359;BA.debugLine="End Sub";
return "";
}
public static String  _calcx() throws Exception{
 //BA.debugLineNum = 709;BA.debugLine="Sub calcx";
 //BA.debugLineNum = 711;BA.debugLine="If busy=\"no\" Then";
if ((mostCurrent._busy).equals("no")) { 
 //BA.debugLineNum = 713;BA.debugLine="clicks=clicks+1";
_clicks = (int) (_clicks+1);
 //BA.debugLineNum = 715;BA.debugLine="If clicks<=2 And bt=1 Then";
if (_clicks<=2 && _bt==1) { 
 //BA.debugLineNum = 716;BA.debugLine="button1.visible=False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 719;BA.debugLine="If clicks<=2 And bt=2 Then";
if (_clicks<=2 && _bt==2) { 
 //BA.debugLineNum = 720;BA.debugLine="button2.visible=False";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 723;BA.debugLine="If clicks<=2 And bt=3 Then";
if (_clicks<=2 && _bt==3) { 
 //BA.debugLineNum = 724;BA.debugLine="button3.visible=False";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 727;BA.debugLine="If clicks<=2 And bt=4 Then";
if (_clicks<=2 && _bt==4) { 
 //BA.debugLineNum = 728;BA.debugLine="button4.visible=False";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 731;BA.debugLine="If clicks<=2 And bt=5 Then";
if (_clicks<=2 && _bt==5) { 
 //BA.debugLineNum = 732;BA.debugLine="button5.visible=False";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 735;BA.debugLine="If clicks<=2 And bt=6 Then";
if (_clicks<=2 && _bt==6) { 
 //BA.debugLineNum = 736;BA.debugLine="button6.visible=False";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 739;BA.debugLine="If clicks<=2 And bt=7 Then";
if (_clicks<=2 && _bt==7) { 
 //BA.debugLineNum = 740;BA.debugLine="button7.visible=False";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 743;BA.debugLine="If clicks<=2 And bt=8 Then";
if (_clicks<=2 && _bt==8) { 
 //BA.debugLineNum = 744;BA.debugLine="button8.visible=False";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 747;BA.debugLine="If clicks<=2 And bt=9 Then";
if (_clicks<=2 && _bt==9) { 
 //BA.debugLineNum = 748;BA.debugLine="button9.visible=False";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 751;BA.debugLine="If clicks<=2 And bt=10 Then";
if (_clicks<=2 && _bt==10) { 
 //BA.debugLineNum = 752;BA.debugLine="button10.visible=False";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 755;BA.debugLine="If clicks<=2 And bt=11 Then";
if (_clicks<=2 && _bt==11) { 
 //BA.debugLineNum = 756;BA.debugLine="button11.visible=False";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 759;BA.debugLine="If clicks<=2 And bt=12 Then";
if (_clicks<=2 && _bt==12) { 
 //BA.debugLineNum = 760;BA.debugLine="button12.visible=False";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 763;BA.debugLine="If clicks<=2 And bt=13 Then";
if (_clicks<=2 && _bt==13) { 
 //BA.debugLineNum = 764;BA.debugLine="button13.visible=False";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 767;BA.debugLine="If clicks<=2 And bt=14 Then";
if (_clicks<=2 && _bt==14) { 
 //BA.debugLineNum = 768;BA.debugLine="button14.visible=False";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 771;BA.debugLine="If clicks<=2 And bt=15 Then";
if (_clicks<=2 && _bt==15) { 
 //BA.debugLineNum = 772;BA.debugLine="button15.visible=False";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 775;BA.debugLine="If clicks<=2 And bt=16 Then";
if (_clicks<=2 && _bt==16) { 
 //BA.debugLineNum = 776;BA.debugLine="button16.visible=False";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 779;BA.debugLine="If clicks<=2 And bt=17 Then";
if (_clicks<=2 && _bt==17) { 
 //BA.debugLineNum = 780;BA.debugLine="button17.visible=False";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 783;BA.debugLine="If clicks<=2 And bt=18 Then";
if (_clicks<=2 && _bt==18) { 
 //BA.debugLineNum = 784;BA.debugLine="button18.visible=False";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 787;BA.debugLine="If clicks<=2 And bt=19 Then";
if (_clicks<=2 && _bt==19) { 
 //BA.debugLineNum = 788;BA.debugLine="button19.visible=False";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 791;BA.debugLine="If clicks<=2 And bt=20 Then";
if (_clicks<=2 && _bt==20) { 
 //BA.debugLineNum = 792;BA.debugLine="button20.visible=False";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 798;BA.debugLine="If clicks=1 Then";
if (_clicks==1) { 
 //BA.debugLineNum = 799;BA.debugLine="Select1=lc";
_select1 = _lc;
 //BA.debugLineNum = 800;BA.debugLine="one=bt";
_one = _bt;
 //BA.debugLineNum = 801;BA.debugLine="loc1=bt";
_loc1 = _bt;
 };
 //BA.debugLineNum = 810;BA.debugLine="If clicks=2 Then";
if (_clicks==2) { 
 //BA.debugLineNum = 811;BA.debugLine="select2=lc";
_select2 = _lc;
 //BA.debugLineNum = 814;BA.debugLine="two=bt";
_two = _bt;
 //BA.debugLineNum = 817;BA.debugLine="If Select1=select2 Then";
if (_select1==_select2) { 
 //BA.debugLineNum = 819;BA.debugLine="match=match+1";
_match = (int) (_match+1);
 }else {
 //BA.debugLineNum = 823;BA.debugLine="loc2=bt";
_loc2 = _bt;
 //BA.debugLineNum = 824;BA.debugLine="busy=\"yes\"";
mostCurrent._busy = "yes";
 //BA.debugLineNum = 825;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 829;BA.debugLine="clicks=0";
_clicks = (int) (0);
 };
 //BA.debugLineNum = 832;BA.debugLine="If match=10 Then";
if (_match==10) { 
 //BA.debugLineNum = 833;BA.debugLine="Msgbox(\"you won!\",\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox("you won!","",mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 839;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 153;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 154;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 155;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 156;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 157;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 158;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _delayx() throws Exception{
 //BA.debugLineNum = 428;BA.debugLine="Sub delayx";
 //BA.debugLineNum = 430;BA.debugLine="For x=1 To 1000000";
{
final int step1 = 1;
final int limit1 = (int) (1000000);
for (_x = (int) (1) ; (step1 > 0 && _x <= limit1) || (step1 < 0 && _x >= limit1); _x = ((int)(0 + _x + step1)) ) {
 }
};
 //BA.debugLineNum = 434;BA.debugLine="If loc1=1 Or loc2=1 Then";
if (_loc1==1 || _loc2==1) { 
 //BA.debugLineNum = 435;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 438;BA.debugLine="If loc1=2 Or loc2=2 Then";
if (_loc1==2 || _loc2==2) { 
 //BA.debugLineNum = 439;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 442;BA.debugLine="If loc1=3 Or loc2=3 Then";
if (_loc1==3 || _loc2==3) { 
 //BA.debugLineNum = 443;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 446;BA.debugLine="If loc1=4 Or loc2=4 Then";
if (_loc1==4 || _loc2==4) { 
 //BA.debugLineNum = 447;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 450;BA.debugLine="If loc1=5 Or loc2=5 Then";
if (_loc1==5 || _loc2==5) { 
 //BA.debugLineNum = 451;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 454;BA.debugLine="If loc1=6 Or loc2=6 Then";
if (_loc1==6 || _loc2==6) { 
 //BA.debugLineNum = 455;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 458;BA.debugLine="If loc1=7 Or loc2=7 Then";
if (_loc1==7 || _loc2==7) { 
 //BA.debugLineNum = 459;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 462;BA.debugLine="If loc1=8 Or loc2=8 Then";
if (_loc1==8 || _loc2==8) { 
 //BA.debugLineNum = 463;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 466;BA.debugLine="If loc1=9 Or loc2=9 Then";
if (_loc1==9 || _loc2==9) { 
 //BA.debugLineNum = 467;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 470;BA.debugLine="If loc1=10 Or loc2=10 Then";
if (_loc1==10 || _loc2==10) { 
 //BA.debugLineNum = 471;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 474;BA.debugLine="If loc1=11 Or loc2=11 Then";
if (_loc1==11 || _loc2==11) { 
 //BA.debugLineNum = 475;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 479;BA.debugLine="If loc1=12 Or loc2=12 Then";
if (_loc1==12 || _loc2==12) { 
 //BA.debugLineNum = 480;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 483;BA.debugLine="If loc1=13 Or loc2=13 Then";
if (_loc1==13 || _loc2==13) { 
 //BA.debugLineNum = 484;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 487;BA.debugLine="If loc1=14 Or loc2=14 Then";
if (_loc1==14 || _loc2==14) { 
 //BA.debugLineNum = 488;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 491;BA.debugLine="If loc1=15 Or loc2=15 Then";
if (_loc1==15 || _loc2==15) { 
 //BA.debugLineNum = 492;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 495;BA.debugLine="If loc1=16 Or loc2=16 Then";
if (_loc1==16 || _loc2==16) { 
 //BA.debugLineNum = 496;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 499;BA.debugLine="If loc1=17 Or loc2=17 Then";
if (_loc1==17 || _loc2==17) { 
 //BA.debugLineNum = 500;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 503;BA.debugLine="If loc1=18 Or loc2=18 Then";
if (_loc1==18 || _loc2==18) { 
 //BA.debugLineNum = 504;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 507;BA.debugLine="If loc1=19 Or loc2=19 Then";
if (_loc1==19 || _loc2==19) { 
 //BA.debugLineNum = 508;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 511;BA.debugLine="If loc1=20 Or loc2=20 Then";
if (_loc1==20 || _loc2==20) { 
 //BA.debugLineNum = 512;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 515;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 517;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim button7 As Button";
mostCurrent._button7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim button8 As Button";
mostCurrent._button8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim button9 As Button";
mostCurrent._button9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim button10 As Button";
mostCurrent._button10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim button11 As Button";
mostCurrent._button11 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim button12 As Button";
mostCurrent._button12 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim button13 As Button";
mostCurrent._button13 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim button14 As Button";
mostCurrent._button14 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim button15 As Button";
mostCurrent._button15 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim button16 As Button";
mostCurrent._button16 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim button17 As Button";
mostCurrent._button17 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim button18 As Button";
mostCurrent._button18 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim button19 As Button";
mostCurrent._button19 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim button20 As Button";
mostCurrent._button20 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim clicks As Int";
_clicks = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim label9 As Label";
mostCurrent._label9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim label11 As Label";
mostCurrent._label11 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim label14 As Label";
mostCurrent._label14 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim label15 As Label";
mostCurrent._label15 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim label16 As Label";
mostCurrent._label16 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim label17 As Label";
mostCurrent._label17 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim label18 As Label";
mostCurrent._label18 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim label19 As Label";
mostCurrent._label19 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim label20 As Label";
mostCurrent._label20 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim loc(20) As Int";
_loc = new int[(int) (20)];
;
 //BA.debugLineNum = 64;BA.debugLine="Dim sel(20) As Int";
_sel = new int[(int) (20)];
;
 //BA.debugLineNum = 65;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 66;BA.debugLine="Dim y As Int";
_y = 0;
 //BA.debugLineNum = 67;BA.debugLine="Dim z As Int";
_z = 0;
 //BA.debugLineNum = 68;BA.debugLine="Dim Select1 As Int";
_select1 = 0;
 //BA.debugLineNum = 69;BA.debugLine="Dim select2 As Int";
_select2 = 0;
 //BA.debugLineNum = 70;BA.debugLine="Dim loc1 As Int";
_loc1 = 0;
 //BA.debugLineNum = 71;BA.debugLine="Dim loc2 As Int";
_loc2 = 0;
 //BA.debugLineNum = 72;BA.debugLine="Dim Canvas1 As Canvas";
mostCurrent._canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim busy As String";
mostCurrent._busy = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim bt As Int";
_bt = 0;
 //BA.debugLineNum = 75;BA.debugLine="Dim lc As Int";
_lc = 0;
 //BA.debugLineNum = 76;BA.debugLine="Dim match As Int";
_match = 0;
 //BA.debugLineNum = 77;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Dim ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim ImageView3 As ImageView";
mostCurrent._imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim ImageView4 As ImageView";
mostCurrent._imageview4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim ImageView5 As ImageView";
mostCurrent._imageview5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim ImageView6 As ImageView";
mostCurrent._imageview6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Dim ImageView7 As ImageView";
mostCurrent._imageview7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Dim ImageView8 As ImageView";
mostCurrent._imageview8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Dim ImageView9 As ImageView";
mostCurrent._imageview9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Dim ImageView10 As ImageView";
mostCurrent._imageview10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Dim ImageView11 As ImageView";
mostCurrent._imageview11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Dim ImageView12 As ImageView";
mostCurrent._imageview12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Dim ImageView13 As ImageView";
mostCurrent._imageview13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Dim ImageView14 As ImageView";
mostCurrent._imageview14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Dim ImageView15 As ImageView";
mostCurrent._imageview15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Dim ImageView16 As ImageView";
mostCurrent._imageview16 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Dim ImageView17 As ImageView";
mostCurrent._imageview17 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Dim ImageView18 As ImageView";
mostCurrent._imageview18 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Dim ImageView19 As ImageView";
mostCurrent._imageview19 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Dim ImageView20 As ImageView";
mostCurrent._imageview20 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Dim one As Int";
_one = 0;
 //BA.debugLineNum = 98;BA.debugLine="Dim two As Int";
_two = 0;
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _mix() throws Exception{
 //BA.debugLineNum = 697;BA.debugLine="Sub mix";
 //BA.debugLineNum = 699;BA.debugLine="If loc(x)=0 Then";
if (_loc[_x]==0) { 
 //BA.debugLineNum = 700;BA.debugLine="y=Rnd(0,20)";
_y = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (20));
 };
 //BA.debugLineNum = 703;BA.debugLine="If sel(y) >0 And loc(x)=0 Then";
if (_sel[_y]>0 && _loc[_x]==0) { 
 //BA.debugLineNum = 704;BA.debugLine="loc(x)=sel(y)";
_loc[_x] = _sel[_y];
 //BA.debugLineNum = 705;BA.debugLine="sel(y)=0";
_sel[_y] = (int) (0);
 };
 //BA.debugLineNum = 708;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Timer1 As Timer";
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
public static String  _restart() throws Exception{
int _a = 0;
String _debug = "";
anywheresoftware.b4a.objects.PanelWrapper _panels = null;
 //BA.debugLineNum = 162;BA.debugLine="Sub restart";
 //BA.debugLineNum = 165;BA.debugLine="For x=0 To 19";
{
final int step1 = 1;
final int limit1 = (int) (19);
for (_x = (int) (0) ; (step1 > 0 && _x <= limit1) || (step1 < 0 && _x >= limit1); _x = ((int)(0 + _x + step1)) ) {
 //BA.debugLineNum = 166;BA.debugLine="loc(x)=0";
_loc[_x] = (int) (0);
 }
};
 //BA.debugLineNum = 170;BA.debugLine="For x=0 To 19";
{
final int step4 = 1;
final int limit4 = (int) (19);
for (_x = (int) (0) ; (step4 > 0 && _x <= limit4) || (step4 < 0 && _x >= limit4); _x = ((int)(0 + _x + step4)) ) {
 //BA.debugLineNum = 171;BA.debugLine="loc(x)=0";
_loc[_x] = (int) (0);
 }
};
 //BA.debugLineNum = 174;BA.debugLine="Dim a As Int";
_a = 0;
 //BA.debugLineNum = 176;BA.debugLine="For a=0 To 9";
{
final int step8 = 1;
final int limit8 = (int) (9);
for (_a = (int) (0) ; (step8 > 0 && _a <= limit8) || (step8 < 0 && _a >= limit8); _a = ((int)(0 + _a + step8)) ) {
 //BA.debugLineNum = 177;BA.debugLine="sel(a)=a+1";
_sel[_a] = (int) (_a+1);
 }
};
 //BA.debugLineNum = 180;BA.debugLine="For a=10 To 19";
{
final int step11 = 1;
final int limit11 = (int) (19);
for (_a = (int) (10) ; (step11 > 0 && _a <= limit11) || (step11 < 0 && _a >= limit11); _a = ((int)(0 + _a + step11)) ) {
 //BA.debugLineNum = 181;BA.debugLine="sel(a)=a-9";
_sel[_a] = (int) (_a-9);
 }
};
 //BA.debugLineNum = 185;BA.debugLine="For z=1 To 100";
{
final int step14 = 1;
final int limit14 = (int) (100);
for (_z = (int) (1) ; (step14 > 0 && _z <= limit14) || (step14 < 0 && _z >= limit14); _z = ((int)(0 + _z + step14)) ) {
 //BA.debugLineNum = 187;BA.debugLine="For x=0 To 19";
{
final int step15 = 1;
final int limit15 = (int) (19);
for (_x = (int) (0) ; (step15 > 0 && _x <= limit15) || (step15 < 0 && _x >= limit15); _x = ((int)(0 + _x + step15)) ) {
 //BA.debugLineNum = 189;BA.debugLine="If loc(x)=0 Then";
if (_loc[_x]==0) { 
 //BA.debugLineNum = 190;BA.debugLine="mix";
_mix();
 };
 }
};
 }
};
 //BA.debugLineNum = 198;BA.debugLine="Dim debug As String";
_debug = "";
 //BA.debugLineNum = 200;BA.debugLine="debug=\"\"";
_debug = "";
 //BA.debugLineNum = 202;BA.debugLine="If debug=\"debug\" Then";
if ((_debug).equals("debug")) { 
 //BA.debugLineNum = 204;BA.debugLine="label1.Text=loc(0)";
mostCurrent._label1.setText((Object)(_loc[(int) (0)]));
 //BA.debugLineNum = 205;BA.debugLine="label2.Text=loc(1)";
mostCurrent._label2.setText((Object)(_loc[(int) (1)]));
 //BA.debugLineNum = 206;BA.debugLine="label3.Text=loc(2)";
mostCurrent._label3.setText((Object)(_loc[(int) (2)]));
 //BA.debugLineNum = 207;BA.debugLine="label4.Text=loc(3)";
mostCurrent._label4.setText((Object)(_loc[(int) (3)]));
 //BA.debugLineNum = 208;BA.debugLine="label5.Text=loc(4)";
mostCurrent._label5.setText((Object)(_loc[(int) (4)]));
 //BA.debugLineNum = 209;BA.debugLine="label6.Text=loc(5)";
mostCurrent._label6.setText((Object)(_loc[(int) (5)]));
 //BA.debugLineNum = 210;BA.debugLine="label7.Text=loc(6)";
mostCurrent._label7.setText((Object)(_loc[(int) (6)]));
 //BA.debugLineNum = 211;BA.debugLine="label8.Text=loc(7)";
mostCurrent._label8.setText((Object)(_loc[(int) (7)]));
 //BA.debugLineNum = 212;BA.debugLine="label9.Text=loc(8)";
mostCurrent._label9.setText((Object)(_loc[(int) (8)]));
 //BA.debugLineNum = 213;BA.debugLine="label10.Text=loc(9)";
mostCurrent._label10.setText((Object)(_loc[(int) (9)]));
 //BA.debugLineNum = 214;BA.debugLine="label11.Text=loc(10)";
mostCurrent._label11.setText((Object)(_loc[(int) (10)]));
 //BA.debugLineNum = 215;BA.debugLine="label12.Text=loc(11)";
mostCurrent._label12.setText((Object)(_loc[(int) (11)]));
 //BA.debugLineNum = 216;BA.debugLine="label13.Text=loc(12)";
mostCurrent._label13.setText((Object)(_loc[(int) (12)]));
 //BA.debugLineNum = 217;BA.debugLine="label14.Text=loc(13)";
mostCurrent._label14.setText((Object)(_loc[(int) (13)]));
 //BA.debugLineNum = 218;BA.debugLine="label15.Text=loc(14)";
mostCurrent._label15.setText((Object)(_loc[(int) (14)]));
 //BA.debugLineNum = 219;BA.debugLine="label16.Text=loc(15)";
mostCurrent._label16.setText((Object)(_loc[(int) (15)]));
 //BA.debugLineNum = 220;BA.debugLine="label17.Text=loc(16)";
mostCurrent._label17.setText((Object)(_loc[(int) (16)]));
 //BA.debugLineNum = 221;BA.debugLine="label18.Text=loc(17)";
mostCurrent._label18.setText((Object)(_loc[(int) (17)]));
 //BA.debugLineNum = 222;BA.debugLine="label19.Text=loc(18)";
mostCurrent._label19.setText((Object)(_loc[(int) (18)]));
 //BA.debugLineNum = 223;BA.debugLine="label20.Text=loc(19)";
mostCurrent._label20.setText((Object)(_loc[(int) (19)]));
 };
 //BA.debugLineNum = 227;BA.debugLine="clicks=0";
_clicks = (int) (0);
 //BA.debugLineNum = 228;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="loc1=0";
_loc1 = (int) (0);
 //BA.debugLineNum = 230;BA.debugLine="loc2=0";
_loc2 = (int) (0);
 //BA.debugLineNum = 233;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 234;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 235;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 236;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 237;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 238;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 239;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 240;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 241;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 242;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 243;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 244;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 245;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 246;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 247;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 248;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 249;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 250;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 251;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 252;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 256;BA.debugLine="Dim panels As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 257;BA.debugLine="panels.Initialize(\"panels\")";
_panels.Initialize(mostCurrent.activityBA,"panels");
 //BA.debugLineNum = 258;BA.debugLine="Activity.AddView(panels,60%x,25%y,30%x,40%y)";
mostCurrent._activity.AddView((android.view.View)(_panels.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (60),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 259;BA.debugLine="ImageView1.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (0)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 260;BA.debugLine="ImageView2.SetBackgroundImage(LoadBitmapSampl";
mostCurrent._imageview2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (1)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 261;BA.debugLine="ImageView3.SetBackgroundImage(LoadBitmapSample";
mostCurrent._imageview3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (2)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 262;BA.debugLine="ImageView4.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (3)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 263;BA.debugLine="ImageView5.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (4)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 264;BA.debugLine="ImageView6.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (5)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 265;BA.debugLine="ImageView7.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview7.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (6)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 266;BA.debugLine="ImageView8.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (7)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 267;BA.debugLine="ImageView9.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._imageview9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (8)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 268;BA.debugLine="ImageView10.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (9)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 269;BA.debugLine="ImageView11.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview11.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (10)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 270;BA.debugLine="ImageView12.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview12.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (11)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 271;BA.debugLine="ImageView13.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview13.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (12)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 272;BA.debugLine="ImageView14.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview14.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (13)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 273;BA.debugLine="ImageView15.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview15.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (14)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 274;BA.debugLine="ImageView16.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview16.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (15)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 275;BA.debugLine="ImageView17.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview17.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (16)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 276;BA.debugLine="ImageView18.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview18.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (17)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 277;BA.debugLine="ImageView19.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview19.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (18)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 278;BA.debugLine="ImageView20.SetBackgroundImage(LoadBitmapSample(";
mostCurrent._imageview20.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (19)])+".png",_panels.getWidth(),_panels.getHeight()).getObject()));
 //BA.debugLineNum = 283;BA.debugLine="ImageView1.SendToBack";
mostCurrent._imageview1.SendToBack();
 //BA.debugLineNum = 284;BA.debugLine="ImageView2.SendToBack";
mostCurrent._imageview2.SendToBack();
 //BA.debugLineNum = 285;BA.debugLine="ImageView3.SendToBack";
mostCurrent._imageview3.SendToBack();
 //BA.debugLineNum = 286;BA.debugLine="ImageView4.SendToBack";
mostCurrent._imageview4.SendToBack();
 //BA.debugLineNum = 287;BA.debugLine="ImageView5.SendToBack";
mostCurrent._imageview5.SendToBack();
 //BA.debugLineNum = 288;BA.debugLine="ImageView6.SendToBack";
mostCurrent._imageview6.SendToBack();
 //BA.debugLineNum = 289;BA.debugLine="ImageView7.SendToBack";
mostCurrent._imageview7.SendToBack();
 //BA.debugLineNum = 290;BA.debugLine="ImageView8.SendToBack";
mostCurrent._imageview8.SendToBack();
 //BA.debugLineNum = 291;BA.debugLine="ImageView9.SendToBack";
mostCurrent._imageview9.SendToBack();
 //BA.debugLineNum = 292;BA.debugLine="ImageView10.SendToBack";
mostCurrent._imageview10.SendToBack();
 //BA.debugLineNum = 293;BA.debugLine="ImageView11.SendToBack";
mostCurrent._imageview11.SendToBack();
 //BA.debugLineNum = 294;BA.debugLine="ImageView12.SendToBack";
mostCurrent._imageview12.SendToBack();
 //BA.debugLineNum = 295;BA.debugLine="ImageView13.SendToBack";
mostCurrent._imageview13.SendToBack();
 //BA.debugLineNum = 296;BA.debugLine="ImageView14.SendToBack";
mostCurrent._imageview14.SendToBack();
 //BA.debugLineNum = 297;BA.debugLine="ImageView15.SendToBack";
mostCurrent._imageview15.SendToBack();
 //BA.debugLineNum = 298;BA.debugLine="ImageView16.SendToBack";
mostCurrent._imageview16.SendToBack();
 //BA.debugLineNum = 299;BA.debugLine="ImageView17.SendToBack";
mostCurrent._imageview17.SendToBack();
 //BA.debugLineNum = 300;BA.debugLine="ImageView18.SendToBack";
mostCurrent._imageview18.SendToBack();
 //BA.debugLineNum = 301;BA.debugLine="ImageView19.SendToBack";
mostCurrent._imageview19.SendToBack();
 //BA.debugLineNum = 302;BA.debugLine="ImageView20.SendToBack";
mostCurrent._imageview20.SendToBack();
 //BA.debugLineNum = 304;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 306;BA.debugLine="match=0";
_match = (int) (0);
 //BA.debugLineNum = 307;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 606;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 609;BA.debugLine="If loc1=1 Or loc2=1 Then";
if (_loc1==1 || _loc2==1) { 
 //BA.debugLineNum = 610;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 613;BA.debugLine="If loc1=2 Or loc2=2 Then";
if (_loc1==2 || _loc2==2) { 
 //BA.debugLineNum = 614;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 617;BA.debugLine="If loc1=3 Or loc2=3 Then";
if (_loc1==3 || _loc2==3) { 
 //BA.debugLineNum = 618;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 621;BA.debugLine="If loc1=4 Or loc2=4 Then";
if (_loc1==4 || _loc2==4) { 
 //BA.debugLineNum = 622;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 625;BA.debugLine="If loc1=5 Or loc2=5 Then";
if (_loc1==5 || _loc2==5) { 
 //BA.debugLineNum = 626;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 629;BA.debugLine="If loc1=6 Or loc2=6 Then";
if (_loc1==6 || _loc2==6) { 
 //BA.debugLineNum = 630;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 633;BA.debugLine="If loc1=7 Or loc2=7 Then";
if (_loc1==7 || _loc2==7) { 
 //BA.debugLineNum = 634;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 637;BA.debugLine="If loc1=8 Or loc2=8 Then";
if (_loc1==8 || _loc2==8) { 
 //BA.debugLineNum = 638;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 641;BA.debugLine="If loc1=9 Or loc2=9 Then";
if (_loc1==9 || _loc2==9) { 
 //BA.debugLineNum = 642;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 645;BA.debugLine="If loc1=10 Or loc2=10 Then";
if (_loc1==10 || _loc2==10) { 
 //BA.debugLineNum = 646;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 649;BA.debugLine="If loc1=11 Or loc2=11 Then";
if (_loc1==11 || _loc2==11) { 
 //BA.debugLineNum = 650;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 654;BA.debugLine="If loc1=12 Or loc2=12 Then";
if (_loc1==12 || _loc2==12) { 
 //BA.debugLineNum = 655;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 658;BA.debugLine="If loc1=13 Or loc2=13 Then";
if (_loc1==13 || _loc2==13) { 
 //BA.debugLineNum = 659;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 662;BA.debugLine="If loc1=14 Or loc2=14 Then";
if (_loc1==14 || _loc2==14) { 
 //BA.debugLineNum = 663;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 666;BA.debugLine="If loc1=15 Or loc2=15 Then";
if (_loc1==15 || _loc2==15) { 
 //BA.debugLineNum = 667;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 670;BA.debugLine="If loc1=16 Or loc2=16 Then";
if (_loc1==16 || _loc2==16) { 
 //BA.debugLineNum = 671;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 674;BA.debugLine="If loc1=17 Or loc2=17 Then";
if (_loc1==17 || _loc2==17) { 
 //BA.debugLineNum = 675;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 678;BA.debugLine="If loc1=18 Or loc2=18 Then";
if (_loc1==18 || _loc2==18) { 
 //BA.debugLineNum = 679;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 682;BA.debugLine="If loc1=19 Or loc2=19 Then";
if (_loc1==19 || _loc2==19) { 
 //BA.debugLineNum = 683;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 686;BA.debugLine="If loc1=20 Or loc2=20 Then";
if (_loc1==20 || _loc2==20) { 
 //BA.debugLineNum = 687;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 690;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 692;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 696;BA.debugLine="End Sub";
return "";
}
public static String  _timer2_tick() throws Exception{
 //BA.debugLineNum = 520;BA.debugLine="Sub Timer2_Tick";
 //BA.debugLineNum = 522;BA.debugLine="If one=1  Or two=1 Then";
if (_one==1 || _two==1) { 
 //BA.debugLineNum = 523;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, \"B";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Black.png").getObject()));
 };
 //BA.debugLineNum = 526;BA.debugLine="If one=2  Or two=2 Then";
if (_one==2 || _two==2) { 
 //BA.debugLineNum = 527;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 530;BA.debugLine="If one=3  Or two=3 Then";
if (_one==3 || _two==3) { 
 //BA.debugLineNum = 531;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 534;BA.debugLine="If one=4  Or two=4 Then";
if (_one==4 || _two==4) { 
 //BA.debugLineNum = 535;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 538;BA.debugLine="If one=5  Or two=5 Then";
if (_one==5 || _two==5) { 
 //BA.debugLineNum = 539;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 542;BA.debugLine="If one=6  Or two=6 Then";
if (_one==6 || _two==6) { 
 //BA.debugLineNum = 543;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 546;BA.debugLine="If one=7  Or two=7 Then";
if (_one==7 || _two==7) { 
 //BA.debugLineNum = 547;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 550;BA.debugLine="If one=8  Or two=8 Then";
if (_one==8 || _two==8) { 
 //BA.debugLineNum = 551;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 554;BA.debugLine="If one=9  Or two=9 Then";
if (_one==9 || _two==9) { 
 //BA.debugLineNum = 555;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 558;BA.debugLine="If one=10 Or two=10 Then";
if (_one==10 || _two==10) { 
 //BA.debugLineNum = 559;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 562;BA.debugLine="If one=11  Or two=11 Then";
if (_one==11 || _two==11) { 
 //BA.debugLineNum = 563;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 566;BA.debugLine="If one=12  Or two=12 Then";
if (_one==12 || _two==12) { 
 //BA.debugLineNum = 567;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 570;BA.debugLine="If one=13  Or two=13 Then";
if (_one==13 || _two==13) { 
 //BA.debugLineNum = 571;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 574;BA.debugLine="If one=14  Or two=14 Then";
if (_one==14 || _two==14) { 
 //BA.debugLineNum = 575;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 578;BA.debugLine="If one=15  Or two=15 Then";
if (_one==15 || _two==15) { 
 //BA.debugLineNum = 579;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 582;BA.debugLine="If one=16  Or two=16 Then";
if (_one==16 || _two==16) { 
 //BA.debugLineNum = 583;BA.debugLine="ImageView16.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview16.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 586;BA.debugLine="If one=17  Or two=17 Then";
if (_one==17 || _two==17) { 
 //BA.debugLineNum = 587;BA.debugLine="ImageView17.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview17.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 590;BA.debugLine="If one=18  Or two=18 Then";
if (_one==18 || _two==18) { 
 //BA.debugLineNum = 591;BA.debugLine="ImageView18.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview18.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 594;BA.debugLine="If one=19  Or two=19 Then";
if (_one==19 || _two==19) { 
 //BA.debugLineNum = 595;BA.debugLine="ImageView19.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview19.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 598;BA.debugLine="If one=20  Or two=20 Then";
if (_one==20 || _two==20) { 
 //BA.debugLineNum = 599;BA.debugLine="ImageView20.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview20.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.png").getObject()));
 };
 //BA.debugLineNum = 602;BA.debugLine="Timer2.Enabled=False";
_timer2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 603;BA.debugLine="End Sub";
return "";
}
public static String  _timer3_tick() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub timer3_Tick";
 //BA.debugLineNum = 146;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
}
