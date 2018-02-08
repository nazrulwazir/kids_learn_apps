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
		Dim MediaPlayer1,s1,s2,s3 As MediaPlayer
	Dim A,B,C As Int 'ignore
    Dim ex As Exception
	Public TTS As TTS
	Dim timer1,timer2,timer3,timer4 As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	  Dim  introwel As MediaPlayer 
	Private ImageView1 As ImageView
	Private ImageView2 As ImageView
	Private btnScramble As Button
	Private btnExit As Button
	Private btnAlphabet As Button
	Private btnMemory As Button
	Private btnAbout As Button
	Private Panel1 As Panel
	Private Label1 As Label
	
	TTS.Initialize("tts")
	Private btn_Start As Button
	Private btnFun As Button
	Private btnNumber As Button
		Private btnExit As Button
	Private IREatBeans1 As IREatBeans
	Private btnVolume As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:

	
		MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
		
		s1.Initialize2("s1")
		s1.Load(File.DirAssets, "Kids Alphabet.mp3")
	
	
		s2.Initialize2("s2")
		s2.Load(File.DirAssets, "Kids Fun.mp3")
		
		
		s3.Initialize2("s3")
		s3.Load(File.DirAssets, "Kids Number.mp3")
		
		


	Activity.LoadLayout("Layout2")
	IREatBeans1.startAnim

	
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


Sub Button1_Click
	'Main.clicksound.play
	introwel.Stop
	StartActivity(Home)
End Sub





Sub btnAlphabet_Click
'	Main.clicksound.play
	'introwel.Stop
'	TTS.Speak("Kids Alphabet", True)
s1.Play
ToastMessageShow("Kids Alphabet", True)
	StartActivity(cat_alphabet)
	
End Sub



Sub btnAbout_Click
'	Main.clicksound.play
	'introwel.Stop
	'ImageView2.Width = 100%x
	'ImageView2.Visible = True
	Panel1.Visible = True
	Label1.TextColor = Colors.black
	Label1.TextSize = 10
	Label1.Text = "Kids Learning Application is developed for student studying in Taska Nur Ina Bestari to help children aged 2 to 4 years of study as through as Alphabet,Number,Scramble,Memory Match,Quiz and Videos.This application is also a range of activities for children.Therefore it can strengthen the minds of children at a young age."
	Label1.Visible = True
	
End Sub

Sub ImageView3_Click
	Panel1.Visible = False
	'Label1.Text= ""
	'Label1.Visible = False
End Sub



Sub btnFun_Click
	'Main.clicksound.play
	'introwel.Stop
	'TTS.Speak("Kids Fun", True)
	s2.Play
ToastMessageShow("Kids Fun", True)
	StartActivity(cat_fun)
End Sub

Sub btnNumber_Click
	'	Main.clicksound.play
		'introwel.Stop
'	TTS.Speak("Kids Number", True)
s3.Play
ToastMessageShow("Kids Number", True)
	StartActivity(cat_num)
End Sub
