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

public class writing extends Activity implements B4AActivity{
	public static writing mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.writing");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (writing).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.writing");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.writing", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (writing) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (writing) Resume **");
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
		return writing.class;
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
        BA.LogInfo("** Activity (writing) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (writing) Resume **");
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
public static anywheresoftware.b4a.obejcts.TTS _tts = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _clicksound = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _introwel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _b7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i10 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i11 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i12 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i13 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i14 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i15 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i16 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i17 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i18 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i19 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i20 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i21 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i22 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i23 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i24 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i25 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _i26 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnb = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnc = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnd = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btne = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnf = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btng = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnh = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btni = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnj = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnk = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnl = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnm = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnn = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btno = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnp = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnq = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnr = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btns = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnt = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnu = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnv = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnw = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnx = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btny = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnz = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _iplay = null;
public anywheresoftware.b4a.objects.ButtonWrapper _play_btn = null;
public fabricviewwrapper.fabricViewWrapper _fv1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_clear = null;
public anywheresoftware.b4a.objects.LabelWrapper _draw_lbl = null;
public b4a.example.main _main = null;
public b4a.example.menu _menu = null;
public b4a.example.number _number = null;
public b4a.example.number_writing _number_writing = null;
public b4a.example.alphabet _alphabet = null;
public b4a.example.cat_alphabet _cat_alphabet = null;
public b4a.example.cat_num _cat_num = null;
public b4a.example.cat_fun _cat_fun = null;
public b4a.example.scramble _scramble = null;
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
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 46;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"abc.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"abc.mp3");
 //BA.debugLineNum = 47;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
_timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 48;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 51;BA.debugLine="Activity.LoadLayout(\"writing\")";
mostCurrent._activity.LoadLayout("writing",mostCurrent.activityBA);
 //BA.debugLineNum = 53;BA.debugLine="fv1.BackgroundMode = fv1.BACKGROUND_STYLE_BLANK";
mostCurrent._fv1.setBackgroundMode(mostCurrent._fv1.BACKGROUND_STYLE_BLANK);
 //BA.debugLineNum = 54;BA.debugLine="fv1.DrawColor = Colors.Red";
mostCurrent._fv1.setDrawColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 55;BA.debugLine="fv1.DrawBackgroundColor = Colors.Transparent";
mostCurrent._fv1.setDrawBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 56;BA.debugLine="fv1.NoteBookLeftLineColor = Colors.Blue";
mostCurrent._fv1.setNoteBookLeftLineColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 57;BA.debugLine="fv1.NoteBookLeftLinePadding = 60";
mostCurrent._fv1.setNoteBookLeftLinePadding((int) (60));
 //BA.debugLineNum = 58;BA.debugLine="fv1.Size=30";
mostCurrent._fv1.setSize((float) (30));
 //BA.debugLineNum = 59;BA.debugLine="fv1.FabricLineColor = Colors.Yellow";
mostCurrent._fv1.setFabricLineColor(anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 62;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 65;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 68;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 70;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 72;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 73;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 74;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 75;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 76;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 77;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 78;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 79;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 80;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 81;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 82;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 83;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 87;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 101;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 102;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 94;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 95;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 96;BA.debugLine="timer1_Tick 'don't wait one second for the UI to";
_timer1_tick();
 //BA.debugLineNum = 97;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _b3_click() throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub b3_Click";
 //BA.debugLineNum = 123;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 124;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 126;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 127;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 128;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 129;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 131;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 132;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 134;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 135;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 142;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 143;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 144;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 146;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 148;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 149;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 150;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 151;BA.debugLine="i1.Visible=True";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _btn_clear_click() throws Exception{
 //BA.debugLineNum = 1011;BA.debugLine="Sub btn_clear_Click";
 //BA.debugLineNum = 1012;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 1013;BA.debugLine="End Sub";
return "";
}
public static String  _btnb_click() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub btnB_Click";
 //BA.debugLineNum = 158;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 160;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 162;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 163;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 165;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 167;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 168;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 170;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 171;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 172;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 174;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 175;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 176;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 177;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 178;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 179;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 180;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 181;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 183;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 184;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 185;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 186;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 187;BA.debugLine="i2.Visible=True";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _btnc_click() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Sub btnC_Click";
 //BA.debugLineNum = 194;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 196;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 199;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 200;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 201;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 202;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 203;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 204;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 206;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 210;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 211;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 213;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 214;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 215;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 216;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 218;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 220;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 221;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 222;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="i3.Visible=True";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 228;BA.debugLine="End Sub";
