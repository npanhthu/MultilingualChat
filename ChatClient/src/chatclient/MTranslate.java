/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MTranslate {

    private String Strout;

    public MTranslate() {
        com.memetix.mst.translate.Translate.setClientId("d9cc0c65-c595-44df-9efb-a38882905a0d");
        com.memetix.mst.translate.Translate.setClientSecret("H/FgWPlYyjUqwhPhbtFhFmwtLoOid0YJzq0gK1TgiS8=");
    }

    public String getStrout() {
        return Strout;
    }

    public String TranslateST(String Strin, String Stngonngu) {
        if (Stngonngu.equals("None")) {
            return Strin;
        }
        try {
            switch (Stngonngu) {
                case "English":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.ENGLISH);
                    return Strout;
                case "Vietnamese":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.VIETNAMESE);
                    return Strout;
                case "Russian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.RUSSIAN);
                    return Strout;
                case "Arabic":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.ARABIC);
                    return Strout;
                case "Bulgarian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.BULGARIAN);
                    return Strout;
                case "Catalan":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.CATALAN);
                    return Strout;
                case "Chinese_simplified":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.CHINESE_SIMPLIFIED);
                    return Strout;
                case "Chinese_traditional":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.CHINESE_TRADITIONAL);
                    return Strout;
                case "Czech":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.CZECH);
                    return Strout;
                case "Danish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.DANISH);
                    return Strout;
                case "Dutch":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.DUTCH);
                    return Strout;
                case "Estonian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.ESTONIAN);
                    return Strout;
                case "Finnish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.FINNISH);
                    return Strout;
                case "French":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.FRENCH);
                    return Strout;
                case "German":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.GERMAN);
                    return Strout;
                case "Greek":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.GREEK);
                    return Strout;
                case "Haitian_creole":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.HAITIAN_CREOLE);
                    return Strout;
                case "Hebrew":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.HEBREW);
                    return Strout;
                case "Hindi":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.HINDI);
                    return Strout;
                case "Hmong_daw":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.HMONG_DAW);
                    return Strout;
                case "Hungarian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.HUNGARIAN);
                    return Strout;
                case "Indonesian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.INDONESIAN);
                    return Strout;
                case "Italian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.ITALIAN);
                    return Strout;
                case "Japanese":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.JAPANESE);
                    return Strout;
                case "Korean":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.KOREAN);
                    return Strout;
                case "Latvian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.LATVIAN);
                    return Strout;
                case "Lithuanian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.LITHUANIAN);
                    return Strout;
                case "Norwegian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.NORWEGIAN);
                    return Strout;
                case "Polish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.POLISH);
                    return Strout;
                case "Portuguese":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.PORTUGUESE);
                    return Strout;
                case "Romanian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.ROMANIAN);
                    return Strout;
                case "Slovak":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.SLOVAK);
                    return Strout;
                case "Slovenian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.SLOVENIAN);
                    return Strout;
                case "Spanish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.SPANISH);
                    return Strout;
                case "Swedish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.SWEDISH);
                    return Strout;
                case "Thai":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.THAI);
                    return Strout;
                case "Turkish":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.TURKISH);
                    return Strout;
                case "Ukrainian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.UKRAINIAN);
                    return Strout;
                //-----------------------------------
                case "Malay":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.MALAY);
                    return Strout;
                case "Persian":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.PERSIAN);
                    return Strout;
                case "Urdu":
                    Strout = Translate.execute(Strin, Language.AUTO_DETECT, Language.URDU);
                    return Strout;
                default:
                    return Strin;
            }
        } catch (Exception ex) {
            Logger.getLogger(MTranslate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Strin;

    }
}
