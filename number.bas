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

	Public TTS As TTS
	Public MediaPlayer1 As MediaPlayer
	
	Dim timer1 As Timer
	
End Sub

Sub Globals

	Public a1,a2,a3,a4,a5,a6,a7,a8,a9,a10 As MediaPlayer
	Dim  introwel As MediaPlayer 
	Dim b1,b2,b3,b4,b5,b6,b7 As Button
	Dim i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22,i23,i24,i25,i26 As ImageView
	

	
	TTS.Initialize("tts")
	
	Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY,btnZ As Button
	Private i2 As ImageView
	
	Private Button1 As Button
	
	Private Iplay As Button	
	
	Private play_btn As Button
	Private btn_clear As Button


End Sub

Sub Activity_Create(FirstTime As Boolean)
	

	
	Activity.LoadLayout("number")
	
	a1.Initialize2("a1")
	a1.Load(File.DirAssets, "1.mp3")
	
	a2.Initialize2("a2")
	a2.Load(File.DirAssets, "2.mp3")
	
	a3.Initialize2("a3")
	a3.Load(File.DirAssets, "3.mp3")
	
	a4.Initialize2("a4")
	a4.Load(File.DirAssets, "4.mp3")
	
	a5.Initialize2("a5")
	a5.Load(File.DirAssets, "5.mp3")
	
	a6.Initialize2("a6")
	a6.Load(File.DirAssets, "6.mp3")
	
	a7.Initialize2("a7")
	a7.Load(File.DirAssets, "7.mp3")
	
	a8.Initialize2("a8")
	a8.Load(File.DirAssets, "8.mp3")
	
	a9.Initialize2("a9")
	a9.Load(File.DirAssets, "9.mp3")
	
	a10.Initialize2("a10")
	a10.Load(File.DirAssets, "10.mp3")
	
		MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "intro.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True
	
	i1.Visible=False
	i2.Visible=False
	i3.Visible=False
	i4.Visible=False
	i5.Visible=False
	i6.Visible=False
	i7.Visible=False
	i8.Visible=False
	i9.Visible=False
	i10.Visible=False
	
	
	

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




Sub b3_Click
a1.Play

i3.Visible=False
i2.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i1.Visible=True
'TTS.Speak("one ", True)



End Sub


Sub btnB_Click

a2.Play



i1.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i2.Visible=True

'TTS.Speak("two ", True)



End Sub

Sub btnC_Click



a3.Play


i1.Visible=False
i2.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i3.Visible=True

'TTS.Speak("THREE", True)



End Sub

Sub btnD_Click
	

a4.Play


i1.Visible=False
i2.Visible=False
i3.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i4.Visible=True

'TTS.Speak("FOUR", True)



End Sub

Sub btnE_Click

a5.Play


i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i5.Visible=True

'TTS.Speak("FIVE", True)



End Sub

Sub btnF_Click


a10.Play



i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i6.Visible=True

'TTS.Speak("ten ", True)



End Sub

Sub btnG_Click
a6.Play

i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False

i7.Visible=True

'TTS.Speak("six ", True)

End Sub

Sub btnH_Click
a7.Play
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i9.Visible=False
i10.Visible=False

i8.Visible=True

'TTS.Speak("SEVEN", True)


End Sub

Sub btnI_Click
a8.Play

i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i10.Visible=False

i9.Visible=True

'TTS.Speak("EIGHT", True)

End Sub

Sub btnJ_Click
a9.Play
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False

i10.Visible=True

'TTS.Speak("NINE", True)


End Sub


Sub Button1_Click

Activity.Finish
End Sub