return "";
}
public static String  _btnd_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub btnD_Click";
 //BA.debugLineNum = 232;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 233;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 235;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 236;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 237;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 240;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 242;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 244;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 245;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 246;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 247;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 248;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 249;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 250;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 251;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 254;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 255;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 256;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 257;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 258;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 260;BA.debugLine="i4.Visible=True";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _btne_click() throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub btnE_Click";
 //BA.debugLineNum = 267;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 269;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 270;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 272;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 273;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 274;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 280;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 281;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 284;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 286;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 287;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 288;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 290;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 291;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 293;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 294;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="i5.Visible=True";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _btnf_click() throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Sub btnF_Click";
 //BA.debugLineNum = 302;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 304;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 306;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 307;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 308;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 309;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 310;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 311;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 312;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 315;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 316;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 317;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 318;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 319;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 320;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 321;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 322;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 323;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 324;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 325;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 326;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 330;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 331;BA.debugLine="i6.Visible=True";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static String  _btng_click() throws Exception{
 //BA.debugLineNum = 337;BA.debugLine="Sub btnG_Click";
 //BA.debugLineNum = 338;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 339;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 340;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 341;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 342;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 343;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 344;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 345;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 346;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 347;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 348;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 349;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 351;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 352;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 353;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 354;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 355;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 356;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 357;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 358;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 359;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 360;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 361;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 362;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 363;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 364;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 365;BA.debugLine="i7.Visible=True";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 369;BA.debugLine="End Sub";
return "";
}
public static String  _btnh_click() throws Exception{
 //BA.debugLineNum = 371;BA.debugLine="Sub btnH_Click";
 //BA.debugLineNum = 372;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 373;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 374;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 375;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 376;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 377;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 378;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 379;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 380;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 381;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 382;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 383;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 384;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 385;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 387;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 388;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 389;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 390;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 391;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 392;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 393;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 394;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 395;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 396;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 397;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 398;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 399;BA.debugLine="i8.Visible=True";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 403;BA.debugLine="End Sub";
return "";
}
public static String  _btni_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub btnI_Click";
 //BA.debugLineNum = 406;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 407;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 408;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 409;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 410;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 411;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 412;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 413;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 416;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 417;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 418;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 419;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 422;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 423;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 424;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 425;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 426;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 427;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 428;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 429;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 430;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 431;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 432;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 433;BA.debugLine="i9.Visible=True";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
