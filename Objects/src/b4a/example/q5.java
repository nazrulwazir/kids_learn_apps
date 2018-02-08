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

public class q5 extends Activity implements B4AActivity{
	public static q5 mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.q5");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (q5).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.q5");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.q5", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (q5) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (q5) Resume **");
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
		return q5.class;
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
        BA.LogInfo("** Activity (q5) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (q5) Resume **");
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
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _clicksound2 = null;
public static anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgb1 = null;
public static int _num = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntest = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsubmit = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnbackcat = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnspeak6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnext = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb6 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb7 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb8 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb9 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb10 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb11 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb12 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsend = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtaddress = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtphone = null;
public static String _strphonenumber = "";
public static String _strmessage = "";
public static int _score = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lbl1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnscore = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnclear = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpercent = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
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
public b4a.example.memory _memory = null;
public b4a.example.kid_video _kid_video = null;
public b4a.example.ayam _ayam = null;
public b4a.example.quiz _quiz = null;
public b4a.example.q2 _q2 = null;
public b4a.example.q3 _q3 = null;
public b4a.example.q4 _q4 = null;
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
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="Activity.LoadLayout(\"q5\")";
mostCurrent._activity.LoadLayout("q5",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="TTS.Initialize(\"tts\")";
_tts.Initialize(processBA,"tts");
 //BA.debugLineNum = 80;BA.debugLine="MediaPlayer1.Initialize( )";
_mediaplayer1.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="MediaPlayer1.Load(File.DirAssets, \"intro.mp3\")";
_mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"intro.mp3");
 //BA.debugLineNum = 82;BA.debugLine="timer1.Initialize(\"timer1\", 1000)";
mostCurrent._timer1.Initialize(processBA,"timer1",(long) (1000));
 //BA.debugLineNum = 83;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 95;BA.debugLine="If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause";
if (_mediaplayer1.IsPlaying()) { 
_mediaplayer1.Pause();};
 //BA.debugLineNum = 96;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 88;BA.debugLine="MediaPlayer1.Play";
_mediaplayer1.Play();
 //BA.debugLineNum = 89;BA.debugLine="timer1.Enabled = True";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 90;BA.debugLine="timer1_Tick 'don't wait one second for the UI to";
_timer1_tick();
 //BA.debugLineNum = 91;BA.debugLine="MediaPlayer1.Looping = True";
_mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub btnNext_Click";
 //BA.debugLineNum = 146;BA.debugLine="StartActivity(finish)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._finish.getObject()));
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _btnprev_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub btnPrev_Click";
 //BA.debugLineNum = 151;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _btnspeak1_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub btnSpeak1_Click";
 //BA.debugLineNum = 117;BA.debugLine="TTS.Speak(\" pencil \", True)";
