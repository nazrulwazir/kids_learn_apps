Type=Activity
Version=6
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
Dim Timer1 As Timer
Dim Timer2 As Timer

	Dim MediaPlayer1 As MediaPlayer
	Public TTS As TTS
	Dim timer3 As Timer

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim  introwel As MediaPlayer 
Dim button1 As Button
Dim button2 As Button
Dim button3 As Button
Dim button4 As Button
Dim button5 As Button
Dim button6 As Button
Dim button7 As Button
Dim button8 As Button
Dim button9 As Button
Dim button10 As Button
Dim button11 As Button
Dim button12 As Button
Dim button13 As Button
Dim button14 As Button
Dim button15 As Button
Dim button16 As Button
Dim button17 As Button
Dim button18 As Button
Dim button19 As Button
Dim button20 As Button
Dim clicks As Int
Dim label1 As Label
Dim label2 As Label
Dim label3 As Label
Dim label4 As Label
Dim label5 As Label
Dim label6 As Label
Dim label7 As Label
Dim label8 As Label
Dim label9 As Label
Dim label10 As Label
Dim label11 As Label
Dim label12 As Label
Dim label13 As Label
Dim label14 As Label
Dim label15 As Label
Dim label16 As Label
Dim label17 As Label
Dim label18 As Label
Dim label19 As Label
Dim label20 As Label
Dim loc(20) As Int
Dim sel(20) As Int
Dim x As Int
Dim y As Int
Dim z As Int
Dim Select1 As Int
Dim select2 As Int
Dim loc1 As Int
Dim loc2 As Int
Dim Canvas1 As Canvas
Dim busy As String
Dim bt As Int
Dim lc As Int
Dim match As Int
Dim ImageView1 As ImageView
Dim ImageView2 As ImageView
Dim ImageView3 As ImageView
Dim ImageView4 As ImageView
Dim ImageView5 As ImageView
Dim ImageView6 As ImageView
Dim ImageView7 As ImageView
Dim ImageView8 As ImageView
Dim ImageView9 As ImageView
Dim ImageView10 As ImageView
Dim ImageView11 As ImageView
Dim ImageView12 As ImageView
Dim ImageView13 As ImageView
Dim ImageView14 As ImageView
Dim ImageView15 As ImageView
Dim ImageView16 As ImageView
Dim ImageView17 As ImageView
Dim ImageView18 As ImageView
Dim ImageView19 As ImageView
Dim ImageView20 As ImageView
Dim one As Int
Dim two As Int
End Sub

Sub Activity_Create(FirstTime As Boolean)
If FirstTime Then
Timer1.Initialize("Timer1",1000)
Timer2.Initialize("Timer1",4000)
End If

'timer1.Enabled=False
'timer2.Enabled=False

	MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro2.mp3")
		timer3.Initialize("timer3", 1000)
		MediaPlayer1.Looping = True
		

'Canvas1.Initialize(Activity)
Activity.LoadLayout("match.bal")

restart

End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause
	timer3.Enabled = False
	MediaPlayer1.Looping = True
Timer1.Enabled = False	


	
End Sub


Sub Activity_Resume
	
		MediaPlayer1.Play
	timer3.Enabled = True
	timer3_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
Timer1.Enabled = True


End Sub

Sub timer3_Tick
	If MediaPlayer1.IsPlaying Then
		'barPosition.Value = MediaPlayer1.Position / MediaPlayer1.Duration * 100
		'lblPosition.Text = "Position: " & ConvertToTimeFormat(MediaPlayer1.Position) & _
		'	" (" & ConvertToTimeFormat(MediaPlayer1.Duration) & ")"
	End If
End Sub
'converts milliseconds to m:ss format.
Sub ConvertToTimeFormat(ms As Int) As String
	Dim seconds, minutes As Int
	seconds = Round(ms / 1000)
	minutes = Floor(seconds / 60)
	seconds = seconds Mod 60
	Return NumberFormat(minutes, 1, 0) & ":" & NumberFormat(seconds, 2, 0) 'ex: 3:05
End Sub


Sub restart


For x=0 To 19
loc(x)=0
Next


