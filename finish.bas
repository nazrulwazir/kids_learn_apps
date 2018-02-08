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

Public TTS As TTS
Public clicksound,clicksound2 As MediaPlayer

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim timer1 As Timer
Dim MediaPlayer1 As MediaPlayer

	

	Private IRCircularJump1 As IRCircularJump
	Private IRCircularRing1 As IRCircularRing
	Private IRCircularZoom1 As IRCircularZoom
	Private IRFunnyBar1 As IRFunnyBar
	Private IRGears1 As IRGears
	Private IRLineWithText1 As IRLineWithText
	Private IRFinePoiStar1 As IRFinePoiStar
	
End Sub



Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")

	Activity.LoadLayout("finish")

	
	MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "celebrate.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		

	IRCircularJump1.startAnim
	IRCircularRing1.startAnim

	IRCircularZoom1.startAnim

	IRFinePoiStar1.startAnim
	IRFunnyBar1.startAnim
	IRGears1.startAnim

	
End Sub

Sub Activity_Resume
	MediaPlayer1.Play
	timer1.Enabled = True
	timer1_Tick 'don't wait one second for the UI to update.
	MediaPlayer1.Looping = True
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If MediaPlayer1.IsPlaying Then MediaPlayer1.Pause
	timer1.Enabled = False
	MediaPlayer1.Looping = True
End Sub

Sub timer1_Tick
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


Sub btnSubmit_Click
	


End Sub

Sub btnNext_Click
	'StartActivity(q5)
End Sub


Sub btnPrev_Click
	Activity.Finish
End Sub

Sub Button2_Click
	StartActivity(cat_fun)
	 Log("running act finish")
     Activity.Finish
     Log("ran act finish")
End Sub