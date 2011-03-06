/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Oct 20, 2010
 * Time: 11:09:53 PM
 * To change this template use File | Settings | File Templates.
 */

/*URL url = new URL("http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx");
def res = url.getFlightInfoList();
String wholePage = res.text.toString();
def divBeginningTag = '<div id="FlightInfo_FlightInfoUpdatePanel">'
int endOfDivBeginningTag = wholePage.indexOf(divBeginningTag)+divBeginningTag.length();
int tableEnd = wholePage.indexOf("</table>",endOfDivBeginningTag);
String dataLine = wholePage.substring(endOfDivBeginningTag, tableEnd)
println new Date();
println dataLine;
System.out << dataLine;*/


                         Set<Character.UnicodeBlock> chineseCharacter = new HashSet<Character.UnicodeBlock>() {{
    add(Character.UnicodeBlock.CJK_COMPATIBILITY);
    add(Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS);
    add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
    add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
    add(Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
    add(Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
    add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
    add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
    add(Character.UnicodeBlock.KANGXI_RADICALS);
}};


new File('.').eachFile {file->
   if(file.isFile() && file.name.endsWith('.rmvb')){
     def newName = "";
     file.name.each { achar  ->
       if(Character.UnicodeBlock.of(achar as char) == Character.UnicodeBlock.BASIC_LATIN ){
           newName += achar
       }
     }
     println "${file.name} rename to ${newName}"
     file.renameTo(new File(newName));

   }

}