For x=0 To 19
loc(x)=0
Next

Dim a As Int

For a=0 To 9
sel(a)=a+1
Next

For a=10 To 19
sel(a)=a-9
Next

'-------------------------
For z=1 To 100

 For x=0 To 19
 
 If loc(x)=0 Then
 mix
 End If
 
 Next
 
Next

'----------------------------------------
Dim debug As String

debug=""

If debug="debug" Then

label1.Text=loc(0)
label2.Text=loc(1)
label3.Text=loc(2)
label4.Text=loc(3)
label5.Text=loc(4)
label6.Text=loc(5)
label7.Text=loc(6)
label8.Text=loc(7)
label9.Text=loc(8)
label10.Text=loc(9)
label11.Text=loc(10)
label12.Text=loc(11)
label13.Text=loc(12)
label14.Text=loc(13)
label15.Text=loc(14)
label16.Text=loc(15)
label17.Text=loc(16)
label18.Text=loc(17)
label19.Text=loc(18)
label20.Text=loc(19)
End If


clicks=0
Timer1.Enabled=False
loc1=0
loc2=0


button1.visible=True
button2.visible=True
button3.visible=True
button4.visible=True
button5.visible=True
button6.visible=True
button7.visible=True
button8.visible=True
button9.visible=True
button10.visible=True
button11.visible=True
button12.visible=True
button13.visible=True
button14.visible=True
button15.visible=True
button16.visible=True
button17.visible=True
button18.visible=True
button19.visible=True
button20.visible=True



Dim panels As Panel
	 panels.Initialize("panels")
	  Activity.AddView(panels,60%x,25%y,30%x,40%y)
	  ImageView1.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(0) & ".png",panels.Width,panels.Height))
	    ImageView2.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(1) & ".png",panels.Width,panels.Height))
		  ImageView3.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(2) & ".png",panels.Width,panels.Height))
		ImageView4.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(3) & ".png",panels.Width,panels.Height))
		ImageView5.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(4) & ".png",panels.Width,panels.Height))
		ImageView6.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(5) & ".png",panels.Width,panels.Height))
		ImageView7.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(6) & ".png",panels.Width,panels.Height))
		ImageView8.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(7) & ".png",panels.Width,panels.Height))
		ImageView9.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(8) & ".png",panels.Width,panels.Height))
		ImageView10.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(9) & ".png",panels.Width,panels.Height))
		ImageView11.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(10) & ".png",panels.Width,panels.Height))
		ImageView12.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(11) & ".png",panels.Width,panels.Height))
		ImageView13.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(12) & ".png",panels.Width,panels.Height))
		ImageView14.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(13) & ".png",panels.Width,panels.Height))
		ImageView15.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(14) & ".png",panels.Width,panels.Height))
		ImageView16.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(15) & ".png",panels.Width,panels.Height))
		ImageView17.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(16) & ".png",panels.Width,panels.Height))
		ImageView18.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(17) & ".png",panels.Width,panels.Height))
		ImageView19.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(18) & ".png",panels.Width,panels.Height))
		ImageView20.SetBackgroundImage(LoadBitmapSample(File.DirAssets, loc(19) & ".png",panels.Width,panels.Height))
		  



ImageView1.SendToBack
ImageView2.SendToBack
ImageView3.SendToBack
ImageView4.SendToBack
ImageView5.SendToBack
ImageView6.SendToBack
ImageView7.SendToBack
ImageView8.SendToBack
ImageView9.SendToBack
ImageView10.SendToBack
ImageView11.SendToBack
ImageView12.SendToBack
ImageView13.SendToBack
ImageView14.SendToBack
ImageView15.SendToBack
ImageView16.SendToBack
ImageView17.SendToBack
ImageView18.SendToBack
ImageView19.SendToBack
ImageView20.SendToBack

busy="no"

match=0
End Sub

Sub Button1_Click

'Msgbox(loc(0),"")


lc=loc(0)
bt=1
calcx
End Sub
Sub Button2_Click
lc=loc(1)
bt=2
calcx
End Sub
Sub Button3_Click
lc=loc(2)
bt=3
calcx
End Sub

Sub Button4_Click
lc=loc(3)
bt=4
calcx
End Sub