_tts.Speak(" pencil ",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _btnsubmit_click() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub btnSubmit_Click";
 //BA.debugLineNum = 123;BA.debugLine="If rb1.Checked = True Then";
if (mostCurrent._rb1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 124;BA.debugLine="clicksound2.Play";
_clicksound2.Play();
 //BA.debugLineNum = 125;BA.debugLine="Msgbox(\"Try Again\", \"Wrong\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Try Again","Wrong",mostCurrent.activityBA);
 //BA.debugLineNum = 126;BA.debugLine="lbl1.Text  =  \"0\"";
mostCurrent._lbl1.setText((Object)("0"));
 }else if(mostCurrent._rb2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 128;BA.debugLine="clicksound2.Play";
_clicksound2.Play();
 //BA.debugLineNum = 129;BA.debugLine="Msgbox(\"Try Again\", \"Wrong\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Try Again","Wrong",mostCurrent.activityBA);
 //BA.debugLineNum = 130;BA.debugLine="lbl1.Text  =  \"0\"";
mostCurrent._lbl1.setText((Object)("0"));
 }else if(mostCurrent._rb3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 132;BA.debugLine="clicksound2.Play";
_clicksound2.Play();
 //BA.debugLineNum = 133;BA.debugLine="Msgbox(\"Try Again\", \"Wrong\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Try Again","Wrong",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="lbl1.Text  =  \"0\"";
mostCurrent._lbl1.setText((Object)("0"));
 }else if(mostCurrent._rb4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 136;BA.debugLine="clicksound.Play";
_clicksound.Play();
 //BA.debugLineNum = 137;BA.debugLine="Msgbox(\"Correct Answer is Pencil\", \"Congrats\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Correct Answer is Pencil","Congrats",mostCurrent.activityBA);
 //BA.debugLineNum = 138;BA.debugLine="lbl1.Text  =  \"1\"";
mostCurrent._lbl1.setText((Object)("1"));
 };
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 155;BA.debugLine="StartActivity(cat_fun)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._cat_fun.getObject()));
 //BA.debugLineNum = 156;BA.debugLine="Log(\"running act finish\")";
anywheresoftware.b4a.keywords.Common.Log("running act finish");
 //BA.debugLineNum = 157;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 158;BA.debugLine="Log(\"ran act finish\")";
anywheresoftware.b4a.keywords.Common.Log("ran act finish");
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public static String  _converttotimeformat(int _ms) throws Exception{
int _seconds = 0;
int _minutes = 0;
 //BA.debugLineNum = 108;BA.debugLine="Sub ConvertToTimeFormat(ms As Int) As String";
 //BA.debugLineNum = 109;BA.debugLine="Dim seconds, minutes As Int";
_seconds = 0;
_minutes = 0;
 //BA.debugLineNum = 110;BA.debugLine="seconds = Round(ms / 1000)";
_seconds = (int) (anywheresoftware.b4a.keywords.Common.Round(_ms/(double)1000));
 //BA.debugLineNum = 111;BA.debugLine="minutes = Floor(seconds / 60)";
_minutes = (int) (anywheresoftware.b4a.keywords.Common.Floor(_seconds/(double)60));
 //BA.debugLineNum = 112;BA.debugLine="seconds = seconds Mod 60";
_seconds = (int) (_seconds%60);
 //BA.debugLineNum = 113;BA.debugLine="Return NumberFormat(minutes, 1, 0) & \":\" & Number";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat(_minutes,(int) (1),(int) (0))+":"+anywheresoftware.b4a.keywords.Common.NumberFormat(_seconds,(int) (2),(int) (0));
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim timer1 As Timer";
mostCurrent._timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 20;BA.debugLine="Dim pgb1 As ProgressBar";
mostCurrent._pgb1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim num As Int";
_num = 0;
 //BA.debugLineNum = 22;BA.debugLine="Dim btnSpeak1 As Button";
mostCurrent._btnspeak1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim btnSpeak2 As Button";
mostCurrent._btnspeak2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim btnSpeak3 As Button";
mostCurrent._btnspeak3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim btnTest As Button";
mostCurrent._btntest = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim rb1 As RadioButton";
mostCurrent._rb1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim rb2 As RadioButton";
mostCurrent._rb2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim rb3 As RadioButton";
mostCurrent._rb3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim rb4 As RadioButton";
mostCurrent._rb4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim btnSubmit As Button";
mostCurrent._btnsubmit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim btnBackCat As Button";
mostCurrent._btnbackcat = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim btnSpeak4 As Button";
mostCurrent._btnspeak4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim btnSpeak5 As Button";
mostCurrent._btnspeak5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim btnSpeak6 As Button";
mostCurrent._btnspeak6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim btnNext As Button";
mostCurrent._btnnext = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim rb5 As RadioButton";
mostCurrent._rb5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim rb6 As RadioButton";
mostCurrent._rb6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim rb7 As RadioButton";
mostCurrent._rb7 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim rb8 As RadioButton";
mostCurrent._rb8 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim rb9 As RadioButton";
mostCurrent._rb9 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim rb10 As RadioButton";
mostCurrent._rb10 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim rb11 As RadioButton";
mostCurrent._rb11 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim rb12 As RadioButton";
mostCurrent._rb12 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim btnSend As Button";
mostCurrent._btnsend = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim txtName As EditText";
mostCurrent._txtname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim txtAddress As EditText";
mostCurrent._txtaddress = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim txtPhone As EditText";
mostCurrent._txtphone = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim strPhoneNumber As String";
mostCurrent._strphonenumber = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim strmessage As String";
mostCurrent._strmessage = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim score As Int";
_score = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim lbl1 As Label";
mostCurrent._lbl1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim lbl2 As Label";
mostCurrent._lbl2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim lbl3 As Label";
mostCurrent._lbl3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim btnScore As Button";
mostCurrent._btnscore = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim btnClear As Button";
mostCurrent._btnclear = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim lblPercent As Label";
mostCurrent._lblpercent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 60;BA.debugLine="ListView1.Initialize(\"ListView1\")";
mostCurrent._listview1.Initialize(mostCurrent.activityBA,"ListView1");
 //BA.debugLineNum = 61;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="clicksound.Initialize2(\"clicksound\")";
_clicksound.Initialize2(processBA,"clicksound");
 //BA.debugLineNum = 64;BA.debugLine="clicksound.Load(File.DirAssets, \"betul.mp3\")";
_clicksound.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"betul.mp3");
 //BA.debugLineNum = 66;BA.debugLine="clicksound2.Initialize2(\"clicksound2\")";
_clicksound2.Initialize2(processBA,"clicksound2");
 //BA.debugLineNum = 67;BA.debugLine="clicksound2.Load(File.DirAssets, \"tak betul.mp3\")";
_clicksound2.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tak betul.mp3");
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Public TTS As TTS";
_tts = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 11;BA.debugLine="Public clicksound,clicksound2 As MediaPlayer";
_clicksound = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
_clicksound2 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Public MediaPlayer1 As MediaPlayer";
_mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 101;BA.debugLine="If MediaPlayer1.IsPlaying Then";
if (_mediaplayer1.IsPlaying()) { 
 };
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
}
