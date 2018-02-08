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
Dim s1,s2 As MediaPlayer
	Dim MediaPlayer1 As MediaPlayer
	Public TTS As TTS
	Dim timer1 As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
Dim  introwel As MediaPlayer 
	Private btn_writing As Button
	Private btn_alphabet As Button
		TTS.Initialize("tts")
	Private Button1 As Button
	Private btn_number As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("num_2")

		MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro3.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
		s1.Initialize2("s1")
	s1.Load(File.DirAssets, "Number.mp3")
	
	s2.Initialize2("s2")
	s2.Load(File.DirAssets, "Number Writing.mp3")
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


Sub btn_writing_Click
		'Main.clicksound.play
	'	introwel.Stop
	'TTS.Speak("Number Writing", True)
	s2.Play
ToastMessageShow("Number Writing", True)
	StartActivity(number_writing)
End Sub

Sub Button1_Click
	'	introwel.Stop
	'Main.clicksound.play
Activity.Finish
End Sub

Sub btn_number_Click
		'Main.clicksound.play
		'introwel.Stop
	'TTS.Speak("Number", True)
	s1.Play
ToastMessageShow("Number", True)
	StartActivity(number)
End Sub