Sub Button5_Click
lc=loc(4)
bt=5
calcx
End Sub
Sub Button6_Click
lc=loc(5)
bt=6
calcx
End Sub
Sub Button7_Click
lc=loc(6)
bt=7
calcx
End Sub
Sub Button8_Click
lc=loc(7)
bt=8
calcx
End Sub
Sub Button9_Click
lc=loc(8)
bt=9
calcx
End Sub
Sub Button10_Click
lc=loc(9)
bt=10
calcx
End Sub
Sub Button11_Click
lc=loc(10)
bt=11
calcx
End Sub
Sub Button12_Click
lc=loc(11)
bt=12
calcx
End Sub
Sub Button13_Click
lc=loc(12)
bt=13
calcx
End Sub
Sub Button14_Click
lc=loc(13)
bt=14
calcx
End Sub
Sub Button15_Click
lc=loc(14)
bt=15
calcx
End Sub
Sub Button16_Click
lc=loc(15)
bt=16
calcx
End Sub
Sub Button17_Click
lc=loc(16)
bt=17
calcx
End Sub
Sub Button18_Click
lc=loc(17)
bt=18
calcx
End Sub
Sub Button19_Click
lc=loc(18)
bt=19
calcx
End Sub
Sub Button20_Click
lc=loc(19)
bt=20
calcx
End Sub



Sub Button21_Click
restart
End Sub


Sub Button22_Click
	'introwel.Stop
Activity.Finish
End Sub

Sub delayx

	For x=1 To 1000000
	Next
	
	
If loc1=1 Or loc2=1 Then
button1.visible=True
End If

If loc1=2 Or loc2=2 Then 
button2.visible=True
End If

If loc1=3 Or loc2=3 Then 
button3.visible=True
End If

If loc1=4 Or loc2=4 Then 
button4.visible=True
End If

If loc1=5 Or loc2=5 Then 
button5.visible=True
End If

If loc1=6 Or loc2=6 Then 
button6.visible=True
End If

If loc1=7 Or loc2=7 Then 
button7.visible=True
End If

If loc1=8 Or loc2=8 Then 
button8.visible=True
End If

If loc1=9 Or loc2=9 Then 
button9.visible=True
End If

If loc1=10 Or loc2=10 Then 
button10.visible=True
End If

If loc1=11 Or loc2=11 Then 
button11.visible=True
End If


If loc1=12 Or loc2=12 Then 
button12.visible=True
End If

If loc1=13 Or loc2=13 Then 
button13.visible=True
End If

If loc1=14 Or loc2=14 Then 
button14.visible=True
End If

If loc1=15 Or loc2=15 Then 
button15.visible=True
End If

If loc1=16 Or loc2=16 Then 
button16.visible=True
End If

If loc1=17 Or loc2=17 Then 
button17.visible=True
End If

If loc1=18 Or loc2=18 Then 
button18.visible=True
End If

If loc1=19 Or loc2=19 Then 
button19.visible=True
End If

If loc1=20 Or loc2=20 Then 
button20.visible=True
End If

busy="no"
	
End Sub


Sub Timer2_Tick

	If one=1  Or two=1 Then
	ImageView1.Bitmap = LoadBitmap(File.DirAssets, "Black.png")
	End If
	
	If one=2  Or two=2 Then
	ImageView2.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=3  Or two=3 Then
	ImageView3.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=4  Or two=4 Then
	ImageView4.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=5  Or two=5 Then
	ImageView5.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=6  Or two=6 Then
	ImageView6.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=7  Or two=7 Then
	ImageView7.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=8  Or two=8 Then
	ImageView8.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=9  Or two=9 Then
	ImageView9.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=10 Or two=10 Then
	ImageView10.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=11  Or two=11 Then
	ImageView11.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=12  Or two=12 Then
	ImageView12.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=13  Or two=13 Then
	ImageView13.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=14  Or two=14 Then
	ImageView14.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=15  Or two=15 Then
	ImageView15.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=16  Or two=16 Then
	ImageView16.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=17  Or two=17 Then
	ImageView17.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=18  Or two=18 Then
	ImageView18.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=19  Or two=19 Then
	ImageView19.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If
	
	If one=20  Or two=20 Then
	ImageView20.Bitmap = LoadBitmap(File.DirAssets, "black.png")
	End If

