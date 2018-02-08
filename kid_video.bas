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
	Dim MediaPlayer1 As MediaPlayer
	Dim timer1 As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnPisang As Button
	Dim vv,vv2,vv3 As VideoView
	Dim p As Phone
	Dim offsetX As Int = 45%x
	Private btnAyam As Button
	Private btnQuiz As Button
	Private Button1 As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)

MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro3.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
Activity.LoadLayout("song")


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

Sub btnPisang_Click
StartActivity(pisang)
End Sub

Sub btnAyam_Click
StartActivity(bangau)
End Sub

Sub btnQuiz_Click
	StartActivity(ayam)
End Sub

Sub Button1_Click
	Activity.Finish
End Sub