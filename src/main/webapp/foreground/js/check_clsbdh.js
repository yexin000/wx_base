//
// ����ʶ�����
//
 function checkClsbdh(m_fields, obj, m_SourceValue) {
    obj.value = obj.value.toUpperCase();
    var m_Value = obj.value;
    var strLetter = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
    var i, c;
    if(m_Value=="" || m_Value.length == 0){
    	alert(m_fields + "����Ϊ�գ�");
    	obj.focus();
    	return 1;
    }
    for (i = 0; i < m_Value.length; i++) {
      c = m_Value.charAt(i); //�ַ���s�е��ַ�
      if (c.charCodeAt(0)<255 && strLetter.indexOf(c) == -1) {
         alert("����ʶ����ź��зǷ��ַ���" + c + "��! ");
          obj.focus();
          return 1;
      }
    }
    for (i = 0; i < m_Value.length; i++) {
      c = m_Value.charAt(i); //�ַ���s�е��ַ�
      if(getWordsCount(m_Value,c)>15){
      	 alert("-3:����ʶ�����У�鲻���Ϲ���");
          obj.focus();
          return 1;
      }
    }
    if(!isNaN(m_Value))
	{
   		alert(m_fields + "-2:����ʶ�����У�鲻���Ϲ���");
   		obj.focus();
        return 1;
	} 
    if (m_Value.length != 17) {
      alert(m_fields + "���Ȳ���17λ!")
        obj.focus();
        return 1;
      
    }
    if (m_Value.length == 0) {
      return 1;
    }
    if (m_SourceValue.length == 0) {
      return chech_clshdh_Jyw(obj);
    }
    var i = 1;
    if (m_Value.indexOf("��") >= 0) {
      alert(m_fields + "���С�!!!");
      obj.focus();
      return 1;
    }
    return 0;
  }
  function getWordsCount(str1,words)   
  {   
    var str   =   str1;
  	var keyword   =   words   //�ؼ���     
  	eval("reg=/"+keyword+"/g");   
  	var i=str.match(reg).length;
  	return i;
  }   
  function chech_clshdh_Jyw(obj) {
//  һ�����ֺ���ĸ����ֵ
//      ����������ָ��ֵΪʵ�����֣�������ĸ��ֵ���£�
//     A B C D E F G H J K L M N P R S T U V W X Y Z
//     1 2 3 4 5 6 7 8 1 2 3 4 5 7 9 2 3 4 5 6 7 8 9
//
//  ����λ�ü�Ȩϵ��
//     1 2 3 4 5 6 7 8  9 10 11 12 13 14 15 16 17
//     8 7 6 5 4 3 2 10 0 9  8  7  6  5  4  3  2

    var strClsbdh = obj.value;

    var strLetter = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
    var arrayValue = new Array(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 7, 9, 2,
                               3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8,
                               9);
    var arrayIndex = new Array(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3,
                               2);

    var iSum = 0;
    var SingleLetter = "";
    for (i = 0; i < 17; i++) {
      iSum += arrayValue[strLetter.indexOf(strClsbdh.substr(i, 1))] *
          arrayIndex[i];
    }
    var checkValue = iSum % 11;
    var strCheckValue = checkValue + "";
    if (checkValue == 10) {
      strCheckValue = "X";
    }

    if (strClsbdh.substr(8, 1) != strCheckValue ) {
      
      //alert("����ʶ����ŵھ�λ�����Ϲ���Ӧ��Ϊ:"+strCheckValue+"��");
      
      alert("-1:����ʶ�����У�鲻���Ϲ���");
      obj.focus();
      return 1;
    }
    return 0;
  }