Timer2.Enabled=False
End Sub


Sub Timer1_Tick


If loc1=1 Or loc2=1 Then
button1.visible=True
End If

If loc1=2 Or loc2=2 Then 
button2.visible=True
End If

If loc1=3 Or loc2=3 Then 
button3.visible=True
End If

If loc1=4 Or loc2=4 Then 
button4.visible=True
End If

If loc1=5 Or loc2=5 Then 
button5.visible=True
End If

If loc1=6 Or loc2=6 Then 
button6.visible=True
End If

If loc1=7 Or loc2=7 Then 
button7.visible=True
End If

If loc1=8 Or loc2=8 Then 
button8.visible=True
End If

If loc1=9 Or loc2=9 Then 
button9.visible=True
End If

If loc1=10 Or loc2=10 Then 
button10.visible=True
End If

If loc1=11 Or loc2=11 Then 
button11.visible=True
End If


If loc1=12 Or loc2=12 Then 
button12.visible=True
End If

If loc1=13 Or loc2=13 Then 
button13.visible=True
End If

If loc1=14 Or loc2=14 Then 
button14.visible=True
End If

If loc1=15 Or loc2=15 Then 
button15.visible=True
End If

If loc1=16 Or loc2=16 Then 
button16.visible=True
End If

If loc1=17 Or loc2=17 Then 
button17.visible=True
End If

If loc1=18 Or loc2=18 Then 
button18.visible=True
End If

If loc1=19 Or loc2=19 Then 
button19.visible=True
End If

If loc1=20 Or loc2=20 Then 
button20.visible=True
End If

busy="no"

Timer1.Enabled=False



End Sub
Sub mix

If loc(x)=0 Then
y=Rnd(0,20)
End If

If sel(y) >0 And loc(x)=0 Then
loc(x)=sel(y)
sel(y)=0
End If

End Sub
Sub calcx

If busy="no" Then

clicks=clicks+1

If clicks<=2 And bt=1 Then
button1.visible=False
End If

If clicks<=2 And bt=2 Then
button2.visible=False
End If

If clicks<=2 And bt=3 Then
button3.visible=False
End If

If clicks<=2 And bt=4 Then
button4.visible=False
End If

If clicks<=2 And bt=5 Then
button5.visible=False
End If

If clicks<=2 And bt=6 Then
button6.visible=False
End If

If clicks<=2 And bt=7 Then
button7.visible=False
End If

If clicks<=2 And bt=8 Then
button8.visible=False
End If

If clicks<=2 And bt=9 Then
button9.visible=False
End If

If clicks<=2 And bt=10 Then
button10.visible=False
End If

If clicks<=2 And bt=11 Then
button11.visible=False
End If

If clicks<=2 And bt=12 Then
button12.visible=False
End If

If clicks<=2 And bt=13 Then
button13.visible=False
End If

If clicks<=2 And bt=14 Then
button14.visible=False
End If

If clicks<=2 And bt=15 Then
button15.visible=False
End If

If clicks<=2 And bt=16 Then
button16.visible=False
End If

If clicks<=2 And bt=17 Then
button17.visible=False
End If

If clicks<=2 And bt=18 Then
button18.visible=False
End If

If clicks<=2 And bt=19 Then
button19.visible=False
End If

If clicks<=2 And bt=20 Then
button20.visible=False
End If




	If clicks=1 Then
	Select1=lc
    one=bt
	loc1=bt
	'Msgbox(select1,"")
	
	End If





	If clicks=2 Then
	select2=lc
	
	
    two=bt
	'Msgbox(select2,"")
	'Msgbox(select1 & "|" & select2,"")
	If Select1=select2 Then
	'Msgbox(select1 & "|" & select2,"")
	match=match+1
	

	Else
	loc2=bt
	busy="yes"
	Timer1.Enabled=True
	'delayx
	End If
	
	clicks=0
	End If

If match=10 Then
Msgbox("you won!","")
End If

End If


End Sub


