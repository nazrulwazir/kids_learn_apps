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
	Public clicksound As MediaPlayer
	Dim MediaPlayer1 As MediaPlayer
	Dim timer1 As Timer
	
End Sub

Sub Globals

	Dim  introwel As MediaPlayer 
	Dim b1,b2,b3,b4,b5,b6,b7 As Button
	Dim i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21,i22,i23,i24,i25,i26 As ImageView
	
	clicksound.Initialize2("clicksound")
	clicksound.Load(File.DirAssets, "click.mp3")
	
	
	TTS.Initialize("tts")
	
	Private btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY,btnZ As Button
	Private i2 As ImageView
	
	Private Button1 As Button
	
	Private Iplay As Button
	

	
	Private play_btn As Button
	Private fv1 As FabricView
	Private btn_clear As Button
	Private draw_lbl As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	

		MediaPlayer1.Initialize( )
		MediaPlayer1.Load(File.DirAssets, "abc.mp3")
		timer1.Initialize("timer1", 1000)
		MediaPlayer1.Looping = True

	
	Activity.LoadLayout("writing")
	
	fv1.BackgroundMode = fv1.BACKGROUND_STYLE_BLANK
    fv1.DrawColor = Colors.Red
    fv1.DrawBackgroundColor = Colors.Transparent
    fv1.NoteBookLeftLineColor = Colors.Blue
    fv1.NoteBookLeftLinePadding = 60
	fv1.Size=30
    fv1.FabricLineColor = Colors.Yellow
	
	
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
	i11.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
	i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
	
	'fv1.Visible=False

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
clicksound.play
 fv1.cleanPage

i3.Visible=False
i2.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i1.Visible=True


End Sub


Sub btnB_Click
clicksound.play

 fv1.cleanPage

i1.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i2.Visible=True



End Sub

Sub btnC_Click
clicksound.play

 fv1.cleanPage


i1.Visible=False
i2.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i3.Visible=True



End Sub

Sub btnD_Click
	
clicksound.play
 fv1.cleanPage

i1.Visible=False
i2.Visible=False
i3.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i4.Visible=True



End Sub

Sub btnE_Click
clicksound.play

 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i5.Visible=True



End Sub

Sub btnF_Click
clicksound.play

 fv1.cleanPage

i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i6.Visible=True



End Sub

Sub btnG_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i8.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i7.Visible=True



End Sub

Sub btnH_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i9.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i8.Visible=True



End Sub

Sub btnI_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i10.Visible=False
i11.Visible=False
	i12.Visible=False
		i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i9.Visible=True



End Sub

Sub btnJ_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i10.Visible=True



End Sub

Sub btnK_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i11.Visible=True



End Sub

Sub btnL_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i12.Visible=True



End Sub

Sub btnM_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i13.Visible=True



End Sub

Sub btnN_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i14.Visible=True


End Sub

Sub btnO_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i16.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i15.Visible=True


End Sub

Sub btnP_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i17.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i16.Visible=True


End Sub

Sub btnQ_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i18.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i17.Visible=True


End Sub

Sub btnR_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i19.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i18.Visible=True


End Sub

Sub btnS_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i19.Visible=True


End Sub

Sub btnT_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i19.Visible=False
	i21.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i20.Visible=True


End Sub

Sub btnU_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i22.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i21.Visible=True

End Sub

Sub btnV_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i21.Visible=False
	i23.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i22.Visible=True


End Sub

Sub btnW_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i22.Visible=False
	i21.Visible=False
	i24.Visible=False
	i25.Visible=False
	i26.Visible=False
i23.Visible=True


End Sub

Sub btnX_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i22.Visible=False
	i21.Visible=False
	i23.Visible=False
	i25.Visible=False
	i26.Visible=False
i24.Visible=True


End Sub

Sub btnY_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i22.Visible=False
	i21.Visible=False
	i24.Visible=False
	i23.Visible=False
	i26.Visible=False
i25.Visible=True


End Sub

Sub btnZ_Click
clicksound.play
 fv1.cleanPage
i1.Visible=False
i2.Visible=False
i3.Visible=False
i4.Visible=False
i5.Visible=False
i6.Visible=False
i7.Visible=False
i8.Visible=False
i9.Visible=False
i11.Visible=False
i10.Visible=False
	i12.Visible=False
	i13.Visible=False
	i14.Visible=False
	i15.Visible=False
	i16.Visible=False
	i17.Visible=False
		i18.Visible=False
	i20.Visible=False
	i19.Visible=False
	i22.Visible=False
	i21.Visible=False
	i24.Visible=False
	i23.Visible=False
	i25.Visible=False
i26.Visible=True

End Sub

Sub Button1_Click
	'introwel.Stop
	clicksound.play
Activity.Finish
End Sub



Sub btn_clear_Click
	  fv1.cleanPage
End Sub

