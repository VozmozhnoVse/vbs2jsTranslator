'#include ::[IDENTIFY_LEVEL].[LIB_VB]
Private Function say_them_it(s)
	If s = "" then
		say_them_it = ""
	Else
		If Mid(s, 1, 1) <> "?" Then
			MsgBox s, vbInformation, "Ошибка ввода"
			say_them_it = ""
		Else
			if InStr(2, s, "?") <> 0 then
				b = InStr(2, s, "?") - 1
			else
				b = Len (s) 'Если это последний вопрос
			end if
			If MsgBox(Mid(s, 2, b), vbQuestion + vbYesNo, "Подтверждение ввода") = vbNo Then
				pos = InStr(2, s, "?") + 1
				say_them_it = "0"
			Else
				pos = InStr(2, s, "?") + 1
				if pos <> 1 then
					Value = Mid(s, pos)
					say_them_it = say_them_it(Value)
				end if
			End If
		End If
	End If
End Function

Dim break_mess' флаг не согласия с предупреждением
Public Function Main(LastControl)
	break_mess = false 'сбросим флаг
	If LastControl Is Nothing or LastControl Is Form1 Then
		' Действия при загрузке формы
		message.text = ""
		num = ""
		BtnSocPriv.Caption = "Социальные льготы"
		vBankruptInfo.Visible = (vBankruptInfo.Text <> "")
		call RefreshOrgMayGiveBtn
	Else
		if LastControl is bSNILSRequest then
'				& "%VAR%.V_DOCS => %PARAM%.P_DOC_ARR" _
			call Runtime.Play("<%PLPCALL [SNILS_REQUEST].[SNILS_CHECK](" _
				& "%PARAM%.P_SNILS => %PARAM%.P_SNILS" _
				& ", %PARAM%.P_LAST_NAME => %PARAM%.P_CL_FAMILY" _
				& ", %PARAM%.P_FIRST_NAME => %PARAM%.P_CL_NAME" _
				& ", %PARAM%.P_PAT_NAME => %PARAM%.P_CL_SNAME" _
				& ", %PARAM%.P_BIRTH_DATE => %PARAM%.P#DATE_PERS" _
				& ", %PARAM%.P_GENDER => %PARAM%.P#SEX" _
				& ") %>" _
				)
		elseif LastControl is GetPassportRF then
			if Runtime.PlayEx("<% PLPCALL P#DOC := [OCR_PASSPORT_RF].[GET_CERTIFICATE]() %>") then
				if Runtime.ServerValidate(Nothing, "SetCertificate") and V_VB.text <> "" then
					execute V_VB.text
					V_VB.text = ""
					call RefreshOrgMayGiveBtn
				end if
			end if
		elseif LastControl is eWhoCode or LastControl is bOrgMayGive then
			call RefreshOrgMayGiveBtn
		else
			If message.text <> "" and message.text <> "%" then
				Form1.ScriptBeep
				num = say_them_it(message.text)
				if num <> "" then
					if LastControl is Text2 then 'ИНН
						LastControl.SetFocus
					end if
					if LastControl is Text14 or LastControl is Text19 then
					'серия и номер
						LastControl.SetFocus
						main = null
						message.text = ""
						break_mess = true 'признак того, что с сообщением не согласились
						exit function
					end	if
					if LastControl is OK then
						if num = "0" then
							main = null
							message.text = ""
							break_mess = true 'признак того, что с сообщением не согласились
							exit function
						else
						' после вывода первого сообщения, вызовем над оставшейся строкой функцию Main
							message.text = num
							Main(OK)
							if break_mess  then' если с повторным предупреждением не согласились, то остановим проверку
								main = null
								message.text = ""
								exit function
							end if
						end if
					end if
				else
					LastControl.SetFocus
				end	if	
				message.text = ""
			End if
		end if
	End If

	Main = True 'В V_VB может быть присваивание Main = Null
	Do While V_VB.TEXT <> ""
		ExecStr = V_VB.TEXT
		V_VB.TEXT = ""
		Execute ExecStr
	Loop
End Function

Public Sub checks_BeforeEdit(Row, Col, Cancel)
	Call Form1.ScriptServerValidate(nothing, "BEFORE_EDIT." & checks.QualByCol(Col) & "[" & row &"]")	
	Execute V_VB.TEXT
	V_VB.TEXT = ""
End Sub

Sub Visits2Ru_OnClick()
	if Form1.ScriptPlayEx("<% PLPCALL %THIS%.[EDIT_VISITS](P_COUNTRY =>%PARAM%.P#COUNTRY) %>") = 1 then
	end if
End Sub

sub RefreshOrgMayGiveBtn
	if bOrgMayGive.Text <> "" then ' ссылка заполнена
		bOrgMayGive.TooltipText = "Выбрать...  (" & eOrgMayGiveName.Text & ")"
	else
		bOrgMayGive.TooltipText = "Выбрать..."
	end if
end sub