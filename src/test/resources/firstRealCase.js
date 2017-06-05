var Form1, message, num, BtnSocPriv, vBankruptInfo, bSNILSRequest, GetPassportRF, V_VB, eWhoCode, bOrgMayGive, Text2, Text14, Text19, OK, eOrgMayGiveName;
function say_them_it(s) {
    if (s === "") {
        return "";
    }
    else {
        if (s.substr(0, 1) !== "?") {
            alert("Ошибка ввода:\n" + s);
            return "";
        }
        else {
            let b, pos;
            if (s.indexOf("?", 1) !== -1) {
                b = s.indexOf("?", 1) - 1;
            }
            else {
                b = s.length; // Если это последний вопрос
            }
            if (!confirm("Подтверждение ввода\n" + s.substr(1, b))) {
                pos = s.indexOf("?", 1) + 1;
                return "0";
            }
            else {
                pos = s.indexOf("?", 1) + 1;
                if (pos !== 1) {
                    return say_them_it(s.substr(pos));
                }
            }
        }
    }
}
var break_mess; // флаг не согласия с предупреждением
function Main(lastControl /*Model.Control*/) {
    var result;
    var num;
    break_mess = false; // сбросим флаг
    if (lastControl === undefined || lastControl === Form1) {
        // Действия при загрузке формы
        message.text = "";
        num = "";
        BtnSocPriv.caption = "Социальные льготы";
        vBankruptInfo.visible = (vBankruptInfo.text);
        RefreshOrgMayGiveBtn();
    }
    else {
        if (lastControl === bSNILSRequest) {
            //          & "%VAR%.V_DOCS => %PARAM%.P_DOC_ARR" _
            Runtime.play("<%PLPCALL [SNILS_REQUEST].[SNILS_CHECK]("
                + "%PARAM%.P_SNILS => %PARAM%.P_SNILS"
                + ", %PARAM%.P_LAST_NAME => %PARAM%.P_CL_FAMILY"
                + ", %PARAM%.P_FIRST_NAME => %PARAM%.P_CL_NAME"
                + ", %PARAM%.P_PAT_NAME => %PARAM%.P_CL_SNAME"
                + ", %PARAM%.P_BIRTH_DATE => %PARAM%.P#DATE_PERS"
                + ", %PARAM%.P_GENDER => %PARAM%.P#SEX"
                + ") %>");
        }
        else if (lastControl === GetPassportRF) {
            if (Runtime.playEx("<% PLPCALL P#DOC := [OCR_PASSPORT_RF].[GET_CERTIFICATE]() %>")) {
                if (Runtime.serverValidate(undefined, "SetCertificate") && V_VB.text !== "") {
                    eval(V_VB.text);
                    V_VB.text = "";
                    RefreshOrgMayGiveBtn();
                }
            }
        }
        else if (lastControl === eWhoCode || lastControl === bOrgMayGive) {
            RefreshOrgMayGiveBtn();
        }
        else {
            if (message.text !== "" && message.text !== "%") {
                // Form1.ScriptBeep();
                num = say_them_it(message.text);
                if (num !== "") {
                    if (lastControl === Text2) {
                        lastControl.setFocus();
                    }
                    if (lastControl === Text14 && lastControl === Text19) {
                        // серия и номер
                        lastControl.setFocus();
                        message.text = "";
                        break_mess = true; // признак того, что с сообщением не согласились
                        return null;
                    }
                    if (lastControl === OK) {
                        if (num === "0") {
                            message.text = "";
                            break_mess = true; // признак того, что с сообщением не согласились
                            return null;
                        }
                        else {
                            //  после вывода первого сообщения, вызовем над оставшейся строкой функцию Main
                            message.text = num;
                            Main(OK);
                            if (break_mess) {
                                message.text = "";
                                return null;
                            }
                        }
                    }
                }
                else {
                    lastControl.setFocus();
                }
                message.text = "";
            }
        }
    }
    result = true; // В V_VB может быть присваивание Main = Null
    while (V_VB.text !== "") {
        let execStr = V_VB.text;
        V_VB.text = "";
        eval(execStr);
    }
}
function checks_BeforeEdit(Row, Col, Cancel) {
}
function Visits2Ru_OnClick() {
    if (Runtime.playEx("<% PLPCALL %THIS%.[EDIT_VISITS](P_COUNTRY =>%PARAM%.P#COUNTRY) %>")) {
    }
}
function RefreshOrgMayGiveBtn() {
    if (bOrgMayGive.Text !== "") {
        bOrgMayGive.tooltipText = "Выбрать...  (" + eOrgMayGiveName.Text + ")";
    }
    else {
        bOrgMayGive.tooltipText = "Выбрать...";
    }
}
// VBS Standard String functions
function Mid(s, start, length) {
    return s.substr(start - 1, length);
}
function InStr(start, s, value) {
    return s.indexOf(value, start);
}
function Len(s) {
    return s.length;
}