return "";
}
public static String  _btnj_click() throws Exception{
 //BA.debugLineNum = 439;BA.debugLine="Sub btnJ_Click";
 //BA.debugLineNum = 440;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 441;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 442;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 443;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 444;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 445;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 446;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 447;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 448;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 449;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 450;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 451;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 452;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 453;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 455;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 456;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 457;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 458;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 459;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 460;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 461;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 462;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 463;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 464;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 465;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 466;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 467;BA.debugLine="i10.Visible=True";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 471;BA.debugLine="End Sub";
return "";
}
public static String  _btnk_click() throws Exception{
 //BA.debugLineNum = 473;BA.debugLine="Sub btnK_Click";
 //BA.debugLineNum = 474;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 475;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 476;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 477;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 478;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 479;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 480;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 481;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 482;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 483;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 484;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 485;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 486;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 487;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 488;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 489;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 490;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 491;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 492;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 493;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 494;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 495;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 496;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 497;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 498;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 499;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 500;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 501;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 502;BA.debugLine="i11.Visible=True";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 506;BA.debugLine="End Sub";
return "";
}
public static String  _btnl_click() throws Exception{
 //BA.debugLineNum = 508;BA.debugLine="Sub btnL_Click";
 //BA.debugLineNum = 509;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 510;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 511;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 512;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 513;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 514;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 515;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 516;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 517;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 518;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 519;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 520;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 521;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 522;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 523;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 524;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 525;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 526;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 527;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 529;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 530;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 531;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 532;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 533;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 534;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 535;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 536;BA.debugLine="i12.Visible=True";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 540;BA.debugLine="End Sub";
return "";
}
public static String  _btnm_click() throws Exception{
 //BA.debugLineNum = 542;BA.debugLine="Sub btnM_Click";
 //BA.debugLineNum = 543;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 544;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 545;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 546;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 547;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 548;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 549;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 550;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 551;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 552;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 553;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 554;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 555;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 556;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 557;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 558;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 559;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 560;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 561;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 562;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 563;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 564;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 565;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 566;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 567;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 568;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 569;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 570;BA.debugLine="i13.Visible=True";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 574;BA.debugLine="End Sub";
return "";
}
public static String  _btnn_click() throws Exception{
 //BA.debugLineNum = 576;BA.debugLine="Sub btnN_Click";
 //BA.debugLineNum = 577;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 578;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 579;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 580;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 581;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 582;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 583;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 584;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 585;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 586;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 588;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 589;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 590;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 591;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 592;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 593;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 594;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 595;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 596;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 597;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 598;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 599;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 600;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 601;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 602;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 603;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 604;BA.debugLine="i14.Visible=True";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 607;BA.debugLine="End Sub";
return "";
}
public static String  _btno_click() throws Exception{
 //BA.debugLineNum = 609;BA.debugLine="Sub btnO_Click";
 //BA.debugLineNum = 610;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 611;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 612;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 613;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 614;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 615;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 616;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 617;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 618;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 619;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 620;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 621;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 622;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 623;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 624;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 625;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 626;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 627;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 628;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 629;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 630;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 631;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 632;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 633;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 634;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 635;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 636;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 637;BA.debugLine="i15.Visible=True";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 640;BA.debugLine="End Sub";
return "";
}
public static String  _btnp_click() throws Exception{
 //BA.debugLineNum = 642;BA.debugLine="Sub btnP_Click";
 //BA.debugLineNum = 643;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 644;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 645;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 646;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 647;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 648;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 649;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 650;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 651;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 652;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 653;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 654;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 655;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 656;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 657;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 658;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 659;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 660;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 661;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 662;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 663;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 664;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 665;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 666;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 667;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 668;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 669;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 670;BA.debugLine="i16.Visible=True";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 673;BA.debugLine="End Sub";
return "";
}
public static String  _btnq_click() throws Exception{
 //BA.debugLineNum = 675;BA.debugLine="Sub btnQ_Click";
 //BA.debugLineNum = 676;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 677;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 678;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 679;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 680;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 681;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 682;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 683;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 684;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 685;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 686;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 687;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 688;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 689;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 690;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 691;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 692;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 693;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 694;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 695;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 696;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 697;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 698;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 699;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 700;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 701;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 702;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 703;BA.debugLine="i17.Visible=True";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 706;BA.debugLine="End Sub";
return "";
}
public static String  _btnr_click() throws Exception{
 //BA.debugLineNum = 708;BA.debugLine="Sub btnR_Click";
 //BA.debugLineNum = 709;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 710;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 711;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 712;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 713;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 714;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 715;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 716;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 717;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 718;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 719;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 720;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 721;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 722;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 723;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 724;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 725;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 726;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 727;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 728;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 729;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 730;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 731;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 732;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 733;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 734;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 735;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 736;BA.debugLine="i18.Visible=True";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _btns_click() throws Exception{
 //BA.debugLineNum = 741;BA.debugLine="Sub btnS_Click";
 //BA.debugLineNum = 742;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 743;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 744;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 745;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 746;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 747;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 748;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 749;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 750;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 751;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 752;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 753;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 754;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 755;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 756;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 757;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 758;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 759;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 760;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 761;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 762;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 763;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 764;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 765;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 766;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 767;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 768;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 769;BA.debugLine="i19.Visible=True";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 772;BA.debugLine="End Sub";
return "";
}
public static String  _btnt_click() throws Exception{
 //BA.debugLineNum = 774;BA.debugLine="Sub btnT_Click";
 //BA.debugLineNum = 775;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 776;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 777;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 778;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 779;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 780;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 781;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 782;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 783;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 784;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 785;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 786;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 787;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 788;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 789;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 790;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 791;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 792;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 793;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 794;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 795;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 796;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 797;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 798;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 799;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 800;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 801;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 802;BA.debugLine="i20.Visible=True";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 805;BA.debugLine="End Sub";
return "";
}
public static String  _btnu_click() throws Exception{
 //BA.debugLineNum = 807;BA.debugLine="Sub btnU_Click";
 //BA.debugLineNum = 808;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 809;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 810;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 811;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 812;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 813;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 814;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 815;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 816;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 817;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 818;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 819;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 820;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 821;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 822;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 823;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 824;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 825;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 826;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 827;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 828;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 829;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 830;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 831;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 832;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 833;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 834;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 835;BA.debugLine="i21.Visible=True";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 837;BA.debugLine="End Sub";
return "";
}
public static String  _btnv_click() throws Exception{
 //BA.debugLineNum = 839;BA.debugLine="Sub btnV_Click";
 //BA.debugLineNum = 840;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 841;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 842;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 843;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 844;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 845;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 846;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 847;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 848;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 849;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 850;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 851;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 852;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 853;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 854;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 855;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 856;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 857;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 858;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 859;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 860;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 861;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 862;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 863;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 864;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 865;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 866;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 867;BA.debugLine="i22.Visible=True";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 870;BA.debugLine="End Sub";
return "";
}
public static String  _btnw_click() throws Exception{
 //BA.debugLineNum = 872;BA.debugLine="Sub btnW_Click";
 //BA.debugLineNum = 873;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 874;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 875;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 876;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 877;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 878;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 879;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 880;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 881;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 882;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 883;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 884;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 885;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 886;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 887;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 888;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 889;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 890;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 891;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 892;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 893;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 894;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 895;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 896;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 897;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 898;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 899;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 900;BA.debugLine="i23.Visible=True";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 903;BA.debugLine="End Sub";
return "";
}
public static String  _btnx_click() throws Exception{
 //BA.debugLineNum = 905;BA.debugLine="Sub btnX_Click";
 //BA.debugLineNum = 906;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 907;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 908;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 909;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 910;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 911;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 912;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 913;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 914;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 915;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 916;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 917;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 918;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 919;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 920;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 921;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 922;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 923;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 924;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 925;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 926;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 927;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 928;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 929;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 930;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 931;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 932;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 933;BA.debugLine="i24.Visible=True";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 936;BA.debugLine="End Sub";
return "";
}
public static String  _btny_click() throws Exception{
 //BA.debugLineNum = 938;BA.debugLine="Sub btnY_Click";
 //BA.debugLineNum = 939;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 940;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 941;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 942;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 943;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 944;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 945;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 946;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 947;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 948;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 949;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 950;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 951;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 952;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 953;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 954;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 955;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 956;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 957;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 958;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 959;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 960;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 961;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 962;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 963;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 964;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 965;BA.debugLine="i26.Visible=False";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 966;BA.debugLine="i25.Visible=True";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 969;BA.debugLine="End Sub";
return "";
}
public static String  _btnz_click() throws Exception{
 //BA.debugLineNum = 971;BA.debugLine="Sub btnZ_Click";
 //BA.debugLineNum = 972;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 973;BA.debugLine="fv1.cleanPage";
mostCurrent._fv1.cleanPage();
 //BA.debugLineNum = 974;BA.debugLine="i1.Visible=False";
mostCurrent._i1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 975;BA.debugLine="i2.Visible=False";
mostCurrent._i2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 976;BA.debugLine="i3.Visible=False";
mostCurrent._i3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 977;BA.debugLine="i4.Visible=False";
mostCurrent._i4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 978;BA.debugLine="i5.Visible=False";
mostCurrent._i5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 979;BA.debugLine="i6.Visible=False";
mostCurrent._i6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 980;BA.debugLine="i7.Visible=False";
mostCurrent._i7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 981;BA.debugLine="i8.Visible=False";
mostCurrent._i8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 982;BA.debugLine="i9.Visible=False";
mostCurrent._i9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 983;BA.debugLine="i11.Visible=False";
mostCurrent._i11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 984;BA.debugLine="i10.Visible=False";
mostCurrent._i10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 985;BA.debugLine="i12.Visible=False";
mostCurrent._i12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 986;BA.debugLine="i13.Visible=False";
mostCurrent._i13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 987;BA.debugLine="i14.Visible=False";
mostCurrent._i14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 988;BA.debugLine="i15.Visible=False";
mostCurrent._i15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 989;BA.debugLine="i16.Visible=False";
mostCurrent._i16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 990;BA.debugLine="i17.Visible=False";
mostCurrent._i17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 991;BA.debugLine="i18.Visible=False";
mostCurrent._i18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 992;BA.debugLine="i20.Visible=False";
mostCurrent._i20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 993;BA.debugLine="i19.Visible=False";
mostCurrent._i19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 994;BA.debugLine="i22.Visible=False";
mostCurrent._i22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 995;BA.debugLine="i21.Visible=False";
mostCurrent._i21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 996;BA.debugLine="i24.Visible=False";
mostCurrent._i24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 997;BA.debugLine="i23.Visible=False";
mostCurrent._i23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 998;BA.debugLine="i25.Visible=False";
mostCurrent._i25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 999;BA.debugLine="i26.Visible=True";
mostCurrent._i26.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1001;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 1003;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 1005;BA.debugLine="clicksound.play";
_clicksound.Play();
 //BA.debugLineNum = 1006;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1007;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 114;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 115;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 116;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 117;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 118;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 119;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim  introwel As MediaPlayer";
mostCurrent._introwel = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim b1,b2,b3,b4,b5,b6,b7 As Button";
mostCurrent._b1 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b2 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b3 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b4 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b5 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b6 = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._b7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i1";
mostCurrent._i1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i16 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i17 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i18 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i19 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i20 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i21 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i22 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i23 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i24 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i25 = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._i26 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="clicksound.Initialize2(\"clicksound\")";
_clicksound.Initialize2(processBA,"clicksound");
 //BA.debugLineNum = 22;BA.debugLine="clicksound.Load(File.DirAssets, \"click.mp3\")";
_clicksound.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"click.mp3");
 //BA.debugLineNum = 25;BA.debugLine="TTS.Initialize(\"tts\")";
_tts.Initialize(processBA,"tts");
 //BA.debugLineNum = 27;BA.debugLine="Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,b";
mostCurrent._btnb = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnc = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnd = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btne = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnf = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btng = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnh = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btni = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnj = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnk = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnl = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnm = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnn = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btno = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnp = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnq = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnr = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btns = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnt = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnu = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnv = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnw = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnx = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btny = new anywheresoftware.b4a.objects.ButtonWrapper();
mostCurrent._btnz = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private i2 As ImageView";
mostCurrent._i2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Iplay As Button";
mostCurrent._iplay = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private play_btn As Button";
mostCurrent._play_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private fv1 As FabricView";
mostCurrent._fv1 = new fabricviewwrapper.fabricViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btn_clear As Button";
mostCurrent._btn_clear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private draw_lbl As Label";
mostCurrent._draw_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 9;BA.debugLine="Public clicksound As MediaPlayer";
_clicksound = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Dim MediaPlayer1 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Dim timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 107;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
}
